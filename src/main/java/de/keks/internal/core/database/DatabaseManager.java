package de.keks.internal.core.database;

import java.sql.Connection;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.ConfigValues;
import de.keks.internal.SkyConfig;
import de.keks.internal.core.database.mysql.SQLConnectionFactory;
import de.keks.internal.core.database.mysql.SQLConnectionHandler;
import de.keks.internal.core.database.mysql.SQLConnectionManager;

public class DatabaseManager {
    public static boolean setupManager() {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return startsql();
        }
        return startYAML();

    }

    private static boolean startYAML() {
        return true;

    }

    private static boolean startsql() {
        String db = CubitPlugin.inst().getConfig().getString("sql.database");
        String port = CubitPlugin.inst().getConfig().getString("sql.port");
        String host = CubitPlugin.inst().getConfig().getString("sql.host");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
        String username = CubitPlugin.inst().getConfig().getString("sql.username");
        String password = CubitPlugin.inst().getConfig().getString("sql.password");
        SQLConnectionFactory factory = new SQLConnectionFactory(url, username, password);
        SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
        SQLConnectionHandler handler = manager.getHandler("cubitdb", factory);

        try {
            Connection connection = handler.getConnection();
            String sql = "CREATE TABLE IF NOT EXISTS CubitDB (Id int NOT NULL AUTO_INCREMENT, UUID text, Lastlogin bigint, Lockedname text, Playername text, PRIMARY KEY (Id));";
            String sql2 = "CREATE TABLE IF NOT EXISTS CubitRegions (Id int NOT NULL AUTO_INCREMENT, region_id text, data bigint, PRIMARY KEY (Id));";
            String sql3 = "CREATE TABLE IF NOT EXISTS CubitFiles (Id int NOT NULL AUTO_INCREMENT, UUID text, region_id text, remote text, PRIMARY KEY (Id));";
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
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "==================CUBIT-ERROR================");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Unable to connect to database.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Pls check you mysql connection in config.yml.");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "==================CUBIT-ERROR================");
            if (CubitPlugin.inst().getConfig().getBoolean("sql.debugmode")) {
                e.printStackTrace();
            }
            return false;
        }

    }

}
