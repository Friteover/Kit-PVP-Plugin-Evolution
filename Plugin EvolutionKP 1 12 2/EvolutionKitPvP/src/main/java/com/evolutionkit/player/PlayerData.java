package com.evolutionkit.player;

import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private int kills;
    private int deaths;
    private int level;
    private int killstreak;
    private boolean inArena;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.kills = 0;
        this.deaths = 0;
        this.level = 1;
        this.killstreak = 0;
        this.inArena = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getKills() {
        return kills;
    }

    public void addKill() {
        this.kills++;
        this.killstreak++;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        this.deaths++;
        this.killstreak = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, Math.min(7, level));
    }

    public void levelUp() {
        if (level < 7) {
            level++;
        }
    }

    public void levelDown() {
        if (level > 1) {
            level--;
        }
    }

    public int getKillstreak() {
        return killstreak;
    }

    public void resetKillstreak() {
        this.killstreak = 0;
    }

    public boolean isInArena() {
        return inArena;
    }

    public void setInArena(boolean inArena) {
        this.inArena = inArena;
    }

    public double getKDRatio() {
        if (deaths == 0) {
            return kills;
        }
        return (double) kills / deaths;
    }

    public void reset() {
        this.kills = 0;
        this.deaths = 0;
        this.level = 1;
        this.killstreak = 0;
    }
}
