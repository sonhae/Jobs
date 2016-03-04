package com.apex.rpg.jobs;

public enum JobType {
	HUNTER(HunterManager.class, "사냥"), MINER(MinerManager.class ,"채집"), BUILDER(BuilderManager.class, "건축"), FISHER(FisherManager.class, "낚시"), FARMER(FarmerManager.class, "농사"), ALCHEMIST(AlchemistManager.class, "제약");
	
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
