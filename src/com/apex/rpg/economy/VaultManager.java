package com.apex.rpg.economy;

import java.util.Queue;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.apex.rpg.RPG;
import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;
import com.apex.rpg.economy.EconomyManager;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class VaultManager implements EconomyManager{
	private Economy econ = null;
	
	public boolean setupEconomy() {
	    RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	    if (economyProvider != null) {
	        econ = economyProvider.getProvider();
	    }

	    return (econ != null);
	}
	
	public void deposit(final RPGPlayer p, final JobType type, final String key) {
		Bukkit.getScheduler().runTaskLater(RPG.pl, new Runnable() {
			
			public void run() {
				Double base = ConfigManager.getAmount(type, key);
				int max = ConfigManager.MAX_LEVEL;
				Double amount = base * (1 + (p.getJobsLevel(type) / max));
				EconomyResponse e = econ.depositPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);
			}
		}, 2L);
	}

	public void withdraw(final RPGPlayer p, final JobType type, final String key) {
		Bukkit.getScheduler().runTaskLater(RPG.pl, new Runnable() {
			
			public void run() {
				Double base = ConfigManager.getAmount(type, key+".withdraw");
				int max = ConfigManager.MAX_LEVEL;
				Double amount = base * (1 + (p.getJobsLevel(type) / max));
				EconomyResponse e = econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);
			}
		}, 2L);

	}

	public double getBalance(RPGPlayer p) {
		// TODO Auto-generated method stub
		return econ.getBalance(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()));
	}

}
