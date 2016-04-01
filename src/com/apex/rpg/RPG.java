package com.apex.rpg;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.database.CacheManager;
import com.apex.rpg.database.DatabaseManager;
import com.apex.rpg.database.SqlManager;
import com.apex.rpg.economy.BufferedManager;
import com.apex.rpg.economy.EconomyManager;
import com.apex.rpg.economy.VaultManager;
import com.apex.rpg.listener.BlockListener;
import com.apex.rpg.listener.EntityListener;
import com.apex.rpg.listener.PlayerListener;
import com.apex.rpg.listener.SelfListener;
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

	}
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		pl = this;
		if (hookEconomy()){
			getLogger().info("Vault 후킹됨");
		} else{
			getLogger().severe("Vault 후킹에 실패했습니다.플러그인을 종료합니다");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		setupDatabase();
		registerEvents();
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
	public void setupDatabase(){
		if (ConfigManager.USE_CACHE) {
			db = new CacheManager();
		} else {
			db = new SqlManager();
		}
	}
	public boolean hookEconomy(){
		if (Bukkit.getPluginManager().getPlugin("Vault") != null){
			econ = ConfigManager.USE_BUFFERED_PAY ? new BufferedManager() : new VaultManager();
			return econ.setupEconomy();
		}
		return false;
	}
	public void registerEvents(){
		Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		Bukkit.getPluginManager().registerEvents(new SelfListener(), this);
	}
}
