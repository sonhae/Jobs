package com.apex.rpg.jobs;

import com.apex.rpg.datatype.action.BlockActionInfo;
import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.RPGPlayer;

public class FarmerManager extends JobManager{

	public FarmerManager(RPGPlayer player) {
		super(player, JobType.FARMER);
		// TODO Auto-generated constructor stub
	}

	public void checkXp(BlockActionInfo b) {
		// TODO Auto-generated method stub
		b.setData(true);
	}
}
