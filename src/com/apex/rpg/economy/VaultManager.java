package com.apex.rpg.economy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.config.ConfigManager.IncomeType;
import com.apex.rpg.datatype.action.ActionInfo;
import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.RPGPlayer;
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
	
	public void pay(final RPGPlayer p, final ActionInfo action, JobType type) {
				Double base = ConfigManager.getDouble(action, type, IncomeType.MOMEY);
				int max = ConfigManager.MAX_LEVEL;
				Double amount = base * (1 + (p.getJobsLevel(type) / max));
				if(amount > 0){
					econ.depositPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);
				} else {
					econ.withdrawPlayer(Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId()), amount);	
				}
	}

}
