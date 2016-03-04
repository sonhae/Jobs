package com.apex.rpg.player;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.apex.rpg.RPG;
import com.apex.rpg.jobs.AlchemistManager;
import com.apex.rpg.jobs.BuilderManager;
import com.apex.rpg.jobs.FarmerManager;
import com.apex.rpg.jobs.FisherManager;
import com.apex.rpg.jobs.HunterManager;
import com.apex.rpg.jobs.JobManager;
import com.apex.rpg.jobs.JobType;
import com.apex.rpg.jobs.MinerManager;
import com.apex.rpg.player.PlayerProfile;

public class RPGPlayer {
	private Player player;
	private PlayerProfile profile;
	private final Map<JobType, JobManager> jobmanagers = new HashMap<JobType, JobManager>();
	public RPGPlayer(Player player) {
		super();
		this.player = player;
		this.profile = RPG.getDatabaseManager().loadProfile(player.getName());
		try {
			for (JobType j : JobType.values()){
				jobmanagers.put(j, j.getManagerClass().getConstructor(PlayerProfile.class).newInstance(this));
			}
		}
		catch (Exception e){
			System.out.println("플레이어 로딩 오류");
		}
		
	}
	public void checkLevelUp(JobType type){
		if (getJobsXP(type) < getXpToLevelup(type)){
			return;
		}
		
		int levelgained = 0;
		
		while (getJobsXP(type) >= getXpToLevelup(type)){
			if (profile.reachedMaxLevel(type)) {
				profile.setXp(type, 0.0f);
				break;
			}
			profile.levelUp(type);
			levelgained++;
		}
		
		player.sendMessage("§e"+type.getJobName() + "의 레벨이 " + levelgained + " 올라 " + getJobsLevel(type) + "레벨이 되었습니다");
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 0.75f, 0.5f);
	}
	public Player getPlayer() {
		return player;
	}
	
	public void xpGain(JobType type, float xp){
		profile.addXp(type, xp * getXpRate() * RPG.xpRate);
	}
	
	public float getXpRate(){
		return profile.getXpRate();
	}
	public void setXpRate(float rate){
		profile.setXpRate(rate);
	}
	public int getJobsLevel(JobType type){
		return profile.getJobsLevel(type);
	}
	public float getXpToLevelup(JobType type){
		return profile.getXpToLevelup(type);
	}
	public float getJobsXP(JobType type){
		return profile.getJobsXP(type);
	}
	public PlayerProfile getProfile() {
		return profile;
	}
	public AlchemistManager getAlchmeistManager(){
		return (AlchemistManager) jobmanagers.get(JobType.ALCHEMIST);
	}
	public BuilderManager getBuilderManager(){
		return (BuilderManager) jobmanagers.get(JobType.BUILDER);
	}
	public FarmerManager getFarmerManager(){
		return (FarmerManager) jobmanagers.get(JobType.FARMER);
	}
	public FisherManager getFisherManager(){
		return (FisherManager) jobmanagers.get(JobType.FISHER);
	}
	public HunterManager getHunterManager(){
		return (HunterManager) jobmanagers.get(JobType.HUNTER);
	}
	public MinerManager getMinerManager(){
		return (MinerManager) jobmanagers.get(JobType.MINER);
	}
	
}
