package com.apex.rpg.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;

public class PlayerXpGainEvent extends Event implements Cancellable{

	public PlayerXpGainEvent(RPGPlayer player, JobType job, float xp) {
		super();
		this.player = player;
		this.job = job;
		this.xp = xp;
	}

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private RPGPlayer player;
	private JobType job;
	private float xp;
	
	
	public float getXp() {
		return xp;
	}

	public void setXp(float xp) {
		this.xp = xp;
	}

	public RPGPlayer getPlayer() {
		return player;
	}

	public JobType getJob() {
		return job;
	}

	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return cancelled;
	}

	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub
		this.cancelled = arg0;
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
