package com.apex.rpg;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.database.CacheManager;
import com.apex.rpg.database.DatabaseManager;
import com.apex.rpg.database.SqlManager;
import com.apex.rpg.economy.EconomyManager;
import com.apex.rpg.economy.VaultManager;

public class RPG extends JavaPlugin{
	public static RPG pl;
	private static DatabaseManager db;
	private static EconomyManager econ;
	public static float xpRate;
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
		if (Bukkit.getPluginManager().getPlugin("Vault") != null){
			econ = new VaultManager();
			if (!econ.setupEconomy()){
				System.out.println("Vault ��ŷ�� �����Ͽ����ϴ�. �÷������� �����մϴ�.");
				this.getServer().getPluginManager().disablePlugin(RPG.pl);
			} else {
				System.out.println("Vault ��ŷ ����");
			}
		}
	}
	public static DatabaseManager getDatabaseManager(){
		return db;
	}
	
}
