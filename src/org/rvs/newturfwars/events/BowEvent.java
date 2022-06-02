package org.rvs.newturfwars.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.rvs.newturfwars.Main;
import org.rvs.newturfwars.TimerBlocks;
import org.rvs.newturfwars.manager.TeamManager;

public class BowEvent implements Listener
{

	private final TeamManager teamManager;
	private final GameStartEvent game;
	private final Main main;
	
	public BowEvent(final Main main, final GameStartEvent game, final TeamManager teamManager) {
		this.teamManager = teamManager;
		this.game = game;
		this.main = main;
	}
	
	@EventHandler
    public void onHit(EntityDamageByEntityEvent e)
    {
		
		if (game.isRunning()) {
			if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
				if(teamManager.getTeam((Player)e.getEntity()).equals(teamManager.getTeam((Player)e.getDamager()))) {
					e.setCancelled(true);
					return;
				}
	        }
			
	        if(TimerBlocks.buildTime) { 
	            e.setDamage(0);
	        	e.setCancelled(true);
	        }
			
	        if(!(e.getDamager() instanceof Projectile)) return;
	       
	       
	        Projectile p = (Projectile) e.getDamager();
		
			if(p.getType() == EntityType.ARROW)
			{
	            if(p.getShooter() instanceof Player)
	            {
	                if(e.getEntity() instanceof Player)
	                {
	                    if(p.getShooter().equals(e.getEntity()) || teamManager.getTeam((Player)e.getEntity()).equals(teamManager.getTeam((Player)p.getShooter()))) {
	                    	e.setCancelled(true);
	                    	return;
	                    }
	                      
	                    e.setDamage(150);
	                    
	                        
	                    if(teamManager.getTeam((Player)p.getShooter()).equals("blue")) {
	                    	
	                    	((Player)p.getShooter()).sendMessage("sei blu hai fatto una kill");
	                    	
	                        final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	                        String command = "";
	                        
	                        if (game.blueForwards.equals("x")) {
	            	            command = "fill " + game.limitLeftBlue.getBlockX() + " " + game.limitLeftBlue.getBlockY() + " " + game.limitLeftBlue.getBlockZ() + " " + (game.limitRightBlue.getBlockX()+1) + " " + game.limitRightBlue.getBlockY() + " " + game.limitRightBlue.getBlockZ() + " minecraft:blue_wool";
	                        } else if (game.blueForwards.equals("z")) {
	            	            command = "fill " + game.limitLeftBlue.getBlockX() + " " + game.limitLeftBlue.getBlockY() + " " + game.limitLeftBlue.getBlockZ() + " " + game.limitRightBlue.getBlockX() + " " + game.limitRightBlue.getBlockY() + " " + (game.limitRightBlue.getBlockZ()+1) + " minecraft:blue_wool";
	                        }
	                        Bukkit.dispatchCommand((CommandSender)console, command);

	                    } else if(teamManager.getTeam((Player)p.getShooter()).equals("red")) {
	                    	((Player)p.getShooter()).sendMessage("sei red hai fatto una kill");

	                        final ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
	                        String command = "";
	            	            if (game.blueForwards.equals("x")) {
	            	            command = "fill " + game.limitLeftRed.getBlockX() + " " + game.limitLeftRed.getBlockY() + " " + game.limitLeftRed.getBlockZ() + " " + (game.limitRightRed.getBlockX()+1) + " " + game.limitRightRed.getBlockY() + " " + game.limitRightRed.getBlockZ() + " minecraft:red_wool";
	                        } else if (game.blueForwards.equals("z")) {
	                        	command = "fill " + game.limitLeftRed.getBlockX() + " " + game.limitLeftRed.getBlockY() + " " + game.limitLeftRed.getBlockZ() + " " + game.limitRightRed.getBlockX() + " " + game.limitRightRed.getBlockY() + " " + (game.limitRightRed.getBlockZ()+1) + " minecraft:red_wool";
	                        }
	                        Bukkit.dispatchCommand((CommandSender)console, command);

	                    }
	                }
	            }
	        
	        }
		}
    }
	
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDeath(final PlayerDeathEvent e) {
    	if (!(e.getEntity() instanceof Player)) return;
        final Player killed = e.getEntity();
        if (killed.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
            final EntityDamageByEntityEvent byEntityEvent = (EntityDamageByEntityEvent)killed.getLastDamageCause();
            if (byEntityEvent.getDamager() instanceof Player) {
                final Player killer = (Player)byEntityEvent.getDamager();
            	e.setDeathMessage(this.main.getConfig().getString("messages.killmsg").replaceAll("&", "§").replace("[killer]", killer.getDisplayName()).replace("[victim]", killed.getDisplayName()));
            }
        }
    }
	 
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        
        if(!(e.getEntity() instanceof Projectile)) return;
    	
        Arrow arrow = (Arrow)e.getEntity();
        arrow.remove();
        
    }
}
