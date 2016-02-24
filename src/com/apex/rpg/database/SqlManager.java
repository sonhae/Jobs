package com.apex.rpg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apex.rpg.RPG;
import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.datatype.PlayerProfile;
import com.apex.rpg.jobs.JobType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlManager implements DatabaseManager {
	Connection conn = null;
	private final String user;
	private final String password;
	private final String host;
	private final String database;
	private final String prefix;
	private final String port;
	
	public SqlManager() {
		super();
		user = ConfigManager.MYSQL_USER;
		host = ConfigManager.MYSQL_HOST;
		password = ConfigManager.MYSQL_PASSWORD;
		database = ConfigManager.MYSQL_DATABASE;
		prefix = ConfigManager.MYSQL_PREFIX;
		port = ConfigManager.MYSQL_PORT;
	}

	public void initialize() {
		// TODO Auto-generated method stub
		Statement st = null;
		try {
			String url = "jdbc:mysql://"+ host +":"+port+"/"+database+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
			Class.forName("com.mysql.jdbc.driver");
			conn = DriverManager.getConnection(url, user,password);
			st = conn.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS `Jobs`.`"+ prefix +"levels` ( "+
			"`user_id` INT NOT NULL , `alchemist` INT NOT NULL DEFAULT '0' ,"+
		    " `builder` INT NOT NULL DEFAULT '0' , `farmer` INT NOT NULL DEFAULT '0' "+
			", `fisher` INT NOT NULL DEFAULT '0' , `hunter` INT NOT NULL DEFAULT '0' ,"+
		    "`miner` INT NOT NULL DEFAULT '0' ) ";
			st.executeUpdate(query);
			query = "CREATE TABLE IF NOT EXISTS `Jobs`.`"+ prefix +"expreience` ( "+
			"`user_id` INT NOT NULL , `alchemist` INT NOT NULL DEFAULT '0' ,"+
		    " `builder` INT NOT NULL DEFAULT '0' , `farmer` INT NOT NULL DEFAULT '0' "+
			", `fisher` INT NOT NULL DEFAULT '0' , `hunter` INT NOT NULL DEFAULT '0' ,"+
		    "`miner` INT NOT NULL DEFAULT '0' ) ";
			st.executeUpdate(query);
			query = "ALTER TABLE `jobs_users` CHANGE `id` `id` INT(11) NOT NULL AUTO_INCREMENT";
			st.executeUpdate(query);
		} catch (Exception e){
			System.out.println("mysql 접속 오류로 인하여 플러그인이 종료됩니다");
			RPG.pl.getServer().getPluginManager().disablePlugin(RPG.pl);
		} finally {
			close(st);
		}
	}

	public void saveProfile(PlayerProfile player) {
		// TODO Auto-generated method stub

	}

	public PlayerProfile createProfile(String uuid){
		// TODO
		return null;
	}
	public PlayerProfile loadProfile(String uuid) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "SELECT e.alchemist, e.builder, e.farmer, e.fisher, e.hunter, e.miner, "
			+ "l.alchemist, l.builder, l.farmer, l.fisher, l.hunter, l.miner, u.uuid "
			+ "FROM jobs_users u "
			+ "JOIN jobs_expreience e ON(e.user_id = u.id) "
			+ "JOIN jobs_levels l ON(l.user_id = u.id) "
			+ "WHERE u.name = ?";
			st = conn.prepareStatement(query);
			st.setString(1, uuid);
			rs = st.executeQuery();
			if (rs.next()){
				return fromResultset(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			close(st);
			close(rs);
		}
		return createProfile(uuid); 
	}
	public HashMap<String, PlayerProfile> loadProfiles() {
		Statement st = null;
		ResultSet rs = null;
		HashMap<String, PlayerProfile> tempmap = new HashMap<String, PlayerProfile>();
		try {
			String query = "SELECT e.alchemist, e.builder, e.farmer, e.fisher, e.hunter, e.miner, "
			+ "l.alchemist, l.builder, l.farmer, l.fisher, l.hunter, l.miner, u.uuid "
			+ "FROM jobs_users u "
			+ "JOIN jobs_expreience e ON(e.user_id = u.id) "
			+ "JOIN jobs_levels l ON(l.user_id = u.id)";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()){
				PlayerProfile p = fromResultset(rs);
				tempmap.put(p.getUUID(), p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			close(st);
			close(rs);
		}
		return tempmap;
	}

	public PlayerProfile fromResultset(ResultSet set) throws SQLException{
		final int xpoffset = 0;
		final int leveloffset = 6;
		final int uuidoffset = 12;
	
		Map<JobType, Integer> levels = new EnumMap<JobType, Integer>(JobType.class);
		Map<JobType, Float> xps = new EnumMap<JobType, Float>(JobType.class);
		
		//xp 정보 입력
		xps.put(JobType.ALCHEMIST, set.getFloat(xpoffset));
		xps.put(JobType.BUILDER, set.getFloat(xpoffset +1));
		xps.put(JobType.FARMER, set.getFloat(xpoffset + 2));
		xps.put(JobType.FISHER, set.getFloat(xpoffset + 3));
		xps.put(JobType.HUNTER, set.getFloat(xpoffset +4));
		xps.put(JobType.MINER, set.getFloat(xpoffset +5));
		
		//level 정보 입력
		levels.put(JobType.ALCHEMIST, set.getInt(leveloffset));
		levels.put(JobType.BUILDER, set.getInt(leveloffset +1));
		levels.put(JobType.FARMER, set.getInt(leveloffset + 2));
		levels.put(JobType.FISHER, set.getInt(leveloffset + 3));
		levels.put(JobType.HUNTER, set.getInt(leveloffset +4));
		levels.put(JobType.MINER, set.getInt(leveloffset +5));
		
		return new PlayerProfile(levels, xps, set.getString(uuidoffset));
	}

	public int getUserID(String uuid){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM "+ prefix +"users WHERE uuid=?";
			st = conn.prepareStatement(query);
			st.setString(1, uuid);
			rs = st.executeQuery();
			if (rs.next()){
				return rs.getInt("id"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			close(st);
			close(rs);
		}
		return -1;
	}
	public void close(AutoCloseable ac){
		try {
			if (ac != null){
				ac.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addXP(String uuid, JobType type, float xp) {
		// TODO Auto-generated method stub
		
	}

	public void setXP(String uuid, JobType type, float xp) {
		// TODO Auto-generated method stub
		
	}

	public void removeXP(String uuid, JobType type, float xp) {
		// TODO Auto-generated method stub
		
	}

	public void setLevel(String uuid, JobType type, int level) {
		// TODO Auto-generated method stub
		
	}

	public void addLevel(String uuid, JobType type, int level) {
		// TODO Auto-generated method stub
		
	}

	public void removeLevel(String uuid, JobType type, int level) {
		// TODO Auto-generated method stub
		
	}
}
