package com.evolutionkit.commands;

import com.evolutionkit.EvolutionKitPvP;
import com.evolutionkit.kit.Kit;
import com.evolutionkit.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitPvPCommand implements CommandExecutor {
    private final EvolutionKitPvP plugin;

    public KitPvPCommand(EvolutionKitPvP plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Cette commande est réservée aux joueurs !");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "join":
                handleJoin(player);
                break;
            case "leave":
                handleLeave(player);
                break;
            case "stats":
                if (args.length > 1) {
                    Player target = plugin.getServer().getPlayer(args[1]);
                    if (target != null) {
                        handleStats(player, target);
                    } else {
                        player.sendMessage(ChatColor.RED + "Joueur introuvable !");
                    }
                } else {
                    handleStats(player, player);
                }
                break;
            case "reset":
                handleReset(player);
                break;
            case "setspawn":
                handleSetSpawn(player);
                break;
            default:
                sendHelp(player);
                break;
        }

        return true;
    }

    private void handleJoin(Player player) {
        if (!plugin.getArenaManager().getArena().isEnabled()) {
            String message = plugin.getConfig().getString("messages.arena-not-set", "&cL'arène n'est pas configurée !");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            return;
        }

        PlayerData data = plugin.getPlayerManager().getPlayerData(player);

        if (data.isInArena()) {
            player.sendMessage(ChatColor.RED + "Vous êtes déjà dans l'arène !");
            return;
        }

        data.setInArena(true);
        plugin.getArenaManager().teleportToArena(player);
        plugin.getKitManager().giveKitToPlayer(player, data);

        if (plugin.getConfig().getBoolean("features.scoreboard", true)) {
            plugin.getScoreboardManager().updateScoreboard(player, data);
        }

        String message = plugin.getConfig().getString("messages.join", "&a✓ Vous avez rejoint l'arène !");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private void handleLeave(Player player) {
        PlayerData data = plugin.getPlayerManager().getPlayerData(player);

        if (!data.isInArena()) {
            player.sendMessage(ChatColor.RED + "Vous n'êtes pas dans l'arène !");
            return;
        }

        data.setInArena(false);
        player.getInventory().clear();
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        if (plugin.getConfig().getBoolean("features.scoreboard", true)) {
            player.setScoreboard(plugin.getServer().getScoreboardManager().getNewScoreboard());
        }

        String message = plugin.getConfig().getString("messages.leave", "&c✗ Vous avez quitté l'arène.");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private void handleStats(Player viewer, Player target) {
        PlayerData data = plugin.getPlayerManager().getPlayerData(target);
        Kit kit = Kit.getByLevel(data.getLevel());

        viewer.sendMessage(ChatColor.GOLD + "═══════════════════════════");
        viewer.sendMessage(ChatColor.YELLOW + "Statistiques de " + ChatColor.WHITE + target.getName());
        viewer.sendMessage("");
        viewer.sendMessage(ChatColor.GRAY + "Niveau: " + kit.getDisplayName());
        viewer.sendMessage(ChatColor.GRAY + "Kills: " + ChatColor.GREEN + data.getKills());
        viewer.sendMessage(ChatColor.GRAY + "Morts: " + ChatColor.RED + data.getDeaths());
        viewer.sendMessage(ChatColor.GRAY + "K/D Ratio: " + ChatColor.AQUA + String.format("%.2f", data.getKDRatio()));
        viewer.sendMessage(ChatColor.GRAY + "Killstreak: " + ChatColor.GOLD + data.getKillstreak());
        viewer.sendMessage(ChatColor.GOLD + "═══════════════════════════");
    }

    private void handleReset(Player player) {
        if (!player.hasPermission("evolutionkit.admin")) {
            player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission !");
            return;
        }

        PlayerData data = plugin.getPlayerManager().getPlayerData(player);
        data.reset();

        if (data.isInArena()) {
            plugin.getKitManager().updatePlayerKit(player, data);
            if (plugin.getConfig().getBoolean("features.scoreboard", true)) {
                plugin.getScoreboardManager().updateScoreboard(player, data);
            }
        }

        player.sendMessage(ChatColor.GREEN + "Vos statistiques ont été réinitialisées !");
    }

    private void handleSetSpawn(Player player) {
        if (!player.hasPermission("evolutionkit.admin")) {
            player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission !");
            return;
        }

        plugin.getArenaManager().setSpawnLocation(player.getLocation());
        player.sendMessage(ChatColor.GREEN + "Point de spawn de l'arène défini !");
    }

    private void sendHelp(Player player) {
        player.sendMessage(ChatColor.GOLD + "═══ Evolution Kit PvP ═══");
        player.sendMessage(ChatColor.YELLOW + "/kitpvp join" + ChatColor.GRAY + " - Rejoindre l'arène");
        player.sendMessage(ChatColor.YELLOW + "/kitpvp leave" + ChatColor.GRAY + " - Quitter l'arène");
        player.sendMessage(ChatColor.YELLOW + "/kitpvp stats [joueur]" + ChatColor.GRAY + " - Voir les stats");
        if (player.hasPermission("evolutionkit.admin")) {
            player.sendMessage(ChatColor.YELLOW + "/kitpvp reset" + ChatColor.GRAY + " - Reset vos stats");
            player.sendMessage(ChatColor.YELLOW + "/kitpvp setspawn" + ChatColor.GRAY + " - Définir le spawn");
        }
    }
}
