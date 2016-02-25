package com.apex.rpg.database;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.apex.rpg.datatype.PlayerProfile;

public class CacheManager implements DatabaseManager{

	private SqlManager sql;
	private HashMap<String, PlayerProfile> cache;
	private Executor exec;
	
	public void initialize() {
		// TODO Auto-generated method stub
		sql = new SqlManager();
		cache = sql.loadProfiles();
		exec = Executors.newCachedThreadPool();
	}

	public HashMap<String, PlayerProfile> loadProfiles() {
		// TODO Auto-generated method stub
		return cache;
	}

	public PlayerProfile loadProfile(String name) {
		// TODO Auto-generated method stub
		return loadProfile(name, null);
	}

	public PlayerProfile loadProfile(UUID uuid) {
		// TODO Auto-generated method stub
		return loadProfile("", uuid);
	}

	public PlayerProfile loadProfile(String name, UUID uuid) {
		if (cache.containsKey(name)) return cache.get(name);
		for (PlayerProfile p : cache.values()){
			if (p.getUUID().equals(uuid)) return p;
		}
		createProfile(name, uuid);
		return cache.put(name, new PlayerProfile(name, uuid));
	}

	public boolean saveProfile(final PlayerProfile player) {
		try {
			exec.execute(new Runnable() {
				public void run() {
					// TODO Auto-generated method stub
					sql.saveProfile(player);
				}
			});	
		} catch (Exception e){
			System.out.println("플레이어 저장 오류.");
			return false;
		}
		return true;
	}

	public void createProfile(final String name, final UUID uuid) {
		exec.execute(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				sql.createProfile(name, uuid);
			}
		});
	}

}
