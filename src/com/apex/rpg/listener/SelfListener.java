package com.apex.rpg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import com.apex.rpg.event.PlayerLevelUpEvent;
import com.apex.rpg.event.PlayerXpGainEvent;
import com.apex.rpg.scoreboard.ScoreboardManager;

public class SelfListener implements Listener{
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerXpGain(PlayerXpGainEvent e){
		ScoreboardManager.handleXpGain(e.getPlayer().getPlayer(), e.getJob());
	}
	@EventHandler
	public void onPlayerLevelUp(PlayerLevelUpEvent e){
		ScoreboardManager.handleLevelUp(e.getPlayer().getPlayer(), e.getJob());
	}
}
