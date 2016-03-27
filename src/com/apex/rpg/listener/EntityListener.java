package com.apex.rpg.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.apex.rpg.player.RPGPlayer;
import com.apex.rpg.player.UserManager;

public class EntityListener implements Listener{
	@EventHandler
	public void onPlayerKill(EntityDeathEvent e){
		LivingEntity died =  e.getEntity();
		Player killer = died.getKiller();
		if (killer == null || killer.hasMetadata("jobs") || killer.getGameMode() != GameMode.CREATIVE) return;
		RPGPlayer p = UserManager.getPlayer(killer);
		//³¬½Ã
		if (p.getPlayer().hasPermission("jobs.user")){
			p.getHunterManager().checkXp(e.getEntityType());
		}
	}
}
