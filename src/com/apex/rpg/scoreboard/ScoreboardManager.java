package com.apex.rpg.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.apex.rpg.jobs.JobType;

public class ScoreboardManager {
	static final String LEVEL_XP = "남은 XP";
    static final String CURRENT_XP = "현재 XP";
	static final String CURRENT_LEVEL = "현재 레벨";
	static final Map<String, UserScoreboard> PLAYER_BOARDS = new HashMap<String, UserScoreboard>();
	
	
	//playerjoinevent에서 호출.
	public static void setup(Player p){
		PLAYER_BOARDS.put(p.getName(), new UserScoreboard(p.getName()));
	}
	
	public static void clear(UserScoreboard u){
		PLAYER_BOARDS.remove(u);
		
		if (u.closeTask != null){
			u.closeTask.cancel();
		}
	}
	
	//playerquitevent에서 호출.
	public static void logout(Player p){
		UserScoreboard u = PLAYER_BOARDS.remove(p.getName());
		
		if (u.closeTask != null){
			u.closeTask.cancel();
		}
		
	}
	
	public static void showPlayerStat(Player p, JobType job){
		UserScoreboard u = PLAYER_BOARDS.get(p.getName());
		u.setTmpBoard();
		u.setStats(job);
		changeBoard(u, 10);
	}
	public static void showPlayerAllstats(Player p, JobType job){
		UserScoreboard u = PLAYER_BOARDS.get(p.getName());
		u.setTmpBoard();
		u.setAllStats();
		changeBoard(u, 10);
	}
	
	public static void keepBoard(Player p){
		UserScoreboard u = PLAYER_BOARDS.get(p.getName());
		u.cancelClose();
	}
	
	public static void changeBoard(UserScoreboard us, int time){
		if (time == 0){
			us.show();
		}else{
			us.show(time);
		}
	}
}

