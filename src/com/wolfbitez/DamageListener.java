package com.wolfbitez;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.persistence.PersistentDataType;

public class DamageListener implements Listener {
	
	private Main plugin = Main.get();
	private ArrayList<Player> removeList = Main.getList();
		
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getEntity().getPersistentDataContainer().has(new NamespacedKey(plugin, "dummy"), PersistentDataType.STRING)) {
			if (removeList.contains(event.getDamager())) {
				event.getDamager().sendMessage(ChatColor.GOLD + "Dummy " + ChatColor.GREEN + event.getEntity().getCustomName() + ChatColor.GOLD + " was removed.");
				event.getEntity().remove();
				removeList.remove(event.getDamager());
				return;
			}
			else if (event.getDamager() instanceof Projectile) {
					Projectile projectile = (Projectile) event.getDamager();
					if (projectile.getShooter() instanceof Player) {
						Player player = (Player) projectile.getShooter();
						player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1f, 1f);
						player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);
						player.sendMessage(ChatColor.GOLD + "You damaged " + event.getEntity().getName() + ChatColor.GOLD + " for " + ChatColor.RED + Math.floor(event.getDamage())/2 + ChatColor.GOLD + " hearts (" + ChatColor.RED + Math.round(Math.floor(event.getDamage())) + ChatColor.GOLD + " attack damage).");
						return;
					}
				}
				
				else if (event.getDamager() instanceof Player) {
					Player player = (Player) event.getDamager();
					player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1f, 1f);
					player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);
					player.sendMessage(ChatColor.GOLD + "You damaged " + event.getEntity().getName() + ChatColor.GOLD + " for " + ChatColor.RED + Math.floor(event.getDamage())/2 + ChatColor.GOLD + " hearts (" + ChatColor.RED + Math.round(Math.floor(event.getDamage())) + ChatColor.GOLD + " attack damage).");
					return;
				}

		}
		
		// if it doesn't contain the entity, just ignore
		else {
			return;
		}
	} // end of entitydamagebyentity
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity().getPersistentDataContainer().has(new NamespacedKey(plugin, "dummy"), PersistentDataType.STRING)) {
			/* 
			 * This was originally a check to see if lava/fire/lightning stuck
			 * But, since we never want to damage the entity, we can safely cancel it
			*/
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityInterract(PlayerArmorStandManipulateEvent event) {
		if (event.getRightClicked().getPersistentDataContainer().has(new NamespacedKey(plugin, "dummy"), PersistentDataType.STRING)) {
			if (event.getPlayer().hasPermission("trainingdummy.admin")) {
				return;
			}
			else {
				event.getPlayer().sendMessage(ChatColor.RED + "You do not have permission to modify this training dummy!");
				event.setCancelled(true);
			}
		}
		else {
			return;
		}
	}
	
}
