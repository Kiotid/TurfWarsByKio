package org.rvs.newturfwars;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.rvs.newturfwars.events.GameStartEvent;
import org.rvs.newturfwars.manager.ScoreboardUpdater;
import org.rvs.newturfwars.manager.TeamManager;

public class TimerBlocks extends BukkitRunnable {
    private int countdown;
    private int timeMax = 60;
	private List<Player> players;
	public static boolean buildTime;
	private GameStartEvent game;
    private ScoreboardUpdater sb;
    private final Main main;
    private final TeamManager teamManager;
    
    public TimerBlocks(final Main main, Player player, GameStartEvent game, ScoreboardUpdater sb, TeamManager teamManager) {
        TimerBlocks.buildTime = false;
    	this.players = player.getWorld().getPlayers();
        this.countdown = timeMax;
    	this.game = game;
    	this.main = main;
        this.sb = sb;
        this.teamManager = teamManager;
    }

    @Override
    public void run() {

    	for (Player p : players) {

     		if (!game.isRunning()) {

    	    	return;
    		}
     		
        	sb.updateScoreboard(p, countdown, TimerBlocks.buildTime ? "build" : "war");
            
            if(countdown <= 5 && countdown != 0)
            	if (TimerBlocks.buildTime)
            		p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " +this.main.getConfig().getString("messages.wartimecount").replaceAll("&", "§").replace("[countdown]", Integer.toString(countdown)));
            	else
            		p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " +this.main.getConfig().getString("messages.buildtimecount").replaceAll("&", "§").replace("[countdown]", Integer.toString(countdown)));
        
            if (countdown <= 0 || countdown == timeMax) {

                if(TimerBlocks.buildTime) {
                	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " +this.main.getConfig().getString("messages.wartime").replaceAll("&", "§"));
                }else {
                	p.sendMessage(this.main.getConfig().getString("messages.prefix").replaceAll("&", "§") + " " +this.main.getConfig().getString("messages.buildtime").replaceAll("&", "§"));
                	p.getInventory().addItem(new ItemStack(this.teamManager.getTeam(p).equals("Blue") ? Material.BLUE_WOOL : Material.RED_WOOL, 32));
                }
                
            }

    	}
    	

        if (countdown <= 0 || countdown == timeMax) {
            if(TimerBlocks.buildTime) {
            	TimerBlocks.buildTime = false;
            }else {
            	TimerBlocks.buildTime = true;
            }
        }
        
        if (countdown <= 0)
        	countdown = timeMax;
    	
    	if(!game.isRunning()) {
        	for (Player p : players) {
        	
    	    	sb.resetScoreboard(p);
    	    	
        	}
    		this.cancel();
    		return;
    	}
    	
    		
        countdown--;

    }
}