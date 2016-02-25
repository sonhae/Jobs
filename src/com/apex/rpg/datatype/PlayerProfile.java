package com.apex.rpg.datatype;

import java.util.UUID;

import java.util.HashMap;
import java.util.Map;

import com.apex.rpg.RPG;
import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.jobs.JobType;

public class PlayerProfile {
	private final String playerName;
	private Map<JobType, Integer> jobLevel;
	private Map<JobType, Float> jobXP;
	private boolean changed;
	
	public PlayerProfile(Map<JobType, Integer> jobLevel, Map<JobType, Float> jobXP, String name, UUID UUID) {
		super();
		this.playerName = name;
		this.jobLevel = jobLevel;
		this.jobXP = jobXP;
		this.UUID = UUID;
		changed = false;
	}
	public String getPlayerName() {
		return playerName;
	}
	public PlayerProfile(String name, UUID uuid){
		super();
		this.playerName = name;
		this.jobLevel = new HashMap<JobType, Integer>();
		this.jobXP = new HashMap<JobType, Float>();
		this.UUID = uuid;
		for (JobType j : JobType.values()){
			jobLevel.put(j, 0);
			jobXP.put(j, 0f);
		}
	}
	public UUID getUUID() {
		return UUID;
	}
	private UUID UUID;

	public float getJobsXP(JobType type){
		return jobXP.get(type);
	}
	public float getXpToLevelup(JobType type){
		int lvl = (jobLevel.get(type));
		return ((3 * lvl + 2) * lvl * 100) + lvl;
	}
	public boolean reachedMaxLevel(JobType type){
		return getJobsLevel(type) >= ConfigManager.MAX_LEVEL;
	}
	public void levelUp(JobType type){
		changed = true;
		float xp = getXpToLevelup(type);
		jobLevel.put(type, jobLevel.get(type) + 1);
		jobXP.put(type,  jobXP.get(type) - xp);
	}
	public int getJobsLevel(JobType type){
		return jobLevel.get(type);
	}
	public void addLevel(JobType type, int level){
		if (reachedMaxLevel(type))return;
		int lvl = (jobLevel.get(type) + level);
		if (lvl >= ConfigManager.MAX_LEVEL) lvl= ConfigManager.MAX_LEVEL;
		modifyJobs(type, lvl);
	}
	public void removeLevel(JobType type, int level){
		int lvl = ((jobLevel.get(type) - level));
		modifyJobs(type, (lvl < 0) ? 0 : lvl);
	}
	public void modifyJobs(JobType job, int level){
		changed = true;
		if (level < 0) level = 0;
		jobLevel.put(job, level);
		jobXP.put(job, 0.0f);
	}
	public void addXp(JobType type, float xp){
		changed = true;
		jobXP.put(type, jobXP.get(type)+ xp);
	}
	public void setXp(JobType type, float xp){
		changed = true;
		jobXP.put(type, xp);
	}
	public void save(){
		 if (changed)RPG.getDatabaseManager().saveProfile(this);
	}
	public boolean isChanged() {
		return changed;
	}
}
