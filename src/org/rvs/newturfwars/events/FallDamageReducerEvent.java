package org.rvs.newturfwars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class FallDamageReducerEvent implements Listener {
	
    @EventHandler
    public void fallDamageEvent(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player) || e.getCause() != DamageCause.FALL) return;
    	
    	e.setDamage(e.getDamage() /  4);
    	
	}
}
