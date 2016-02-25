package com.apex.rpg.database;

import java.util.HashMap;

import com.apex.rpg.datatype.PlayerProfile;
import com.apex.rpg.jobs.JobType;

public class CacheManager implements DatabaseManager{

	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	public void addXP(String uuid, JobType type, float xp) {
		// TODO Auto-generated method stub
		
	}

	public void setXP(String uuid, JobType type, float xp) {
		// TODO Auto-generated method stub
		
	}

	public void removeXP(String uuid, JobType type, float xp) {
		// TODO Auto-generated method stub
		
	}

	public void setLevel(String uuid, JobType type, int level) {
		// TODO Auto-generated method stub
		
	}

	public void addLevel(String uuid, JobType type, int level) {
		// TODO Auto-generated method stub
		
	}

	public void removeLevel(String uuid, JobType type, int level) {
		// TODO Auto-generated method stub
		
	}

	public HashMap<String, PlayerProfile> loadProfiles() {
		// TODO Auto-generated method stub
		return null;
	}

	public PlayerProfile loadProfile(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean saveProfile(PlayerProfile player) {
		return false;
		// TODO Auto-generated method stub
		
	}

}
