package com.apex.rpg.jobs;

public enum JobType {
	HUNTER(HunterManager.class), MINER(MinerManager.class), BUILDER(BuilderManager.class), FISHER(FisherManager.class), FARMER(FarmerManager.class), ALCHEMIST(AlchemistManager.class);
	
	private Class<? extends JobManager> managerClass;
	
	
	public Class<? extends JobManager> getManagerClass() {
		return managerClass;
	}

	private JobType(Class<? extends JobManager> managerClass){
		this.managerClass = managerClass;
	}
}
