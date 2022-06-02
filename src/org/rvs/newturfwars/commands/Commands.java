package org.rvs.newturfwars.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.rvs.newturfwars.Main;
import org.rvs.newturfwars.TimerArrows;
import org.rvs.newturfwars.TimerBlocks;
import org.rvs.newturfwars.TimerGame;
import org.rvs.newturfwars.events.GameStartEvent;
import org.rvs.newturfwars.events.MakeAreaEvent;
import org.rvs.newturfwars.manager.ScoreboardUpdater;
import org.rvs.newturfwars.manager.TeamManager;
import org.rvs.newturfwars.manager.YMLFile;
import org.bukkit.command.CommandExecutor;

public class Commands implements CommandExecutor
{
    private final Main main;
    private final GameStartEvent game;
    private final TeamManager teamManager;
    private final ScoreboardUpdater sb;
    
    public Commands(final Main main, final GameStartEvent game, final TeamManager teamManager, final ScoreboardUpdater sb) {
        this.main = main;
        this.game = game;
        this.teamManager = teamManager;
        this.sb = sb;
    }
    
    @Override
    public boolean onCommand(final CommandSender cs, final Command cmd, final String label, final String[] args) {
        if (cs instanceof ConsoleCommandSender) {
            cs.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.no-console").replaceAll("&", "§"));
            return true;
        }
        Player p = (Player) cs;
        
        if (!cmd.getName().equalsIgnoreCase("kioturfwars")) return true;
        
        if (args == null || args.length == 0) { 
        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + "§6Help:"); 
        	
        	return true; 
        }
                
    	if(game.isRunning()) {
    		p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.alreadystart").replaceAll("&", "§"));
    		return true;
    	}
    	
        switch (args[0].toLowerCase()) {
	        case "help":
	        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + "§6Help:"); 

	        	return true;
	             
	        case "setlobby":
				YMLFile.write("spawns", "lobby.x", p.getLocation().getX());
				YMLFile.write("spawns", "lobby.y", p.getLocation().getY());
				YMLFile.write("spawns", "lobby.z", p.getLocation().getZ());
		        	
	        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.setlobby").replaceAll("&", "§"));
	        	
	            return true;
	            
	        case "lobby":
	        	p.teleport(new Location(p.getWorld(), YMLFile.readDouble("spawns", "lobby.x"), YMLFile.readDouble("spawns", "lobby.y"), YMLFile.readDouble("spawns", "lobby.z")));
	        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.lobby").replaceAll("&", "§"));
    	    	sb.resetScoreboard(p);
    	    	p.setLevel(0);
        		p.setExp(0);
        		p.getInventory().clear();
	        	return true;
	        	
	        case "setredspawn":
				YMLFile.write("spawns", "red.x", p.getLocation().getX());
				YMLFile.write("spawns", "red.y", p.getLocation().getY());
				YMLFile.write("spawns", "red.z", p.getLocation().getZ());
		        	
	        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.setred").replaceAll("&", "§"));
	        	
	            return true;
	            
	        case "setbluespawn":
				YMLFile.write("spawns", "blue.x", p.getLocation().getX());
				YMLFile.write("spawns", "blue.y", p.getLocation().getY());
				YMLFile.write("spawns", "blue.z", p.getLocation().getZ());
		        	
	        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.setblue").replaceAll("&", "§"));
	        	
	            return true;
	            
	        case "setspecspawn":
				YMLFile.write("spawns", "spec.x", p.getLocation().getX());
				YMLFile.write("spawns", "spec.y", p.getLocation().getY());
				YMLFile.write("spawns", "spec.z", p.getLocation().getZ());
		        	
	        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.setspec").replaceAll("&", "§"));
	        	
	            return true;
	            
	        case "setarea":
	        	
	        	if (args.length < 2){
		    		p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + "devi avere il numero giusto di argomenti, guarda /ktw help per l'usage");
		    		return true;

		    	}
        	
        		if(MakeAreaEvent.XYZRight == null && MakeAreaEvent.XYZRight == null) {
		    		p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + "prima setta i capi cliccando");
		    		return true;
        		}
        		
	        	if(args[1].equals("blue")) {
		            final ConsoleCommandSender console1 = Bukkit.getServer().getConsoleSender();
		            final String command1 = "fill " + MakeAreaEvent.XYZRight.getBlockX() + " " + MakeAreaEvent.XYZRight.getBlockY() + " " + MakeAreaEvent.XYZRight.getBlockZ() + " " + MakeAreaEvent.XYZLeft.getBlockX() + " " + MakeAreaEvent.XYZLeft.getBlockY() + " " + MakeAreaEvent.XYZLeft.getBlockZ() + " minecraft:blue_wool";
		            Bukkit.dispatchCommand((CommandSender)console1, command1);

	        	}else if(args[1].equals("red")) {
		            final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		            final String command = "fill " + MakeAreaEvent.XYZRight.getBlockX() + " " + MakeAreaEvent.XYZRight.getBlockY() + " " + MakeAreaEvent.XYZRight.getBlockZ() + " " + MakeAreaEvent.XYZLeft.getBlockX() + " " + MakeAreaEvent.XYZLeft.getBlockY() + " " + MakeAreaEvent.XYZLeft.getBlockZ() + " minecraft:red_wool";
		            Bukkit.dispatchCommand((CommandSender)console, command);
	        	}
	        	

	            return true;
	            
	        case "setbound":
	        	
	        	if (args.length < 4){
		    		p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + "devi avere il numero giusto di argomenti, guarda /ktw help per l'usage");
		    		return true;
		    	}
        	
        		if(MakeAreaEvent.XYZRightbound == null && MakeAreaEvent.XYZLeftbound == null) {
		    		p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + "prima setta i capi cliccando");
		    		return true;
        		}
        		
	        	String team = args[1];
	        	String goforwars = args[2];
	        	String name = args[3];

	        	Location limitRight = MakeAreaEvent.XYZRightbound;
	        	Location limitLeft = MakeAreaEvent.XYZLeftbound;

	        	if (team.equals("blue"))
	        		game.setBlueArea(limitRight, limitLeft, goforwars);
	        	else if (team.equals("red"))
	        		game.setRedArea(limitRight, limitLeft, goforwars);

				YMLFile.write("arenas", name+"."+team+".go", goforwars);
				YMLFile.write("arenas", name+"."+team+".right.x", p.getLocation().getX());
				YMLFile.write("arenas", name+"."+team+".right.y", p.getLocation().getY());
				YMLFile.write("arenas", name+"."+team+".right.z", p.getLocation().getZ());

				YMLFile.write("arenas", name+"."+team+".left.x", p.getLocation().getX());
				YMLFile.write("arenas", name+"."+team+".left.y", p.getLocation().getY());
				YMLFile.write("arenas", name+"."+team+".left.z", p.getLocation().getZ());
		        	
	        	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + this.main.getConfig().getString("messages.setbound").replaceAll("&", "§"));
	        	
	        	return true;
	            
	        case "forcestart":	   
	        	
	            game.startMatch(p);
	           
	            new TimerGame(this.main, p, game).runTaskTimer(this.main, 0, 20);
	            new TimerArrows(this.main, p, game).runTaskTimer(this.main, 0, 40);
	            new TimerBlocks(this.main, p, game, sb, teamManager).runTaskTimer(this.main, 0, 20);

	        	return true;
	        	
	        default:
	            p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " +  "§cInvalid Command");
	            return true;
        }
    }
      
}
