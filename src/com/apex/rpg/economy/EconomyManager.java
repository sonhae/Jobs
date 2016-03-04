package com.apex.rpg.economy;

import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;

public interface EconomyManager {
	public boolean deposit(RPGPlayer p, JobType type, String key);
	public boolean withdraw(RPGPlayer p,JobType type,  String key);
	public double getBalance(RPGPlayer p);
	public boolean setupEconomy();
}
