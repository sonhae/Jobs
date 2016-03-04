package com.apex.rpg.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.apex.rpg.RPG;
import com.apex.rpg.jobs.JobType;

public class ConfigManager {
	private static FileConfiguration config;
	public static String MYSQL_HOST, MYSQL_PASSWORD, MYSQL_USER, MYSQL_DATABASE, MYSQL_PORT, MYSQL_PREFIX;
	public static int MAX_LEVEL;
	public static boolean USE_CACHE;	
	
	static {
		config = RPG.pl.getConfig();
		MYSQL_HOST = config.getString("database.host");
		MYSQL_PASSWORD = config.getString("database.password");
		MYSQL_USER = config.getString("database.user");
		MYSQL_PORT = config.getString("database.port");
	}

	public static double getXp(JobType job, String value){
		String path = job.toString() + "." + value + ".xp";
		return config.getDouble(path, -1);
	}
	public static double getWithdrawAmount(JobType job, String value){
		String path = job.toString() + "." + value + ".withdraw";
		return config.getDouble(path, -1);
	}
	public static double getAmount(JobType job, String value){
		String path = job.toString() + "." + value + ".amount";
		return config.getDouble(path, -1);
	}
	public static boolean contains(JobType job, String value){
		String path = job.toString() + "." + value;
		return config.contains(path);
	}
}
