package com.apex.rpg.jobs;

import org.bukkit.entity.Player;

import com.apex.rpg.RPG;
import com.apex.rpg.datatype.action.ActionInfo;
import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.RPGPlayer;

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
	public void giveXp(ActionInfo action){
		player.xpGain(type, action);
	}
	public void pay(ActionInfo action){
		RPG.getEconomyManager().pay(player, action, type);
	}
}
