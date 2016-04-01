package com.apex.rpg.jobs;

import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.datatype.action.BlockActionInfo;
import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.RPGPlayer;

import net.coreprotect.database.Lookup;

public class MinerManager extends JobManager{

	public MinerManager(RPGPlayer player) {
		super(player, JobType.MINER);
		// TODO Auto-generated constructor stub
	}
	
	public void checkXp(BlockActionInfo action){
		if ((!ConfigManager.HOOK_COREPROTECT || Lookup.who_placed_cache(action.getBlock()).isEmpty()) && ConfigManager.contains(type, action)){
			pay(action);
			giveXp(action);
		}
	}
}
