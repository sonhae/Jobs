package com.apex.rpg.jobs;

public enum JobType {
	HUNTER(HunterManager.class, "���"), MINER(MinerManager.class ,"ä��"), BUILDER(BuilderManager.class, "����"), FISHER(FisherManager.class, "����"), FARMER(FarmerManager.class, "���"), ALCHEMIST(AlchemistManager.class, "����");
	
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
