package com.apex.rpg.economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.apex.rpg.RPG;
import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;
import com.apex.rpg.economy.EconomyManager;

import net.milkbowl.vault.economy.Economy;

public class VaultManager implements EconomyManager{
	private Economy econ = null;
	
	public boolean setupEconomy() {
	    RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	    if (economyProvider != null) {
	        econ = economyProvider.getProvider();
	    }

	    return (econ != null);
	}
	
	public void pay(final RPGPlayer p, final JobType type, final String key, boolean withdraw) {
				Double base = ConfigManager.getAmount(type, withdraw ? key + ".withdraw" : key);
				int max = ConfigManager.MAX_LEVEL;
				Double amount = base * (1 + (p.getJobsLevel(type) / max));
				if(!withdraw){
					econ.depositPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);
				} else {
					econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);	
				}
	}

}
