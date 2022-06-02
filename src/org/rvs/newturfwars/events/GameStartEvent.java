package org.rvs.newturfwars.events;

import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.rvs.newturfwars.Main;
import org.rvs.newturfwars.manager.ScoreboardUpdater;
import org.rvs.newturfwars.manager.TeamManager;
import org.rvs.newturfwars.manager.YMLFile;

public class GameStartEvent {
    private boolean matchStart;
    private final Main main;
    private final TeamManager teamManager;
    private final ScoreboardUpdater sb;
    
    public Location limitRightRed;
    public Location limitLeftRed;
    public Location limitRightBlue;
    public Location limitLeftBlue;
    
    public String redForwards;
    public String blueForwards;
    
    public GameStartEvent(final Main main, final TeamManager teamManager, final ScoreboardUpdater sb) {
    	this.matchStart = false;
    	this.main = main;
    	this.teamManager = teamManager;
    	this.sb = sb;
    }

    public boolean isRunning() {
    	return this.matchStart;
    }
    
    public void startMatch(Player p) {
    	this.matchStart = true;
    	Random rand = new Random();
    	   	
    	Player owner = p.getWorld().getPlayers().get(rand.nextInt(p.getWorld().getPlayers().size()));
    	
    	teamManager.createTeam(owner, "Blue");
    	
    	while (teamManager.isInTeam(owner)) owner = p.getWorld().getPlayers().get(rand.nextInt(p.getWorld().getPlayers().size()));
    		
    	teamManager.createTeam(owner, "Red");

    	int i = 0;
    	int playersTotali = p.getWorld().getPlayers().size();
    	
    	for (Player playerInLobby : p.getWorld().getPlayers()) {
    		
    		if(!playerInLobby.getGameMode().equals(GameMode.SURVIVAL)) playerInLobby.setGameMode(GameMode.SURVIVAL);
    		   
    		if(p.getWorld().getPlayers().size()%2!=0) {
    			playersTotali = playersTotali - 1;
    		}
    		
            if(i<=playersTotali/2) {
            	teamManager.addPlayer(playerInLobby, "Blue", "member");
            }else {
            	teamManager.addPlayer(playerInLobby, "Red", "member");
            }
    		
        	ItemStack bow = new ItemStack(Material.BOW, 1);
        	ItemStack arrows = new ItemStack(Material.ARROW, 3);
        	
        	ItemMeta metaBow = bow.getItemMeta();
        	String kit = "default";
        	metaBow.setDisplayName(this.main.getConfig().getString("names."+kit+".bow").replaceAll("&", "§"));
        	metaBow.setUnbreakable(true);
        	metaBow.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        	
        	bow.setItemMeta(metaBow);
        	
        	ItemMeta metaArrows = arrows.getItemMeta();
        	metaArrows.setDisplayName(this.main.getConfig().getString("names."+kit+".arrow").replaceAll("&", "§"));
        	arrows.setItemMeta(metaArrows);

        	playerInLobby.getInventory().addItem(bow);
        	playerInLobby.getInventory().addItem(arrows);
        	        	
    		i++;
		}
    }
    
    public void endMatch(List<Player> players) {
    	this.matchStart = false;
    	for (Player p : players) {
	    	p.teleport(new Location(p.getWorld(), YMLFile.readDouble("spawns", "lobby.x"), YMLFile.readDouble("spawns", "lobby.y"), YMLFile.readDouble("spawns", "lobby.z")));
	    	p.sendMessage(this.main.getConfig().getString("messages.matchend").replaceAll("&", "§"));
	    	p.getInventory().clear();
    	}
    	
    }
    
    public void setRedArea(Location limitRight, Location limitLeft, String go) {
    	limitRightRed = limitRight;
    	limitLeftRed = limitLeft;
    	
    	redForwards = go;
    }
    
    public void setBlueArea(Location limitRight, Location limitLeft, String go) {
    	limitRightBlue = limitRight;
    	limitLeftBlue = limitLeft;
    	
    	blueForwards = go;
    }
}
