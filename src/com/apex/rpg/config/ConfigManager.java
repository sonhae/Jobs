package com.apex.rpg.config;

import org.bukkit.configuration.file.FileConfiguration;

import com.apex.rpg.RPG;

public class ConfigManager {
	
	private static RPG rpg;
	private static FileConfiguration config;
	public static String MYSQL_HOST, MYSQL_PASSWORD, MYSQL_USER, MYSQL_DATABASE, MYSQL_PORT, MYSQL_PREFIX;
	public static int MAX_LEVEL;
	public static boolean USE_CACHE;
	
	public ConfigManager(RPG pl){
		rpg = pl;
		config = rpg.getConfig();
		MYSQL_HOST = config.getString("database.host");
		MYSQL_PASSWORD = config.getString("database.password");
		MYSQL_USER = config.getString("database.user");
		MYSQL_PORT = config.getString("database.port");
	}

}
