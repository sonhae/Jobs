package com.apex.rpg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.database.CacheManager;
import com.apex.rpg.database.DatabaseManager;
import com.apex.rpg.database.SqlManager;
import com.apex.rpg.economy.EconomyManager;
import com.apex.rpg.economy.VaultManager;
import com.apex.rpg.listener.BlockListener;
import com.apex.rpg.listener.EntityListener;
import com.apex.rpg.listener.PlayerListener;
import com.apex.rpg.player.UserManager;
import com.apex.rpg.scoreboard.ScoreboardManager;

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
		this.saveDefaultConfig();
		pl = this;
		if (ConfigManager.USE_CACHE) {
			db = new CacheManager();
		} else {
			db = new SqlManager();
		}
		if (Bukkit.getPluginManager().getPlugin("Vault") != null){
			econ = new VaultManager();
			if (!econ.setupEconomy()){
				System.out.println("Vault 후킹에 실패하였습니다. 플러그인을 종료합니다.");
				this.getServer().getPluginManager().disablePlugin(RPG.pl);
			} else {
				System.out.println("Vault 후킹 성공");
			}
		}
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		for (Player p : Bukkit.getOnlinePlayers()){
			UserManager.setup(p);
			ScoreboardManager.setup(p);
		}
	}
	public static EconomyManager getEconomyManager(){
		return econ;
	}
	public static DatabaseManager getDatabaseManager(){
		return db;
	}
	
}
