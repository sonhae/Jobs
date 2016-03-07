package com.apex.rpg.economy;

import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;

public interface EconomyManager {
	public void deposit(RPGPlayer p, JobType type, String key);
	public void withdraw(RPGPlayer p,JobType type,  String key);
	public double getBalance(RPGPlayer p);
	public boolean setupEconomy();
}
