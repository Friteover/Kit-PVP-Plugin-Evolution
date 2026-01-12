package com.evolutionkit.arena;

import com.evolutionkit.EvolutionKitPvP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ArenaManager {
    private final EvolutionKitPvP plugin;
    private final Arena arena;

    public ArenaManager(EvolutionKitPvP plugin) {
        this.plugin = plugin;
        this.arena = new Arena();
        loadArenaFromConfig();
    }

    public Arena getArena() {
        return arena;
    }

    public void teleportToArena(Player player) {
        if (arena.isEnabled()) {
            player.teleport(arena.getSpawnLocation());
        }
    }

    public void setSpawnLocation(Location location) {
        arena.setSpawnLocation(location);
        saveArenaToConfig();
    }

    private void loadArenaFromConfig() {
        ConfigurationSection arenaSection = plugin.getConfig().getConfigurationSection("arena");
        if (arenaSection != null && arenaSection.getBoolean("enabled", false)) {
            ConfigurationSection spawnSection = arenaSection.getConfigurationSection("spawn");
            if (spawnSection != null) {
                String worldName = spawnSection.getString("world");
                double x = spawnSection.getDouble("x");
                double y = spawnSection.getDouble("y");
                double z = spawnSection.getDouble("z");
                float yaw = (float) spawnSection.getDouble("yaw");
                float pitch = (float) spawnSection.getDouble("pitch");

                if (worldName != null && Bukkit.getWorld(worldName) != null) {
                    Location location = new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
                    arena.setSpawnLocation(location);
                }
            }
        }
    }

    private void saveArenaToConfig() {
        Location loc = arena.getSpawnLocation();
        if (loc != null && loc.getWorld() != null) {
            plugin.getConfig().set("arena.enabled", true);
            plugin.getConfig().set("arena.spawn.world", loc.getWorld().getName());
            plugin.getConfig().set("arena.spawn.x", loc.getX());
            plugin.getConfig().set("arena.spawn.y", loc.getY());
            plugin.getConfig().set("arena.spawn.z", loc.getZ());
            plugin.getConfig().set("arena.spawn.yaw", loc.getYaw());
            plugin.getConfig().set("arena.spawn.pitch", loc.getPitch());
            plugin.saveConfig();
        }
    }
}
