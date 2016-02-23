package com.apex.rpg;

import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import com.apex.rpg.datatype.PlayerProfile;

public class RPG extends JavaPlugin{
	private HashMap<String, PlayerProfile> cache;
	public static RPG pl;
}
