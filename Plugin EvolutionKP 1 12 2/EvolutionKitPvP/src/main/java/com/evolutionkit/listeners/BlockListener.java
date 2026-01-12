package com.evolutionkit.listeners;

import com.evolutionkit.EvolutionKitPvP;
import com.evolutionkit.player.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    private final EvolutionKitPvP plugin;

    public BlockListener(EvolutionKitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerManager().getPlayerData(player);

        if (data.isInArena()) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Vous ne pouvez pas casser de blocs dans l'arène !");
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerData data = plugin.getPlayerManager().getPlayerData(player);

        if (data.isInArena()) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Vous ne pouvez pas placer de blocs dans l'arène !");
        }
    }
}
