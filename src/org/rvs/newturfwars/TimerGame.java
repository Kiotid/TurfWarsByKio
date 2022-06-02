package org.rvs.newturfwars;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.rvs.newturfwars.events.GameStartEvent;

public class TimerGame extends BukkitRunnable {
    private int countdown;
	private List<Player> players;
	private GameStartEvent game;

    public TimerGame(Main main, Player player, GameStartEvent game) {
    	this.players = player.getWorld().getPlayers();
        this.countdown = 600;
        this.game = game;
    }

    @Override
    public void run() {

    	for (Player p : players) {
            p.setLevel(countdown);
    		p.setExp(0);
    	}
    	
    	if(countdown <= 0) {
    		game.endMatch(players);
    		this.cancel();
    		return;
    	}
    	
        countdown--; // decrement the counter
    }
}