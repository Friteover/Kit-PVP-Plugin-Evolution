package com.evolutionkit.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {
    private final Map<UUID, PlayerData> playerDataMap;

    public PlayerManager() {
        this.playerDataMap = new HashMap<>();
    }

    public PlayerData getPlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        if (!playerDataMap.containsKey(uuid)) {
            playerDataMap.put(uuid, new PlayerData(uuid));
        }
        return playerDataMap.get(uuid);
    }

    public PlayerData getPlayerData(UUID uuid) {
        if (!playerDataMap.containsKey(uuid)) {
            playerDataMap.put(uuid, new PlayerData(uuid));
        }
        return playerDataMap.get(uuid);
    }

    public void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }

    public boolean hasPlayerData(UUID uuid) {
        return playerDataMap.containsKey(uuid);
    }

    public Map<UUID, PlayerData> getAllPlayerData() {
        return new HashMap<>(playerDataMap);
    }
}
