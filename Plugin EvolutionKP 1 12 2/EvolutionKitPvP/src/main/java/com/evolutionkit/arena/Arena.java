package com.evolutionkit.arena;

import org.bukkit.Location;

public class Arena {
    private Location spawnLocation;
    private boolean enabled;

    public Arena() {
        this.enabled = false;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location location) {
        this.spawnLocation = location;
        this.enabled = true;
    }

    public boolean isEnabled() {
        return enabled && spawnLocation != null;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
