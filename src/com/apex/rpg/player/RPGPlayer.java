package com.apex.rpg.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.apex.rpg.RPG;
import com.apex.rpg.jobs.AlchemistManager;
import com.apex.rpg.jobs.BuilderManager;
import com.apex.rpg.jobs.FarmerManager;
import com.apex.rpg.jobs.FisherManager;
import com.apex.rpg.jobs.HunterManager;
import com.apex.rpg.jobs.JobManager;
import com.apex.rpg.jobs.JobType;
import com.apex.rpg.jobs.MinerManager;
import com.apex.rpg.player.PlayerProfile;

public class RPGPlayer {
	private Player player;
	private PlayerProfile profile;
	private final Map<JobType, JobManager> jobmanagers = new HashMap<JobType, JobManager>();
	public RPGPlayer(Player player) {
		super();
		this.player = player;
		try {
			for (JobType j : JobType.values()){
				jobmanagers.put(j, j.getManagerClass().getConstructor(PlayerProfile.class).newInstance(this));
			}
		}
		catch (Exception e){
			e.printStackTrace();
			RPG.pl.getServer().getPluginManager().disablePlugin(RPG.pl);
		}
		
	}
	public void checkLevelUp(JobType type){
		if (profile.getJobsXP(type) < profile.getXpToLevelup(type)){
			return;
		}
		
		int levelgained = 0;
		
		while (profile.getJobsXP(type) >= profile.getXpToLevelup(type)){
			if (profile.reachedMaxLevel(type)) {
				profile.setXp(type, 0.0f);
				break;
			}
			profile.levelUp(type);
			levelgained++;
		}
	}
	public Player getPlayer() {
		return player;
	}
	public PlayerProfile getProfile() {
		return profile;
	}
	public AlchemistManager getAlchmeishManager(){
		return (AlchemistManager) jobmanagers.get(JobType.ALCHEMIST);
	}
	public BuilderManager getBuilderManager(){
		return (BuilderManager) jobmanagers.get(JobType.BUILDER);
	}
	public FarmerManager getFarmerManager(){
		return (FarmerManager) jobmanagers.get(JobType.FARMER);
	}
	public FisherManager getFisherManager(){
		return (FisherManager) jobmanagers.get(JobType.FISHER);
	}
	public HunterManager getHunterManager(){
		return (HunterManager) jobmanagers.get(JobType.HUNTER);
	}
	public MinerManager getMinerManager(){
		return (MinerManager) jobmanagers.get(JobType.MINER);
	}
	
}
