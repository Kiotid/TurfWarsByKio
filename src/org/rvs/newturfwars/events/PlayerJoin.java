package org.rvs.newturfwars.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.rvs.newturfwars.Main;
import org.rvs.newturfwars.manager.ScoreboardUpdater;
import org.rvs.newturfwars.manager.YMLFile;

public class PlayerJoin implements Listener
{
    private Main main;
	private GameStartEvent game;
	private ScoreboardUpdater sb;

    public PlayerJoin(final Main main, GameStartEvent game, final ScoreboardUpdater sb) {
        this.main = main;
        this.game = game;
        this.sb = sb;
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer();
		        
		if(game.isRunning()) {
			if(!player.getGameMode().equals(GameMode.SPECTATOR)) player.setGameMode(GameMode.SPECTATOR);

			player.sendMessage(this.main.getConfig().getString("messages.spec").replaceAll("&", "§")); 
			player.teleport(new Location(player.getWorld(), YMLFile.readDouble("spawns", "spec.x"), YMLFile.readDouble("spawns", "spec.y"), YMLFile.readDouble("spawns", "spec.z")));
        	player.getInventory().clear();
		}else {
			if(!player.getGameMode().equals(GameMode.SURVIVAL)) player.setGameMode(GameMode.SURVIVAL);
			
        	player.teleport(new Location(player.getWorld(), YMLFile.readDouble("spawns", "lobby.x"), YMLFile.readDouble("spawns", "lobby.y"), YMLFile.readDouble("spawns", "lobby.z")));
        	player.sendMessage(this.main.getConfig().getString("messages.lobby").replaceAll("&", "§"));
        	sb.resetScoreboard(player);
        	player.setLevel(0);
        	player.setExp(0);
        	player.getInventory().clear();
		}
    }
    //this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " + 
	
}
