package de.keks.internal.core.database.yaml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import de.keks.internal.core.database.DatabaseManager;

public class YAMLInject {

	private static String offerPath = "offer";
	private static String cubliPath = "data";

	public static void savePlayer(Player player) {
		// No needed
	}

	public static long getLastlogin(Player p) {
		return p.getLastPlayed();

	}

	public static long getLastloginfromUUID(UUID uuid) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
		return p.getLastPlayed();

	}

	public static long getLastloginfromString(String p) {
		long lastloginString = 0;

		@SuppressWarnings("deprecation")
		OfflinePlayer player = Bukkit.getOfflinePlayer(p);
		if (player != null) {
			lastloginString = player.getLastPlayed();
		} else {
			Date now = new Date();
			lastloginString = now.getTime();
		}

		return lastloginString;

	}

	public static void setupPlayer(Player player) {
		// No needed
	}

	public static void updatePlayer(Player player) {
		// No needed
	}

	public static void setOffer(String regionid, double data) {
		SetupYAML provider = DatabaseManager.yamlProvider;
		FileConfiguration config = provider.getOfferConfig();
		config.set(offerPath + "." + regionid, data);
		provider.saveOfferData();
	}

	public static void removeOffer(String regionid) {
		SetupYAML provider = DatabaseManager.yamlProvider;
		FileConfiguration config = provider.getOfferConfig();
		config.set(offerPath + "." + regionid, null);
		provider.saveOfferData();

	}

	public static double getOffer(String regionid) {
		double offerdata = 0;
		SetupYAML provider = DatabaseManager.yamlProvider;
		FileConfiguration config = provider.getOfferConfig();
		offerdata = config.getDouble(offerPath + "." + regionid);
		return offerdata;
	}

	public static boolean isOffered(String regionid) {
		boolean isoffered = false;
		SetupYAML provider = DatabaseManager.yamlProvider;
		FileConfiguration config = provider.getOfferConfig();
		isoffered = config.contains(offerPath + "." + regionid);
		return isoffered;
	}

	public static boolean saveRegionfile(Player player, String regionid, Boolean remote) {
		SetupYAML provider = DatabaseManager.yamlProvider;
		FileConfiguration config = provider.getCubliConfig();

		config.set(cubliPath + "." + player.getUniqueId() + ".regionID", regionid);
		config.set(cubliPath + "." + player.getUniqueId() + ".remote", remote);
		provider.saveCubliData();
		return true;
	}

	public static boolean loadRegionfile(Player player, String regionid) {
		SetupYAML provider = DatabaseManager.yamlProvider;
		FileConfiguration config = provider.getCubliConfig();

		config.set(cubliPath + "." + player.getUniqueId() + ".regionID", null);
		config.set(cubliPath + "." + player.getUniqueId() + ".remote", null);
		config.set(cubliPath + "." + player.getUniqueId(), null);
		provider.saveCubliData();
		return true;
	}

	public static List<String> getSavelist(Player player) {
		List<String> list = new ArrayList<String>();
		SetupYAML provider = DatabaseManager.yamlProvider;
		FileConfiguration config = provider.getCubliConfig();

		for (String regionID : config.getStringList(cubliPath + "." + player.getUniqueId() + ".regionID")) {
			list.add(regionID);
		}
		return list;

	}

}
