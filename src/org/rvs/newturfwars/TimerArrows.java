package org.rvs.newturfwars;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.rvs.newturfwars.events.GameStartEvent;

public class TimerArrows extends BukkitRunnable {

	private List<Player> players;
	private final Main main;
	private GameStartEvent game;

    public TimerArrows(Main main, Player player, GameStartEvent game) {
    	this.players = player.getWorld().getPlayers();
    	this.main = main;
    	this.game = game;
    }

    @Override
    public void run() {
    	int amount = 0;
    	
    	for (Player p : players) {
    		for(ItemStack item : p.getInventory().getContents()) {
    			try {
	    		    if(item.getType().equals(Material.ARROW)) {
	    		        amount = item.getAmount();
	    		        break;
	    		    }
    			}catch(Exception e) {
    				
    			}
    		}
    		
    		if (amount < 3 || amount == 0) {
	        	ItemStack arrows = new ItemStack(Material.ARROW, 1);
	        	ItemMeta metaArrows = arrows.getItemMeta();
	        	String kit = "default";
	        	metaArrows.setDisplayName(this.main.getConfig().getString("names."+kit+".arrow").replaceAll("&", "§"));
	        	arrows.setItemMeta(metaArrows);
	        	
        		p.getInventory().addItem(arrows);
    		}
    	}
    	
    	if(!game.isRunning()) {
    		this.cancel();
    		return;
    	}
    }
}