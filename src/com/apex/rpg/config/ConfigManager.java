package com.apex.rpg.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.apex.rpg.RPG;
import com.apex.rpg.datatype.action.ActionInfo;
import com.apex.rpg.datatype.action.BlockActionInfo;
import com.apex.rpg.datatype.action.EntityActionInfo;
import com.apex.rpg.datatype.action.ItemActionInfo;
import com.apex.rpg.datatype.jobs.JobType;

public class ConfigManager {
	private static FileConfiguration config;
	public static String MYSQL_HOST, MYSQL_PASSWORD, MYSQL_USER, MYSQL_DATABASE, MYSQL_PORT, MYSQL_PREFIX;
	public static int MAX_LEVEL;
	public static boolean USE_CACHE, USE_BUFFERED_PAY, HOOK_COREPROTECT;
	public static final String dot = ".";
	static {
		config = RPG.pl.getConfig();
		MYSQL_HOST = config.getString("database.host");
		MYSQL_PASSWORD = config.getString("database.password");
		MYSQL_USER = config.getString("database.user");
		MYSQL_PORT = config.getString("database.port");
		MYSQL_DATABASE = config.getString("database.db");
		MYSQL_PREFIX = config.getString("database.prefix");
		MAX_LEVEL = config.getInt("jobsetting.maxlevel");
		USE_CACHE = config.getBoolean("database.useCache");
		USE_BUFFERED_PAY = config.getBoolean("jobsetting.useBufferedPay");
		HOOK_COREPROTECT = config.getBoolean("jobsetting.hook-coreprotect");
	}
	
	
	@SuppressWarnings("deprecation")
	public static double getDouble(ActionInfo action,JobType job, IncomeType type){
		String str = "";
		if (action instanceof BlockActionInfo){
			BlockActionInfo a = (BlockActionInfo)action;
			Material m = a.getBlock().getType();
			str = m.name() + (a.needData() ? "-" + a.getBlock().getData() : "");
		}
		if (action instanceof EntityActionInfo){
			EntityActionInfo a = (EntityActionInfo)action;
			str = a.getEntity().getType().name();
		}
		if (action instanceof ItemActionInfo){
			ItemActionInfo a = (ItemActionInfo)action;
			str = a.getItem().getType().name();
		}
		return config.getDouble(job + dot + action.getAction()+ dot + str + type, -1);
	}
	public static boolean contains(JobType job, ActionInfo action){
		return getDouble(action,job ,IncomeType.XP) != -1;
	}
	public enum IncomeType{
		XP, MOMEY;
	}
}
