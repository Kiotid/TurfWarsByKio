package org.rvs.newturfwars.events;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import org.rvs.newturfwars.Main;
import org.rvs.newturfwars.manager.TeamManager;

public class RejectEvent implements Listener{

	private final Main main;
	private TeamManager teamManager;

	public RejectEvent(final Main main, final TeamManager teamManager){
		this.teamManager = teamManager;
		this.main = main;
	}
	
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e) {
    	if(e.getPlayer().getLocation().getBlock().getType().equals(Material.RED_WOOL)){ //|| !e.getPlayer().getLocation().getBlock().getType().equals(Material.BLUE_WOOL)) return;
    	
			double mult = -1.3;
	        
			int z1 = e.getPlayer().getLocation().getBlockZ();
			int z2 = (int) e.getPlayer().getLocation().getZ();
			int x1 = e.getPlayer().getLocation().getBlockX();
			int x2 = (int) e.getPlayer().getLocation().getX();
			int n1 = z1 - z2;
		    int n2 = x1 - x2;
			 
		    Vector v = e.getPlayer().getLocation().getDirection();
		    v.setY(-1);
			   
		    if (n1 < 0 && n2 < 0) {
		        v.setZ(-1);
		    } else if (n1 >= 0) {
		        v.setZ(1);
		    }
		    if (n2 < 0 && n1 < 0) {
		    	v.setX(-1);
	
		    } else if (n2 >= 0) {
		        v.setX(1);
		    }
	    	
		    e.getPlayer().setVelocity(v.multiply(mult));
		    
		    e.getPlayer().sendMessage("sbalza");
	
	    	/*if(e.getPlayer().getLocation().getBlock().getType() == Material.BLUE_WOOL && this.teamManager.getTeam(e.getPlayer()).equals("Red")){
			    e.getPlayer().setVelocity(v.multiply(mult));
	    	}else if(e.getPlayer().getLocation().getBlock().getType() == Material.RED_WOOL && this.teamManager.getTeam(e.getPlayer()).equals("Blue")){
			    e.getPlayer().setVelocity(v.multiply(mult));
	    	}*/
    	}

    }

}
