package com.apex.rpg.event;

import org.bukkit.Bukkit;

import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;

public class EventManager {
	public static boolean handleXpGain(RPGPlayer p, JobType job, float xp){
		PlayerXpGainEvent e = new PlayerXpGainEvent(p, job, xp);
		Bukkit.getPluginManager().callEvent(e);
		if (!e.isCancelled()){
			p.getProfile().addXp(job, e.getXp());
		}
		return !e.isCancelled();
	}
	public static void handleLevelUp(RPGPlayer p, JobType job, int gained){
		PlayerLevelUpEvent e = new PlayerLevelUpEvent(p, job, gained);
		Bukkit.getPluginManager().callEvent(e);
	}
}
