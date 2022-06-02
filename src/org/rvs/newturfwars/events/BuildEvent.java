package org.rvs.newturfwars.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.rvs.newturfwars.Main;
import org.rvs.newturfwars.TimerBlocks;
import org.rvs.newturfwars.manager.TeamManager;
import org.rvs.newturfwars.manager.YMLFile;

public class BuildEvent implements Listener{
	
	private GameStartEvent game;
	private TeamManager teamManager;
	private Main main;
    
    public BuildEvent(Main main, GameStartEvent game, TeamManager teamManager) {
    	this.main = main;
        this.game = game;
        this.teamManager = teamManager;
    }
    
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e) {
    	if(game.isRunning() && (!TimerBlocks.buildTime || !(e.getBlock().getLocation().subtract(0, 1, 0).getBlock().getType().equals(this.teamManager.getTeam(e.getPlayer()).equals("Blue") ? Material.BLUE_WOOL : Material.RED_WOOL))))
    		e.setCancelled(true);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakBlock(BlockBreakEvent e) {
    	e.getPlayer().sendMessage(e.getBlock().getY() + " -> " + (int)YMLFile.readDouble("arenas", "arena1.red.right.y"));

    	if((int)e.getBlock().getY() < (int)YMLFile.readDouble("arenas", "arena1.red.right.y") || !(e.getBlock().getType().equals(this.teamManager.getTeam(e.getPlayer()).equals("Blue") ? Material.BLUE_WOOL : Material.RED_WOOL)))
    	
    		e.setCancelled(true);
    	
    }
}
