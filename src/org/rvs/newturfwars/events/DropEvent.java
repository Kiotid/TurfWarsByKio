package org.rvs.newturfwars.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener{

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
    	e.setCancelled(true);
    }
    
    @EventHandler
    public void onInventoryClickEvent(final InventoryClickEvent e) {
    	e.setCancelled(true);
    }

}
