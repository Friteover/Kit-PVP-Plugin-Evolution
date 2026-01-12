package com.evolutionkit;

import com.evolutionkit.arena.ArenaManager;
import com.evolutionkit.commands.KitPvPCommand;
import com.evolutionkit.kit.KitManager;
import com.evolutionkit.listeners.BlockListener;
import com.evolutionkit.listeners.CombatListener;
import com.evolutionkit.listeners.PlayerListener;
import com.evolutionkit.player.PlayerManager;
import com.evolutionkit.ui.ScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EvolutionKitPvP extends JavaPlugin {
    private PlayerManager playerManager;
    private KitManager kitManager;
    private ArenaManager arenaManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        // Sauvegarder la config par défaut
        saveDefaultConfig();

        // Initialiser les managers
        playerManager = new PlayerManager();
        kitManager = new KitManager();
        arenaManager = new ArenaManager(this);
        scoreboardManager = new ScoreboardManager();

        // Enregistrer les événements
        getServer().getPluginManager().registerEvents(new CombatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);

        // Enregistrer les commandes
        getCommand("kitpvp").setExecutor(new KitPvPCommand(this));

        getLogger().info("Evolution Kit PvP activé avec succès !");
        getLogger().info("12 kits disponibles avec système d'évolution progressive");
    }

    @Override
    public void onDisable() {
        getLogger().info("Evolution Kit PvP désactivé !");
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }
}
