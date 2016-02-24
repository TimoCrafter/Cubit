package de.keks.internal.core.database.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SQLInject {

	public static void savePlayer(Player player) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn.prepareStatement(
					"SELECT Lastlogin FROM cubitDB WHERE UUID = '" + player.getUniqueId().toString() + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				updatePlayer(player);
			} else {
				setupPlayer(player);
			}
			result.close();
			sql.close();
			manager.release("cubitdb", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static long getLastlogin(Player p) {
		long lastlogin = 0;
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn.prepareStatement(
					"SELECT Lastlogin FROM cubitDB WHERE UUID = '" + p.getUniqueId().toString() + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {

				lastlogin = result.getLong(1);
			} else {

				lastlogin = p.getLastPlayed();
			}
			result.close();
			sql.close();
			manager.release("cubitdb", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastlogin;

	}

	public static long getLastloginfromUUID(UUID uuid) {
		long lastlogin = 0;
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn.prepareStatement("SELECT Lastlogin FROM cubitDB WHERE UUID = '" + uuid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {

				lastlogin = result.getLong(1);
			} else {
				Date now = new Date();
				lastlogin = now.getTime();
			}
			result.close();
			sql.close();
			manager.release("cubitdb", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastlogin;

	}

	public static long getLastloginfromString(String p) {
		long lastloginString = 0;
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			@SuppressWarnings("deprecation")
			OfflinePlayer player = Bukkit.getOfflinePlayer(p);
			if (player != null) {
				Connection conn = manager.getConnection("cubitdb");
				PreparedStatement sql = conn.prepareStatement(
						"SELECT Lastlogin FROM cubitDB WHERE UUID = '" + player.getUniqueId().toString() + "';");
				ResultSet result = sql.executeQuery();
				if (result.next()) {

					lastloginString = result.getLong(1);
				} else {
					Date now = new Date();
					lastloginString = now.getTime();
				}
				result.close();
				sql.close();
				manager.release("cubitdb", conn);
			} else {
				Date now = new Date();
				lastloginString = now.getTime();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastloginString;

	}

	public static void setupPlayer(Player player) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Date now = new Date();
			String name = player.getName().toString();
			String uuid = player.getUniqueId().toString();
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn
					.prepareStatement("INSERT INTO cubitDB (UUID, Lastlogin, Lockedname, Playername) VALUES ('" + uuid
							+ "', " + now.getTime() + ", '" + name + "', '" + name + "');");
			sql.executeUpdate();
			sql.close();
			manager.release("cubitdb", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updatePlayer(Player player) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Date now = new Date();
			String uuid = player.getUniqueId().toString();
			String name = player.getName().toString();
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn.prepareStatement("UPDATE cubitDB SET Lastlogin = " + now.getTime()
					+ ", Playername =  '" + name + "' WHERE UUID = '" + uuid + "';");
			sql.executeUpdate();
			sql.close();
			manager.release("cubitdb", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void setOffer(String regionid, double data, UUID uuid) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn
					.prepareStatement("SELECT data FROM cubitRegions WHERE region_id = '" + regionid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn.prepareStatement("UPDATE cubitRegions SET data = " + data + ", uuid = "
						+ uuid.toString() + " WHERE region_id = '" + regionid + "';");
				update.executeUpdate();
				update.close();
			} else {
				PreparedStatement insert = conn
						.prepareStatement("INSERT INTO cubitRegions (region_id, data, uuid) VALUES ('" + regionid
								+ "', '" + data + "', '" + uuid.toString() + "');");
				insert.executeUpdate();
				insert.close();
			}
			result.close();
			sql.close();
			manager.release("cubitdb", conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void removeOffer(String regionid) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn
					.prepareStatement("SELECT data FROM cubitRegions WHERE region_id = '" + regionid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				PreparedStatement update = conn
						.prepareStatement("DELETE FROM cubitRegions WHERE region_id = '" + regionid + "';");
				update.executeUpdate();
				update.close();
			}
			result.close();
			sql.close();
			manager.release("cubitdb", conn);

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static double getOffer(String regionid) {
		double offerdata = 0;
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn
					.prepareStatement("SELECT data FROM cubitRegions WHERE region_id = '" + regionid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				offerdata = result.getFloat(1);
			} else {
				offerdata = 0;
			}
			result.close();
			sql.close();
			manager.release("cubitdb", conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return offerdata;
	}

	public static boolean isOffered(String regionid) {
		boolean isoffered = false;
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sql = conn
					.prepareStatement("SELECT data FROM cubitRegions WHERE region_id = '" + regionid + "';");
			ResultSet result = sql.executeQuery();
			if (result.next()) {
				isoffered = true;
			} else {
				isoffered = false;
			}
			result.close();
			sql.close();
			manager.release("cubitdb", conn);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isoffered;
	}

	public static boolean saveRegionfile(Player player, String regionid) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			String uuid = player.getUniqueId().toString();
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sel = conn.prepareStatement(
					"SELECT region_id FROM cubitFiles WHERE UUID = '" + uuid + "' AND region_id = '" + regionid + "';");
			ResultSet result = sel.executeQuery();
			if (!result.next()) {
				PreparedStatement sql = conn.prepareStatement(
						"INSERT INTO cubitFiles (UUID, region_id) VALUES ('" + uuid + "', '" + regionid + "');");
				sql.executeUpdate();
				sql.close();
				result.close();
				sel.close();
				return true;
			} else {
				result.close();
				sel.close();
				manager.release("cubitdb", conn);
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean loadRegionfile(Player player, String regionid) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			String uuid = player.getUniqueId().toString();
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sel = conn.prepareStatement(
					"SELECT region_id FROM cubitFiles WHERE UUID = '" + uuid + "' AND region_id = '" + regionid + "';");
			ResultSet result = sel.executeQuery();
			if (result.next()) {
				PreparedStatement sql = conn.prepareStatement(
						"DELETE FROM cubitFiles WHERE UUID = '" + uuid + "' AND region_id = '" + regionid + "';");
				sql.executeUpdate();
				sql.close();
				result.close();
				sel.close();
				manager.release("cubitdb", conn);
				return true;
			} else {
				result.close();
				sel.close();
				manager.release("cubitdb", conn);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<String> getSavelist(Player player) {
		SQLConnectionManager manager = SQLConnectionManager.DEFAULT;
		try {
			String uuid = player.getUniqueId().toString();
			Connection conn = manager.getConnection("cubitdb");
			PreparedStatement sel = conn
					.prepareStatement("SELECT * FROM cubitFiles WHERE UUID = '" + uuid + "' ORDER BY id DESC;");
			List<String> list = new ArrayList<String>();
			try {
				ResultSet result = sel.executeQuery();
				if (result != null) {
					@SuppressWarnings("unused")
					int i = 0;
					while (result.next()) {
						list.add(result.getString("region_id"));
						i++;
					}
				}
				result.close();
				sel.close();
				manager.release("cubitdb", conn);
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
