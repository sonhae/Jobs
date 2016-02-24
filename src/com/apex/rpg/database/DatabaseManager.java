package com.apex.rpg.database;

import java.util.HashMap;

import com.apex.rpg.datatype.PlayerProfile;
import com.apex.rpg.jobs.JobType;

public interface DatabaseManager {
	public void initialize();
	public void addXP(String uuid, JobType type, float xp);
	public void setXP(String uuid, JobType type, float xp);
	public void removeXP(String uuid, JobType type, float xp);
	public void setLevel(String uuid, JobType type, int level);
	public void addLevel(String uuid, JobType type, int level);
	public void removeLevel(String uuid, JobType type, int level);
	public HashMap<String, PlayerProfile> loadProfiles();
	public PlayerProfile loadProfile(String uuid);
	public void saveProfile(PlayerProfile player);
}
