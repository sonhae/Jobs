package com.apex.rpg.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.apex.rpg.RPG;
import com.apex.rpg.jobs.JobType;
import com.apex.rpg.player.RPGPlayer;
import com.apex.rpg.player.UserManager;

public class UserScoreboard {
	
	private Scoreboard board;
	public String playerName;
	private Objective statObj;
	
	private Scoreboard tmpBoard;
	public BukkitTask closeTask = null;
	public BukkitTask updateTask = null;
	private JobType jobType;
	
	private BoardType boardType;
	private class CloseTaskRunnable extends BukkitRunnable{

		public void run() {
			close();
			closeTask = null;
		}
		
	}
	private class UpdateTaskRunnable extends BukkitRunnable{

		public void run() {
			updateScoreboard();
			updateTask = null;
		}
		
	}
	public boolean equalsJobType(JobType job){
		return jobType == job;
	}
	public void setStats(JobType job){
		jobType = job;
		boardType = BoardType.STAT;
		newObjective(job.getJobName());
	}
	public void setAllStats(){
		jobType = null;
		boardType = BoardType.ALLSTATS;
		newObjective("³» Á¤º¸");
	}
	public void show(){
		show(0);
	}
	public void show(int time){
		Player p = Bukkit.getPlayer(playerName);
		if (p == null){
			ScoreboardManager.clear(this);
			return;
		}
		p.setScoreboard(board);
		if (time != 0){
			cancelClose();
			closeTask = new CloseTaskRunnable().runTaskLater(RPG.pl, time * 20L);
		}
	}
	public void setTmpBoard(){
		Player p = Bukkit.getPlayer(playerName);
		if (p == null){
			ScoreboardManager.clear(this);
			return;
		}
		Scoreboard tmpBoard = p.getScoreboard();
		if (tmpBoard == board){
			if (tmpBoard == null){
				this.tmpBoard = Bukkit.getScoreboardManager().getMainScoreboard();
			}
		} else {
			this.tmpBoard = tmpBoard;
		}
	}
	public void close(){
		Player p = Bukkit.getPlayer(playerName);
		if (p == null){
			ScoreboardManager.clear(this);
			return;
		}
		if (tmpBoard != null){
			p.setScoreboard(tmpBoard);
			tmpBoard = null;
		}
	}
	public void cancelClose(){
		if (closeTask != null) closeTask.cancel();
	}
	public void updateSoon(){
		if (updateTask == null){
			updateTask = new UpdateTaskRunnable().runTaskLater(RPG.pl, 2L);
		}
	}
	public void newObjective(String displayname){
		statObj.unregister();
		statObj = board.registerNewObjective("jobs", "dummy");
		if (displayname.length() > 32){
			displayname = displayname.substring(0,32);
		}
		statObj.setDisplayName(displayname);
		updateScoreboard();
		statObj.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	public boolean isBoardShown(){
		Player p = Bukkit.getPlayer(playerName);
		if (p == null){
			ScoreboardManager.clear(this);
			return false;
		}
		return p.getScoreboard() == board;
	}
	public boolean isJobStatBoard(){
		return boardType == BoardType.STAT;
	}
	public boolean isJobAllStatsBoard(){
		return boardType == BoardType.ALLSTATS;
	}
	public void updateScoreboard(){
		try {
			updateTask.cancel();
		} catch (Throwable e){
			
		}
		updateTask = null;
		Player p = Bukkit.getPlayer(playerName);
		if (p == null){
			ScoreboardManager.clear(this);
			return;
		}
		RPGPlayer rp = UserManager.getPlayer(playerName);
		if (rp != null){
			switch (boardType) {
			case STAT:
				int cxp = (int) rp.getJobsXP(jobType);
				statObj.getScore(ScoreboardManager.CURRENT_LEVEL).setScore(rp.getJobsLevel(jobType));
				statObj.getScore(ScoreboardManager.CURRENT_XP).setScore(cxp);
				statObj.getScore(ScoreboardManager.LEVEL_XP).setScore((int) (rp.getXpToLevelup(jobType) - cxp));
				break;
			case ALLSTATS:
				for (JobType j : JobType.values()){
					statObj.getScore(j.getJobName()).setScore(rp.getJobsLevel(j));
				}
				break;
			default:
				break;
			}
		}
	}
	public UserScoreboard(String name){
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		this.statObj = sb.registerNewObjective("jobs", "dummy");
		
		statObj.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.board = sb;
		this.playerName = name;
	}
	
}
