package com.wolfbitez;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	static ArrayList<Player> removeList = new ArrayList<Player>();
	
	@Override
	public void onEnable() {
		plugin = this;
		getServer().getPluginManager().registerEvents(new DamageListener(), this);
		getCommand("dummy").setExecutor(new DummyCommand());
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
	public static Main get() {
		return plugin;
	}
	
	public static ArrayList<Player> getList() {
		return removeList;
	}
	
}
