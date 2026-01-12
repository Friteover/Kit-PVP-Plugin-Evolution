package com.evolutionkit.kit;

import com.evolutionkit.player.PlayerData;
import org.bukkit.entity.Player;

public class KitManager {
    
    public void giveKitToPlayer(Player player, PlayerData data) {
        Kit kit = Kit.getByLevel(data.getLevel());
        kit.giveKit(player);
    }
    
    public void updatePlayerKit(Player player, PlayerData data) {
        giveKitToPlayer(player, data);
    }
    
    public Kit getKitForLevel(int level) {
        return Kit.getByLevel(level);
    }
}
