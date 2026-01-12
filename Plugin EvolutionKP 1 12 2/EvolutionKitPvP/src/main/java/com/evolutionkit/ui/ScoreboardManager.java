package com.evolutionkit.ui;

import com.evolutionkit.kit.Kit;
import com.evolutionkit.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    public void updateScoreboard(Player player, PlayerData data) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("kitpvp", "dummy");
        objective.setDisplayName(ChatColor.GOLD + "§l⚔ Kit PvP ⚔");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Kit kit = Kit.getByLevel(data.getLevel());

        setScore(objective, " ", 9);
        setScore(objective, ChatColor.YELLOW + "Kit: " + kit.getDisplayName(), 8);
        setScore(objective, ChatColor.GRAY + "Niveau " + data.getLevel() + "/7", 7);
        setScore(objective, "  ", 6);
        setScore(objective, ChatColor.GREEN + "Kills: " + ChatColor.WHITE + data.getKills(), 5);
        setScore(objective, ChatColor.RED + "Morts: " + ChatColor.WHITE + data.getDeaths(), 4);
        setScore(objective, ChatColor.AQUA + "K/D: " + ChatColor.WHITE + String.format("%.2f", data.getKDRatio()), 3);
        setScore(objective, "   ", 2);
        setScore(objective, ChatColor.GOLD + "Streak: " + ChatColor.WHITE + data.getKillstreak(), 1);
        setScore(objective, "    ", 0);

        player.setScoreboard(scoreboard);
    }

    private void setScore(Objective objective, String text, int score) {
        objective.getScore(text).setScore(score);
    }
}
