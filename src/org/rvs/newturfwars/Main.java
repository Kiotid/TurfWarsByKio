package org.rvs.newturfwars;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.rvs.newturfwars.commands.Commands;
import org.rvs.newturfwars.events.BowEvent;
import org.rvs.newturfwars.events.BuildEvent;
import org.rvs.newturfwars.events.DeadEvent;
import org.rvs.newturfwars.events.DropEvent;
import org.rvs.newturfwars.events.FallDamageReducerEvent;
import org.rvs.newturfwars.events.GameStartEvent;
import org.rvs.newturfwars.events.HungerLossEvent;
import org.rvs.newturfwars.events.MakeAreaEvent;
import org.rvs.newturfwars.events.PlayerJoin;
import org.rvs.newturfwars.events.RejectEvent;
import org.rvs.newturfwars.manager.MySQLManager;
import org.rvs.newturfwars.manager.ScoreboardUpdater;
import org.rvs.newturfwars.manager.TeamManager;
import org.rvs.newturfwars.manager.YMLFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{	
    public MySQLManager mySQLManager;
    
    public void onEnable() {
        this.mySQLManager = new MySQLManager(this);
        this.mySQLManager.setup();
        
    	TeamManager teamManager = new TeamManager(this);
    	ScoreboardUpdater sb = new ScoreboardUpdater();
        GameStartEvent game = new GameStartEvent(this, teamManager, sb);

        this.registerEvents(game, teamManager, sb);
        this.registerCommands(game, teamManager, sb);

        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        
        System.out.println("[NewKioTurfWars] ENABLED!");
        
    }
    
    private void registerEvents(GameStartEvent game, TeamManager teamManager, ScoreboardUpdater sb) {
        Bukkit.getPluginManager().registerEvents((Listener)new PlayerJoin(this, game, sb), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new BowEvent(this, game, teamManager), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new DeadEvent(this, teamManager, game, sb), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new DropEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new HungerLossEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new RejectEvent(this, teamManager), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new FallDamageReducerEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new BuildEvent(this, game, teamManager), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new MakeAreaEvent(), (Plugin)this);
    }

    
    private void registerCommands(GameStartEvent game, TeamManager teamManager, ScoreboardUpdater sb) {
        this.getCommand("kioturfwars").setExecutor((CommandExecutor)new Commands(this, game, teamManager, sb));
    }
}

