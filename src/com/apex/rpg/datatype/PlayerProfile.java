package com.apex.rpg.datatype;

import java.util.HashMap;
import java.util.Map;

import com.apex.rpg.jobs.JobType;

public class PlayerProfile {
	private Map<JobType, Integer> jobLevel;
	private Map<JobType, Float> jobXP;
	
	public PlayerProfile(Map<JobType, Integer> jobLevel, Map<JobType, Float> jobXP, String UUID) {
		super();
		this.jobLevel = jobLevel;
		this.jobXP = jobXP;
		this.UUID = UUID;
	}
	public PlayerProfile(String uuid){
		super();
		this.jobLevel = new HashMap<JobType, Integer>();
		this.jobXP = new HashMap<JobType, Float>();
		this.UUID = uuid;
		for (JobType j : JobType.values()){
			jobLevel.put(j, 0);
			jobXP.put(j, 0f);
		}
	}
	public String getUUID() {
		return UUID;
	}
	private String UUID;
	
	public int getJobsLevel(JobType type){
		return jobLevel.get(type);
	}
	public void addLevel(JobType type, int level){
		int lvl = (jobLevel.get(type) + level);
		jobLevel.put(type, (lvl > 100) ? 100 : lvl);
	}
	public void removeLevel(JobType type, int level){
		int lvl = ((jobLevel.get(type) - level));
		jobLevel.put(type, (lvl < 0) ? 0 : lvl);
	}
	public void setLevel(JobType type, int level){
		if (level < 0 || level > 100) return;
		jobLevel.put(type, level);
	}
	public void save(){
		 
	}
}
