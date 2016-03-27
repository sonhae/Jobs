package com.apex.rpg.jobs;

import org.bukkit.block.Block;

import com.apex.rpg.RPG;
import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.player.RPGPlayer;

import net.coreprotect.database.Lookup;

public class MinerManager extends JobManager{

	public MinerManager(RPGPlayer player) {
		super(player, JobType.MINER);
		// TODO Auto-generated constructor stub
	}
	
	public void checkXp(Block b){
		String key = b.getType() + ":" + b.getData();
		if (Lookup.who_placed_cache(b).isEmpty() && ConfigManager.contains(type, key)){
			RPG.getEconomyManager().pay(player, type, key, false);
			player.xpGain(type, ConfigManager.getXp(type, key));
		}
	}
}
