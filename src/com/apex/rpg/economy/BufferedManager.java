package com.apex.rpg.economy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;

public class BufferedManager implements EconomyManager{

	ExecutorService exec = Executors.newCachedThreadPool();
	VaultManager vault;

	public boolean setupEconomy() {
		vault = new VaultManager();
		return vault.setupEconomy();
	}

	public void pay(final RPGPlayer p, final JobType type, final String key, final boolean withdraw) {
		// TODO Auto-generated method stub
		exec.execute(new Runnable() {
			public void run() {
				vault.pay(p, type, key, withdraw);
			}
		});
	}

}
