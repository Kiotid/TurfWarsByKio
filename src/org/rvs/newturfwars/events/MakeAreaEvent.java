package org.rvs.newturfwars.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class MakeAreaEvent implements Listener{
	
	public static Location XYZRight;
	public static Location XYZLeft;	
	public static Location XYZRightbound;
	public static Location XYZLeftbound;
    
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{
		
		if(e.getPlayer().getInventory().getItemInMainHand().getType().equals((Object)Material.PURPLE_DYE))
		{
				if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
	
					if(!e.getClickedBlock().getLocation().equals(XYZRight)) {
						XYZRight = e.getClickedBlock().getLocation();
						e.getPlayer().sendMessage("Set Right");
					}
				}else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
	
					if(!e.getClickedBlock().getLocation().equals(XYZLeft)) {
	
						XYZLeft = e.getClickedBlock().getLocation();
						e.getPlayer().sendMessage("Set Left");
					}
				}
				
			
			e.setCancelled(true);

		}else if(e.getPlayer().getInventory().getItemInMainHand().getType().equals((Object)Material.YELLOW_DYE)) {
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {

				if(!e.getClickedBlock().getLocation().equals(XYZRightbound)) {
					XYZRightbound = e.getClickedBlock().getLocation();
					e.getPlayer().sendMessage("Set Right");
				}
			}else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

				if(!e.getClickedBlock().getLocation().equals(XYZLeftbound)) {

					XYZLeftbound = e.getClickedBlock().getLocation();
					e.getPlayer().sendMessage("Set Left");
				}
			}
			
		
			e.setCancelled(true);
		}

	}
}
