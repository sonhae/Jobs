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

import com.apex.rpg.datatype.action.BlockActionInfo;
import com.apex.rpg.datatype.action.ActionInfo.Actions;
import com.apex.rpg.datatype.player.RPGPlayer;
import com.apex.rpg.player.UserManager;

public class BlockListener implements Listener{

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerBreak(BlockBreakEvent e){
		Block b = e.getBlock();
		
		if (!e.getPlayer().hasMetadata("jobs") || e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		RPGPlayer p = UserManager.getPlayer(e.getPlayer());
		// ���, ä��
			if (p.getPlayer().hasPermission("jobs.user")){
				BlockActionInfo a = new BlockActionInfo(Actions.BREAK, b);
				p.getFarmerManager().checkXp(a);	
				p.getMinerManager().checkXp(a);
			}		
	}
	@EventHandler
	public void onPlayerFish(PlayerFishEvent e){
	
		if (e.isCancelled()) return;
		Entity fish = e.getCaught();
		if (!e.getPlayer().hasMetadata("jobs") || e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		RPGPlayer p = UserManager.getPlayer(e.getPlayer());
		
			//����
			if (p.getPlayer().hasPermission("jobs.user")){
				p.getFisherManager().checkXp(fish);
			}
	}
	public void onPlayerBrew(BrewEvent e){
	
	}
	public void onPlayerPlace(BlockPlaceEvent e){
		if (e.isCancelled()) return;
		Block b = e.getBlock();

		if (!e.getPlayer().hasMetadata("jobs") || e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		RPGPlayer p = UserManager.getPlayer(e.getPlayer());
		
		// ����
		BlockActionInfo a = new BlockActionInfo(Actions.PLACE, b);
			if (p.getPlayer().hasPermission("jobs.user")){
				p.getBuilderManager().checkXp(b);
			}	
	}
}