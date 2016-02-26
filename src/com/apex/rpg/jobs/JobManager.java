package com.apex.rpg.jobs;

import org.bukkit.entity.Player;

import com.apex.rpg.player.RPGPlayer;

public abstract class JobManager {	
	protected RPGPlayer player;
	protected JobType type;
	
	public JobManager(RPGPlayer player, JobType type) {
		super();
		this.player = player;
		this.type = type;
	}
	public Player getPlayer(){
		return player.getPlayer();
	}
	public int getJobsLevel(){
		return player.getProfile().getJobsLevel(type);
	}
	public void giveXp(int xp){
		
	}
	public abstract void pay();
}
