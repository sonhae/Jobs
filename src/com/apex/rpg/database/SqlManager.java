package com.apex.rpg.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.apex.rpg.RPG;
import com.apex.rpg.config.ConfigManager;
import com.apex.rpg.datatype.PlayerProfile;
import com.apex.rpg.jobs.JobType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlManager implements DatabaseManager {
	private Connection conn = null;
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

	public boolean saveProfile(PlayerProfile player) {
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		try {
			String query = "UPDATE `" + prefix + "expreiences` a, `" + prefix + "levels` b, `" + prefix + "users` "
					+ "SET a.alchemist=?, a.builder=?, a.farmer=?, a.fisher=?, a.hunter=?, a.miner=?,"
					+ "b.alchemist=?, b.builder=?, b.farmer=?, b.fisher=?, b.hunter=?, b.miner=?"
					+ "WHERE a.user_id = b.user_id = c.id AND c.uuid = ?";
			st = conn.prepareStatement(query);
			st.setFloat(1, player.getJobsXP(JobType.ALCHEMIST));
			st.setFloat(2, player.getJobsXP(JobType.BUILDER));
			st.setFloat(3, player.getJobsXP(JobType.FARMER));
			st.setFloat(4, player.getJobsXP(JobType.FISHER));
			st.setFloat(5, player.getJobsXP(JobType.HUNTER));
			st.setFloat(6, player.getJobsXP(JobType.MINER));
			st.setInt(7, player.getJobsLevel(JobType.ALCHEMIST));
			st.setInt(8, player.getJobsLevel(JobType.BUILDER));
			st.setInt(9, player.getJobsLevel(JobType.FARMER));
			st.setInt(10, player.getJobsLevel(JobType.FISHER));
			st.setInt(11, player.getJobsLevel(JobType.HUNTER));
			st.setInt(12, player.getJobsLevel(JobType.MINER));
			st.setString(13, player.getUUID().toString());
			return (st.executeUpdate() == 0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			close(st);
		}
		return false;
	}

	public void createProfile(String name, UUID uuid){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "INSERT INTO `"+ prefix + "users`(`name`, `uuid`) VALUES (?, ?)";
			st = conn.prepareStatement(query);
			st.setString(1, name);
			st.setString(2, uuid.toString());
			rs = st.getGeneratedKeys();
			int a = rs.getInt(1);
			st.close();
			rs.close();
			
			st = conn.prepareStatement("INSERT INTO `"+prefix+ "experience`(`user_id`) VALUES (?)");
			st.setInt(1, a);
			st.executeUpdate();
			st.close();
			
			st = conn.prepareStatement("INSERT INTO `"+prefix+ "levels`(`user_id`) VALUES (?)");
			st.setInt(1, a);
			st.executeUpdate();
			st.close();
			
		} catch (SQLException e) {
			
		} 
	}
	public PlayerProfile loadProfile(String name){
		return loadProfile(name, null);
	}
	public PlayerProfile loadProfile(UUID uuid){
		return loadProfile("", uuid);
	}
	public PlayerProfile loadProfile(String name, UUID uuid) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "SELECT e.alchemist, e.builder, e.farmer, e.fisher, e.hunter, e.miner, "
			+ "l.alchemist, l.builder, l.farmer, l.fisher, l.hunter, l.miner, u.uuid, u.name"
			+ "FROM jobs_users u "
			+ "JOIN jobs_expreience e ON(e.user_id = u.id) "
			+ "JOIN jobs_levels l ON(l.user_id = u.id) "
			+ "WHERE u.name = ? OR u.uuid = ?";
			st = conn.prepareStatement(query);
			st.setString(1, name);
			st.setString(2, uuid.toString());
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
		createProfile(name, uuid);
		return new PlayerProfile(name, uuid);
	}
	public HashMap<String, PlayerProfile> loadProfiles() {
		Statement st = null;
		ResultSet rs = null;
		HashMap<String, PlayerProfile> tempmap = new HashMap<String, PlayerProfile>();
		try {
			String query = "SELECT e.alchemist, e.builder, e.farmer, e.fisher, e.hunter, e.miner, "
			+ "l.alchemist, l.builder, l.farmer, l.fisher, l.hunter, l.miner, u.uuid, u.name"
			+ "FROM jobs_users u "
			+ "JOIN jobs_expreience e ON(e.user_id = u.id) "
			+ "JOIN jobs_levels l ON(l.user_id = u.id)";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()){
				PlayerProfile p = fromResultset(rs);
				tempmap.put(p.getPlayerName(), p);
			}
		} catch (SQLException e) {
			System.out.println("mysql 접속 오류로 인하여 플러그인이 종료됩니다");
			RPG.pl.getServer().getPluginManager().disablePlugin(RPG.pl);
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
		final int nameoffset = 13;
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
		
		return new PlayerProfile(levels, xps, set.getString(nameoffset), UUID.fromString(set.getString(uuidoffset)));
	}

	public int getUserID(String name, UUID uuid){
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM "+ prefix +"users WHERE uuid=?";
			st = conn.prepareStatement(query);
			st.setString(1, uuid.toString());
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
}
