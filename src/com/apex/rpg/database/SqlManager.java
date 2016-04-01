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
import com.apex.rpg.datatype.jobs.JobType;
import com.apex.rpg.datatype.player.PlayerProfile;

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
		initialize();
	}

	public void initialize() {
		// TODO Auto-generated method stub
		Statement st = null;
		try {
			String url = "jdbc:mysql://"+ host +":"+port+"/"+database+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useOldAliasMetadataBehavior=true";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user,password);
			st = conn.createStatement();
			String query = "CREATE TABLE IF NOT EXISTS `"+ prefix +"levels` ( "+
			"`user_id` INT NOT NULL , `alchemist` INT NOT NULL DEFAULT '1' ,"+
		    " `builder` INT NOT NULL DEFAULT '1' , `farmer` INT NOT NULL DEFAULT '1' "+
			", `fisher` INT NOT NULL DEFAULT '1' , `hunter` INT NOT NULL DEFAULT '1' ,"+
		    "`miner` INT NOT NULL DEFAULT '1' , PRIMARY KEY (`user_id`)) ";
			st.executeUpdate(query);
			query = "CREATE TABLE IF NOT EXISTS `"+ prefix +"expreience` ( "+
			"`user_id` INT NOT NULL , `alchemist` FLOAT NOT NULL DEFAULT '0' ,"+
		    " `builder` FLOAT NOT NULL DEFAULT '0' , `farmer` FLOAT NOT NULL DEFAULT '0' "+
			", `fisher` FLOAT NOT NULL DEFAULT '0' , `hunter` FLOAT NOT NULL DEFAULT '0' ,"+
		    "`miner` FLOAT NOT NULL DEFAULT '0' , PRIMARY KEY (`user_id`)) ";
			st.executeUpdate(query);
			query = "CREATE TABLE IF NOT EXISTS `"+ prefix +"users` ( "
					+ "`id` INT NOT NULL AUTO_INCREMENT , `name` TEXT NOT NULL ,"
					+ " `uuid` TEXT NOT NULL , PRIMARY KEY (`id`))";
			st.executeUpdate(query);
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("mysql ���� ������ ���Ͽ� �÷������� ����˴ϴ�");
			RPG.pl.getServer().getPluginManager().disablePlugin(RPG.pl);
		} finally {
			close(st);
		}
	}

	public boolean saveProfile(PlayerProfile player) {
		// TODO Auto-generated method stub
		PreparedStatement st = null;
		try {
			int id = getUserID(player.getPlayerName(), player.getUUID());
			String query = "UPDATE `" + prefix + "expreience` SET "
					+ "`alchemist`=?,`builder`=?, `farmer`=?,"
					+ "`fisher`=?,`hunter`=?,`miner`=? WHERE `user_id`=?";
			st = conn.prepareStatement(query);
			st.setFloat(1, player.getJobsXP(JobType.ALCHEMIST));
			st.setFloat(2, player.getJobsXP(JobType.BUILDER));
			st.setFloat(3, player.getJobsXP(JobType.FARMER));
			st.setFloat(4, player.getJobsXP(JobType.FISHER));
			st.setFloat(5, player.getJobsXP(JobType.HUNTER));
			st.setFloat(6, player.getJobsXP(JobType.MINER));
			st.setInt(7, id);
			st.executeUpdate();
			st.close();
			query = "UPDATE `" + prefix + "levels` SET "
					+ "`alchemist`=?,`builder`=?,"
					+ "`farmer`=?,`fisher`=?,`hunter`=?,`miner`=? WHERE `user_id`=?";
			st = conn.prepareStatement(query);
			st.setInt(1, player.getJobsLevel(JobType.ALCHEMIST));
			st.setInt(2, player.getJobsLevel(JobType.BUILDER));
			st.setInt(3, player.getJobsLevel(JobType.FARMER));
			st.setInt(4, player.getJobsLevel(JobType.FISHER));
			st.setInt(5, player.getJobsLevel(JobType.HUNTER));
			st.setInt(6, player.getJobsLevel(JobType.MINER));
			st.setInt(7, id);
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
		int a = 0;
		try {
			String query = "INSERT INTO `"+ prefix + "users`(`name`, `uuid`) VALUES (?, ?)";
			st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, name);
			st.setString(2, (uuid == null ? "" : uuid.toString()));
			st.executeUpdate();
			rs = st.getGeneratedKeys();
			if (rs.next()){
				a = rs.getInt(1);
			}
			st.close();
			rs.close();
			
			st = conn.prepareStatement("INSERT INTO `"+prefix+ "expreience`(`user_id`) VALUES (?)");
			st.setInt(1, a);
			st.executeUpdate();
			st.close();
			
			st = conn.prepareStatement("INSERT INTO `"+prefix+ "levels`(`user_id`) VALUES (?)");
			st.setInt(1, a);
			st.executeUpdate();
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
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
			+ "l.alchemist, l.builder, l.farmer, l.fisher, l.hunter, l.miner, u.uuid, u.name "
			+ "FROM " + prefix + "users u "
			+ "JOIN " + prefix + "expreience e ON(e.user_id = u.id) "
			+ "JOIN " + prefix + "levels l ON(l.user_id = u.id) "
			+ "WHERE u.name = ? OR u.uuid = ?";
			st = conn.prepareStatement(query);
			st.setString(1, name);
			st.setString(2, uuid == null ? "" : uuid.toString());
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
			+ "l.alchemist, l.builder, l.farmer, l.fisher, l.hunter, l.miner, u.uuid, u.name "
			+ "FROM " + prefix + "users u "
			+ "JOIN " + prefix + "expreience e ON(e.user_id = u.id) "
			+ "JOIN " + prefix + "levels l ON(l.user_id = u.id)";
			st = conn.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()){
				PlayerProfile p = fromResultset(rs);
				tempmap.put(p.getPlayerName(), p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			RPG.pl.getLogger().severe("mysql ���� ������ ���Ͽ� �÷������� ����˴ϴ�");
			RPG.pl.getServer().getPluginManager().disablePlugin(RPG.pl);
		} finally {
			// TODO: handle finally clause
			close(st);
			close(rs);
		}
		return tempmap;
	}

	public PlayerProfile fromResultset(ResultSet set) throws SQLException{
		final int xpoffset = 1;
		final int leveloffset = 7;
		final int uuidoffset = 13;
		final int nameoffset = 14;
		Map<JobType, Integer> levels = new EnumMap<JobType, Integer>(JobType.class);
		Map<JobType, Float> xps = new EnumMap<JobType, Float>(JobType.class);
		
		//xp ���� �Է�
		xps.put(JobType.ALCHEMIST, set.getFloat(xpoffset));
		xps.put(JobType.BUILDER, set.getFloat(xpoffset +1));
		xps.put(JobType.FARMER, set.getFloat(xpoffset + 2));
		xps.put(JobType.FISHER, set.getFloat(xpoffset + 3));
		xps.put(JobType.HUNTER, set.getFloat(xpoffset +4));
		xps.put(JobType.MINER, set.getFloat(xpoffset +5));
		
		//level ���� �Է�
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
