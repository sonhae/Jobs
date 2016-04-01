package com.apex.rpg.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.RPGPlayer;

public class PlayerLevelUpEvent extends Event{

	public PlayerLevelUpEvent(RPGPlayer player, JobType job, int gained) {
		super();
		this.player = player;
		this.job = job;
		this.gained = gained;
	}
	private static final HandlerList handlers = new HandlerList();
	public RPGPlayer getPlayer() {
		return player;
	}
	public JobType getJob() {
		return job;
	}
	private RPGPlayer player;
	private JobType job;
	private int gained;

	public int getGained() {
		return gained;
	}
	public int getLevel(){
		return player.getJobsLevel(job);
	}
    public static HandlerList getHandlerList() {
        return handlers;
    }
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}
}
