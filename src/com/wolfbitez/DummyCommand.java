package com.wolfbitez;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class DummyCommand implements CommandExecutor {
	private Main plugin = Main.get();
	private ArrayList<Player> removeList = Main.getList();	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 0) {
				return false;
			}
			
			Player player = (Player) sender;
			World world = player.getWorld();
			
			if (args[0].toString().toLowerCase().equals("create")) {
				String name;
				if (args.length == 1) {
					name = "Dummy";
				}
				else {
					name = args[1].toString();
				}
				Location location = player.getLocation();
				LivingEntity dummy = (LivingEntity) world.spawnEntity(location, EntityType.ARMOR_STAND);
				dummy.setCustomName(ChatColor.GREEN + name);
				dummy.setCustomNameVisible(true);
				dummy.setGravity(false);
				dummy.getEquipment().setHelmet(new ItemStack(Material.TARGET));
				
				// yeah
				dummy.getPersistentDataContainer().set(new NamespacedKey(plugin, "dummy"), PersistentDataType.STRING, "true");

				player.sendMessage(ChatColor.GOLD + "Dummy " + ChatColor.RED + name + ChatColor.GOLD + " was successfully created.");
				return true;
				
			}
			
			else if (args[0].toString().toLowerCase().equals("remove")) {
				if (removeList.contains(player)) {
					player.sendMessage(ChatColor.GOLD + "Remove cancelled.");
					removeList.remove(player);
					return true;
				}
				else {
					removeList.add(player);
					player.sendMessage(ChatColor.GOLD + "Punch the dummy you wish to remove.");
					return true;
				}
			}
			
			else {
				return false;
			}
			
		}
		else {
			sender.sendMessage(ChatColor.DARK_RED + "Only players may use this command.");
			return true;
		}
	}
	
}
