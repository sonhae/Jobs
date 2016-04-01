package com.apex.rpg.economy;

import com.apex.rpg.datatype.action.ActionInfo;
import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.RPGPlayer;

public interface EconomyManager { 
	public void pay(RPGPlayer p, ActionInfo action, JobType type);
	public boolean setupEconomy();
}
