package org.rvs.newturfwars.events;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.rvs.newturfwars.Main;
import org.rvs.newturfwars.manager.ScoreboardUpdater;
import org.rvs.newturfwars.manager.TeamManager;
import org.rvs.newturfwars.manager.YMLFile;

public class DeadEvent implements Listener
{
	private Main main;
	private TeamManager teamManager;
	private GameStartEvent game;
	private ScoreboardUpdater sb;
	
	public DeadEvent(final Main main, final TeamManager teamManager, final GameStartEvent game, final ScoreboardUpdater sb){
		this.main = main;
		this.game = game;
		this.sb = sb;
		this.teamManager = teamManager;
	}
	
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(EntityDeathEvent e) {
    	
        e.getDrops().clear();  
        e.setDroppedExp(0);
    }   
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
    	
    	if(game.isRunning())
    		this.teamManager.tpToTeam(e.getPlayer(), e);
    	else {
            e.setRespawnLocation(new Location(e.getPlayer().getWorld(), YMLFile.readDouble("spawns", "lobby.x"), YMLFile.readDouble("spawns", "lobby.y"), YMLFile.readDouble("spawns", "lobby.z")));

        	e.getPlayer().sendMessage(this.main.getConfig().getString("messages.lobby").replaceAll("&", "§"));
        	sb.resetScoreboard(e.getPlayer());
        	e.getPlayer().setLevel(0);
        	e.getPlayer().setExp(0);
        	e.getPlayer().getInventory().clear();
    	}  
    }
    
}
