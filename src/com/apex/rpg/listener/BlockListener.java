package com.apex.rpg.listener;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.player.PlayerFishEvent;

import com.apex.rpg.player.RPGPlayer;
import com.apex.rpg.player.UserManager;

public class BlockListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		
		if (!e.getPlayer().hasMetadata("jobs") || e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		RPGPlayer p = UserManager.getPlayer(e.getPlayer());
		// ³ó»ç, Ã¤±¤
			if (p.getPlayer().hasPermission("jobs.user")){
				p.getFarmerManager().checkXp(b);	
				p.getMinerManager().checkXp(b);
			}		
	}
	@EventHandler
	public void onPlayerFish(PlayerFishEvent e){
		if (e.isCancelled()) return;
		Entity fish = e.getCaught();
		RPGPlayer p = UserManager.getPlayer(e.getPlayer());
		
		if (p == null || e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		
			//³¬½Ã
			if (p.getPlayer().hasPermission("jobs.user")){
				p.getFisherManager().checkXp(fish);
			}
	}
	public void onPlayerBrew(BrewEvent e){
	
	}
	public void onPlayerPlace(BlockPlaceEvent e){
		if (e.isCancelled()) return;
		Block b = e.getBlock();
		RPGPlayer p = UserManager.getPlayer(e.getPlayer());
		
		if (p == null || e.getPlayer().getGameMode() != GameMode.CREATIVE) return;
		
		// ºô´õ
			if (p.getPlayer().hasPermission("jobs.user")){
				p.getBuilderManager().checkXp(b);
			}	
	}
}