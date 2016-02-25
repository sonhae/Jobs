package com.apex.rpg;

import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.database.CacheManager;
import com.apex.rpg.database.DatabaseManager;
import com.apex.rpg.database.SqlManager;
import com.apex.rpg.datatype.PlayerProfile;

public class RPG extends JavaPlugin{
	public static RPG pl;
	private static DatabaseManager db;
	
	public static BukkitScheduler getScheduler(){
		return pl.getServer().getScheduler();
	}
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		pl = this;
		if (ConfigManager.USE_CACHE) {
			db = new CacheManager();
		} else {
			db = new SqlManager();
		}
	}
	public static DatabaseManager getDatabaseManager(){
		return db;
	}
	
}
