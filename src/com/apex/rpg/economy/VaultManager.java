package com.apex.rpg.economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class VaultManager implements EconomyManager{
	private static Economy econ = null;
	
	public boolean setupEconomy() {
	    RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	    if (economyProvider != null) {
	        econ = economyProvider.getProvider();
	    }

	    return (econ != null);
	}
	
	public boolean deposit(RPGPlayer p, JobType type, String key) {
		// TODO Auto-generated method stub
		Double base = ConfigManager.getAmount(type, key);
		int max = ConfigManager.MAX_LEVEL;
		Double amount = base * (1 + (p.getJobsLevel(type) / max));
		EconomyResponse e = econ.depositPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);
		
		return e.transactionSuccess();
	}

	public boolean withdraw(RPGPlayer p, JobType type, String key) {
		// TODO Auto-generated method stub
		Double base = ConfigManager.getAmount(type, key+".withdraw");
		int max = ConfigManager.MAX_LEVEL;
		Double amount = base * (1 + (p.getJobsLevel(type) / max));
		EconomyResponse e = econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);
		
		return e.transactionSuccess();
	}

	public double getBalance(RPGPlayer p) {
		// TODO Auto-generated method stub
		return econ.getBalance(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()));
	}

}
