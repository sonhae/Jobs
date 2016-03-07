package com.apex.rpg.jobs;

import org.bukkit.block.Block;

import com.apex.rpg.RPG;
import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.player.RPGPlayer;
import com.gmail.nossr50.mcMMO;

public class MinerManager extends JobManager{

	public MinerManager(RPGPlayer player) {
		super(player, JobType.MINER);
		// TODO Auto-generated constructor stub
	}
	
	public void checkXp(Block b){
		String key = b.getType() + ":" + b.getData();
		if (!mcMMO.getPlaceStore().isTrue(b) && ConfigManager.contains(type, key)){
			RPG.getEconomyManager().deposit(player, type, key);
			player.xpGain(type, ConfigManager.getXp(type, key));
			
		}
	}
}
