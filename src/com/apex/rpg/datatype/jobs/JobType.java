package com.apex.rpg.datatype.jobs;

import com.apex.rpg.jobs.AlchemistManager;
import com.apex.rpg.jobs.BuilderManager;
import com.apex.rpg.jobs.FarmerManager;
import com.apex.rpg.jobs.FisherManager;
import com.apex.rpg.jobs.HunterManager;
import com.apex.rpg.jobs.JobManager;
import com.apex.rpg.jobs.MinerManager;

public enum JobType {
	HUNTER(HunterManager.class, "헌터"), MINER(MinerManager.class ,"광부"), BUILDER(BuilderManager.class, "건축가"), FISHER(FisherManager.class, "낚시꾼"), FARMER(FarmerManager.class, "농부"), ALCHEMIST(AlchemistManager.class, "약쟁이");
	private Class<? extends JobManager> managerClass;
	private String jobName;
	
	public String getJobName() {
		return jobName;
	}

	public Class<? extends JobManager> getManagerClass() {
		return managerClass;
	}

	private JobType(Class<? extends JobManager> managerClass, String jobName){
		this.managerClass = managerClass;
		this.jobName = jobName;
	}
}
