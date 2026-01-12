package com.evolutionkit.listeners;

import com.evolutionkit.EvolutionKitPvP;
import com.evolutionkit.kit.Kit;
import com.evolutionkit.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CombatListener implements Listener {
    private final EvolutionKitPvP plugin;

    public CombatListener(EvolutionKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        PlayerData victimData = plugin.getPlayerManager().getPlayerData(victim);

        if (!victimData.isInArena()) {
            return;
        }

        // Gérer la mort de la victime
        victimData.addDeath();
        int oldLevel = victimData.getLevel();
        victimData.levelDown();

        if (victimData.getLevel() < oldLevel) {
            Kit newKit = Kit.getByLevel(victimData.getLevel());
            String message = plugin.getConfig().getString("messages.death", "&c-1 Niveau !")
                    .replace("{kit}", newKit.getDisplayName());
            victim.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }

        // Gérer le kill
        if (killer != null && killer != victim) {
            PlayerData killerData = plugin.getPlayerManager().getPlayerData(killer);

            if (killerData.isInArena()) {
                killerData.addKill();

                // Vérifier si le joueur doit level up
                int killsNeeded = getKillsNeededForNextLevel(killerData.getLevel());
                int currentKills = killerData.getKills();

                // Calculer les kills depuis le dernier niveau
                int killsForCurrentLevel = 0;
                for (int i = 1; i < killerData.getLevel(); i++) {
                    killsForCurrentLevel += getKillsNeededForNextLevel(i);
                }

                int killsSinceLastLevel = currentKills - killsForCurrentLevel;

                if (killsSinceLastLevel >= killsNeeded && killerData.getLevel() < 12) {
                    int previousLevel = killerData.getLevel();
                    killerData.levelUp();
                    Kit newKit = Kit.getByLevel(killerData.getLevel());

                    String levelUpMsg = plugin.getConfig().getString("messages.level-up", "&6⚡ LEVEL UP !")
                            .replace("{kit}", newKit.getDisplayName());
                    killer.sendMessage(ChatColor.translateAlternateColorCodes('&', levelUpMsg));

                    // Donner le nouveau kit
                    plugin.getKitManager().updatePlayerKit(killer, killerData);
                } else {
                    // Message de progression
                    int progress = (int) ((double) killsSinceLastLevel / killsNeeded * 100);
                    String killMsg = plugin.getConfig().getString("messages.kill", "&a+1 Kill !")
                            .replace("{progress}", String.valueOf(progress));
                    killer.sendMessage(ChatColor.translateAlternateColorCodes('&', killMsg));
                }

                // Messages de killstreak
                int streak = killerData.getKillstreak();
                if (streak == 3 || streak == 5 || streak == 10) {
                    String streakMsg = plugin.getConfig().getString("messages.killstreak-" + streak, "");
                    if (!streakMsg.isEmpty()) {
                        killer.sendMessage(ChatColor.translateAlternateColorCodes('&', streakMsg));
                    }
                }

                // Mettre à jour le scoreboard
                if (plugin.getConfig().getBoolean("features.scoreboard", true)) {
                    plugin.getScoreboardManager().updateScoreboard(killer, killerData);
                }
            }
        }

        // Mettre à jour le scoreboard de la victime
        if (plugin.getConfig().getBoolean("features.scoreboard", true)) {
            plugin.getScoreboardManager().updateScoreboard(victim, victimData);
        }

        // Empêcher le drop d'items
        event.getDrops().clear();
    }

    private int getKillsNeededForNextLevel(int currentLevel) {
        String path = "kills-per-level." + currentLevel;
        return plugin.getConfig().getInt(path, 1);
    }
}
