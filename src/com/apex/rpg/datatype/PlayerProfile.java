package com.apex.rpg.datatype;

import java.util.HashMap;
import java.util.Map;

import com.apex.rpg.jobs.JobType;

public class PlayerProfile {
	private Map<JobType, Integer> jobLevel = new HashMap<JobType, Integer>();
	private Map<JobType, Float> jobXP = new HashMap<JobType, Float>();
	
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
