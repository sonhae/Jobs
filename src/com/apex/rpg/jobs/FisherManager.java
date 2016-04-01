package com.apex.rpg.jobs;

import org.bukkit.entity.Entity;

import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.RPGPlayer;

public class FisherManager extends JobManager{

	public FisherManager(RPGPlayer player) {
		super(player, JobType.FISHER);
		// TODO Auto-generated constructor stub
	}

	public void checkXp(Entity fish){
		
	}
}
