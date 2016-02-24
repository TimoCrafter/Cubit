package de.keks.internal.core.database.mysql;

import java.sql.Connection;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.core.database.mysql.SQLConnectionFactory;
import de.keks.internal.core.database.mysql.SQLConnectionHandler;
import de.keks.internal.core.database.mysql.SQLConnectionManager;

public class SQLSetup {

	public static boolean start() {
		String db = ConfigValues.sqlDatabase;
		String port = String.valueOf(ConfigValues.sqlPort);
		String host = ConfigValues.sqlHostname;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
		String username = ConfigValues.sqlUsername;
		String password = ConfigValues.sqlPassword;
		SQLConnectionFactory factory = new SQLConnectionFactory(url, username, password);
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		SQLConnectionHandler handler = manager.getHandler("cubitdb", factory);

		try {
			Connection connection = handler.getConnection();
			String sql = "CREATE TABLE IF NOT EXISTS cubitDB (Id int NOT NULL AUTO_INCREMENT, UUID text, Lastlogin bigint, Lockedname text, Playername text, PRIMARY KEY (Id));";
			String sql2 = "CREATE TABLE IF NOT EXISTS cubitRegions (Id int NOT NULL AUTO_INCREMENT, region_id text, uuid text, data bigint, PRIMARY KEY (Id));";
			String sql3 = "CREATE TABLE IF NOT EXISTS cubitFiles (Id int NOT NULL AUTO_INCREMENT, UUID text, region_id text, PRIMARY KEY (Id));";
			Statement action = connection.createStatement();
			action.executeUpdate(sql);
			action.executeUpdate(sql2);
			action.executeUpdate(sql3);
			action.close();
			handler.release(connection);
			CubitPlugin.inst().getLogger().info("[Module] Database loaded!");
			return true;

		} catch (Exception e) {
			CubitPlugin.inst().getLogger().info("[Module] Database error!");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "==================Cubit-ERROR================");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Unable to connect to database.");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Pls check you mysql connection in config.yml.");
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "==================Cubit-ERROR================");
			if (ConfigValues.sqlDebugmode) {
				e.printStackTrace();
			}
			return false;
		}

	}

}
