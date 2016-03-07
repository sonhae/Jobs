package com.apex.rpg.database;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.apex.rpg.player.PlayerProfile;

public class CacheManager implements DatabaseManager{

	public CacheManager() {
		initialize();
	}

	private SqlManager sql;
	private HashMap<String, PlayerProfile> cache;
	private Executor exec;
	
	public void initialize() {
		// TODO Auto-generated method stub
		sql = new SqlManager();
		cache = sql.loadProfiles();
		System.out.println(cache.size()+"명의 플레이어를 로딩중..");
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

	public PlayerProfile loadProfile(final String name, final UUID uuid) {
		if (cache.containsKey(name)) return cache.get(name);
		if (uuid != null){
			for (PlayerProfile p : cache.values()){
				if (p.getUUID().equals(uuid)) return p;
			}
		}
		exec.execute(new Runnable() {
			public void run() {
				cache.put(name, sql.loadProfile(name, uuid));
			}
		});
		return cache.get(name);
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
