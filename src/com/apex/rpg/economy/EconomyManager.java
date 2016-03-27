package com.apex.rpg.economy;

import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;

public interface EconomyManager {
	public void pay(RPGPlayer p, JobType type, String key, boolean withdraw);
	public boolean setupEconomy();
}
