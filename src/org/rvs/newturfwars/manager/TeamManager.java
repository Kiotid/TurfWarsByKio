package org.rvs.newturfwars.manager;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.rvs.newturfwars.Main;

public class TeamManager{
	private HashMap<String, Team> teams = new HashMap<>();
	private HashMap<UUID, String> players = new HashMap<>();
	private final Main main;
	
	public TeamManager(final Main main) {
		this.main = main;
	}

	public boolean createTeam(Player owner, String name) {
	    if (isInTeam(owner))
	        return false;
	    Team team = new Team(owner, name);
	    teams.put(name, team);
	    players.put(owner.getUniqueId(), name);

	    if(name.equals("Red") || name.equals("Blue")) {
        	giveArmorAndTp(true, name, owner);
        	owner.sendMessage(this.main.getConfig().getString(name.equals("Blue") ? "messages.blueowner" : "messages.redowner").replaceAll("&", "§"));
        	owner.sendMessage(this.main.getConfig().getString(name.equals("Blue") ? "messages.blue" : "messages.red").replaceAll("&", "§"));

    	}
	    	    
	    return true;
	}

	public boolean addPlayer(Player player, String name, String memberType) { 
	    if (teams.containsKey(name) && !players.containsKey(player.getUniqueId())) { 
	        Team team = teams.get(name); 
	        team.addMember(player, memberType);
	        players.put(player.getUniqueId(), name);
		    if(name.equals("Red") || name.equals("Blue")) {
	        	giveArmorAndTp(false, name, player);
	        	player.sendMessage(this.main.getConfig().getString(name.equals("Blue") ? "messages.blue" : "messages.red").replaceAll("&", "§"));

		    }

	        return true;
	    }
	    return false;
	}

	private void giveArmorAndTp(boolean owner, String name, Player player) {
		player.getInventory().clear();
		
    	ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
    	ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
    	ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
    	ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
    	
    	LeatherArmorMeta helmetMeta = (LeatherArmorMeta)helmet.getItemMeta();
    	helmetMeta.setColor(!owner ? name.equals("Blue") ? Color.BLUE : Color.RED : Color.GREEN);
    	helmetMeta.setUnbreakable(true);
    	helmetMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    	helmet.setItemMeta(helmetMeta);
    	
    	LeatherArmorMeta chestplateMeta = (LeatherArmorMeta)chestplate.getItemMeta();
    	chestplateMeta.setColor(name.equals("Blue") ? Color.BLUE : Color.RED);
    	chestplateMeta.setUnbreakable(true);
    	chestplateMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    	chestplate.setItemMeta(chestplateMeta);
    	
    	LeatherArmorMeta leggingsMeta = (LeatherArmorMeta)leggings.getItemMeta();
    	leggingsMeta.setColor(name.equals("Blue") ? Color.BLUE : Color.RED);
    	leggingsMeta.setUnbreakable(true);
    	leggingsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    	leggings.setItemMeta(leggingsMeta);
    	
    	LeatherArmorMeta bootsMeta = (LeatherArmorMeta)boots.getItemMeta();
    	bootsMeta.setColor(name.equals("Blue") ? Color.BLUE : Color.RED);
    	bootsMeta.setUnbreakable(true);
    	bootsMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    	boots.setItemMeta(bootsMeta);
    	
    	player.getInventory().setHelmet(helmet);
    	player.getInventory().setChestplate(chestplate);
    	player.getInventory().setLeggings(leggings);
    	player.getInventory().setBoots(boots);
    	
    	player.teleport(new Location(player.getWorld(), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.x" : "red.x"), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.y" : "red.y"), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.z" : "red.z")));
	}

	public void removePlayer(Player player) {
	    if (players.containsKey(player.getUniqueId())) {
	        String name = players.get(player.getUniqueId()); 
	        players.remove(player.getUniqueId());
	        Team team = teams.get(name);
	        if (team.getOwner().equals(player.getUniqueId())) {
	            Set<UUID> members = team.getMembers();
	            for (UUID uuid : members) {
	                players.remove(uuid); 
	            }
	            teams.remove(name);
	        }else {
		        team.removeMember(player); 
	        }
	    }

	}

	public boolean isInTeam(Player player) {
	    return players.containsKey(player.getUniqueId());
	}

	public String getTeam(Player player) {
	    if (players.containsKey(player.getUniqueId()))
	        return players.get(player.getUniqueId());
	    return null;
	}
	

	public void tpToTeam(Player player, PlayerRespawnEvent e) {
	    if (!players.containsKey(player.getUniqueId())) return;
	    
	    boolean owner = (teams.get(getTeam(player)).getOwner()).equals(player) ? true : false;
	    
        String name = players.get(player.getUniqueId());
        
    	giveArmorAndTp(owner, name, player);

    	ItemStack bow = new ItemStack(Material.BOW, 1);
    	ItemStack arrows = new ItemStack(Material.ARROW, 3);
    	
    	ItemMeta metaBow = bow.getItemMeta();
    	
    	String kit = "default";
    	metaBow.setDisplayName(main.getConfig().getString("names."+kit+".bow").replaceAll("&", "§"));
    	metaBow.setUnbreakable(true);
    	metaBow.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    	
    	bow.setItemMeta(metaBow);
    	
    	ItemMeta metaArrows = arrows.getItemMeta();

    	metaArrows.setDisplayName(main.getConfig().getString("names."+kit+".arrow").replaceAll("&", "§"));
    	arrows.setItemMeta(metaArrows);

    	player.getInventory().addItem(bow);
    	player.getInventory().addItem(arrows);
    	
        e.setRespawnLocation(new Location(player.getWorld(), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.x" : "red.x"), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.y" : "red.y"), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.z" : "red.z")));
	    
	}
	
	public void tpToTeam(Player player) {
	    if (!players.containsKey(player.getUniqueId())) return;
	    
	    boolean owner = teams.get(players.get(player.getUniqueId())).getOwner().equals(player.getUniqueId()) ? true : false;
	    
        String name = players.get(player.getUniqueId());
        
    	giveArmorAndTp(owner, name, player);

    	player.teleport(new Location(player.getWorld(), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.x" : "red.x"), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.y" : "red.y"), YMLFile.readDouble("spawns", name.equals("Blue") ? "blue.z" : "red.z")));
	    
	}
	
	public Set<String> getTeams() {
	    return teams.keySet();
	}
}
