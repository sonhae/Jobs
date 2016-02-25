package com.apex.rpg.database;

import java.util.HashMap;
import java.util.UUID;

import com.apex.rpg.datatype.PlayerProfile;

public interface DatabaseManager {
	public void initialize();
	public void createProfile(String name, UUID uuid); 
	public HashMap<String, PlayerProfile> loadProfiles();
	public PlayerProfile loadProfile(String name);
	public PlayerProfile loadProfile(UUID uuid);
	public PlayerProfile loadProfile(String name, UUID uuid);
	public boolean saveProfile(PlayerProfile player);
}
