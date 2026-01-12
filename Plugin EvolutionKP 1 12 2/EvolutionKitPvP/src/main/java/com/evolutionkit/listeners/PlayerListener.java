package com.evolutionkit.listeners;

import com.evolutionkit.EvolutionKitPvP;
import com.evolutionkit.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {
    private final EvolutionKitPvP plugin;

    public PlayerListener(EvolutionKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Créer les données du joueur si elles n'existent pas
        plugin.getPlayerManager().getPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerData data = plugin.getPlayerManager().getPlayerData(event.getPlayer());
        if (data.isInArena()) {
            data.setInArena(false);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        PlayerData data = plugin.getPlayerManager().getPlayerData(event.getPlayer());

        if (data.isInArena()) {
            // Téléporter à l'arène
            if (plugin.getArenaManager().getArena().isEnabled()) {
                event.setRespawnLocation(plugin.getArenaManager().getArena().getSpawnLocation());
            }

            // Donner le kit approprié après un court délai
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                plugin.getKitManager().updatePlayerKit(event.getPlayer(), data);
            }, 5L);
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerManager().getPlayerData(player);

        if (data.isInArena()) {
            // Vérifier si le joueur a changé de monde
            if (plugin.getArenaManager().getArena().isEnabled()) {
                Location arenaSpawn = plugin.getArenaManager().getArena().getSpawnLocation();
                if (arenaSpawn != null && arenaSpawn.getWorld() != null) {
                    if (!player.getWorld().equals(arenaSpawn.getWorld())) {
                        // Le joueur a changé de monde, le retirer de l'arène
                        data.setInArena(false);
                        player.getInventory().clear();
                        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

                        if (plugin.getConfig().getBoolean("features.scoreboard", true)) {
                            player.setScoreboard(plugin.getServer().getScoreboardManager().getNewScoreboard());
                        }

                        player.sendMessage(ChatColor.YELLOW + "Vous avez quitté l'arène (changement de monde).");
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerManager().getPlayerData(player);

        if (data.isInArena()) {
            if (plugin.getArenaManager().getArena().isEnabled()) {
                Location arenaSpawn = plugin.getArenaManager().getArena().getSpawnLocation();
                if (arenaSpawn != null && arenaSpawn.getWorld() != null) {
                    Location playerLoc = player.getLocation();

                    // Vérifier si le joueur est dans le même monde
                    if (playerLoc.getWorld().equals(arenaSpawn.getWorld())) {
                        // Calculer la distance
                        double distance = playerLoc.distance(arenaSpawn);
                        double maxDistance = plugin.getConfig().getDouble("arena.max-distance", 500.0);

                        if (distance > maxDistance) {
                            // Le joueur est trop loin, le retirer de l'arène
                            data.setInArena(false);
                            player.getInventory().clear();
                            player.getActivePotionEffects()
                                    .forEach(effect -> player.removePotionEffect(effect.getType()));

                            if (plugin.getConfig().getBoolean("features.scoreboard", true)) {
                                player.setScoreboard(plugin.getServer().getScoreboardManager().getNewScoreboard());
                            }

                            player.sendMessage(ChatColor.YELLOW + "Vous avez quitté l'arène (trop éloigné du spawn).");
                        }
                    }
                }
            }
        }
    }
}
