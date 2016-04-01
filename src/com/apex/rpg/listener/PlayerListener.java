package com.apex.rpg.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.apex.rpg.datatype.player.RPGPlayer;
import com.apex.rpg.player.UserManager;
import com.apex.rpg.scoreboard.ScoreboardManager;

public class PlayerListener implements Listener{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		UserManager.setup(e.getPlayer());
     	ScoreboardManager.setup(e.getPlayer());
		
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		RPGPlayer p = UserManager.getPlayer(e.getPlayer());
		p.getProfile().save();
		UserManager.remove(e.getPlayer());
		ScoreboardManager.logout(e.getPlayer());
	}
}
