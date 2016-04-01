package com.apex.rpg.player;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.apex.rpg.RPG;
import com.apex.rpg.datatype.player.RPGPlayer;

public class UserManager {
	
	public static RPGPlayer getPlayer(UUID uuid){
		Player p = Bukkit.getPlayer(uuid);
		if (p != null){
			return getPlayer(p);
		}
		return null;
	}
	public static RPGPlayer getPlayer(Player player){
			return (RPGPlayer) player.getMetadata("jobs").get(0).value();
	}
	public static RPGPlayer getPlayer(String name){
		Player p = Bukkit.getPlayerExact(name);
		if (p != null){
			return getPlayer(p);
		}
		return null;
	}
	
	public static void saveAll(){
		for (Player p : Bukkit.getOnlinePlayers()){
			getPlayer(p).getProfile().save();
		}
	}
	
	public static void setup(Player p){
		RPGPlayer r = new RPGPlayer(p, RPG.getDatabaseManager().loadProfile(p.getName(), p.getUniqueId()));
		p.setMetadata("jobs", new FixedMetadataValue(RPG.pl, r));
	}
	
	public static void remove(Player p){
		p.removeMetadata("jobs", RPG.pl);
	}
	
}
