package de.keks.internal.core.database.yaml;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class YAMLInject {

	private static String offerPath = "offer";
	private static String cubliPath = "data";
	private static String cubliIDPath = "regionList";

	public static void savePlayer(Player player) {
		// No needed
	}

	public static long getLastlogin(Player p) {
		long lastloginString = 0;
		if (p.isOnline()) {
			Date now = new Date();
			lastloginString = now.getTime();
		} else {
			lastloginString = p.getLastPlayed();
		}
		return lastloginString;

	}

	public static long getLastloginfromUUID(UUID uuid) {
		long lastloginString = 0;
		OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
		if (p.isOnline()) {
			Date now = new Date();
			lastloginString = now.getTime();
		} else {
			lastloginString = p.getLastPlayed();
		}
		return lastloginString;

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

	public static void setOffer(String regionid, double data, UUID uuid) {
		YAMLConnectionHandler provider = YAMLSetup.getYAMLConnection();
		FileConfiguration config = provider.getOfferConfig();
		config.set(offerPath + "." + regionid, data);
		config.set(offerPath + "." + regionid + ".price", data);
		config.set(offerPath + "." + regionid + ".owner", uuid.toString());
		provider.saveOfferData();
	}

	public static void removeOffer(String regionid) {
		YAMLConnectionHandler provider = YAMLSetup.getYAMLConnection();
		FileConfiguration config = provider.getOfferConfig();
		config.set(offerPath + "." + regionid, null);
		provider.saveOfferData();

	}

	public static double getOffer(String regionid) {
		double offerdata = 0;
		YAMLConnectionHandler provider = YAMLSetup.getYAMLConnection();
		FileConfiguration config = provider.getOfferConfig();
		offerdata = config.getDouble(offerPath + "." + regionid + ".price");
		return offerdata;
	}

	public static boolean isOffered(String regionid) {
		boolean isoffered = false;
		YAMLConnectionHandler provider = YAMLSetup.getYAMLConnection();
		FileConfiguration config = provider.getOfferConfig();
		isoffered = config.contains(offerPath + "." + regionid);
		return isoffered;
	}

	public static boolean saveRegionfile(Player player, String regionid) {
		YAMLConnectionHandler provider = YAMLSetup.getYAMLConnection();
		FileConfiguration config = provider.getCubliConfig();

		config.set(cubliPath + "." + player.getUniqueId() + ".regionID", regionid);

		config.set(cubliIDPath + "." + player.getUniqueId(), regionid);
		provider.saveCubliData();
		return true;
	}

	public static boolean loadRegionfile(Player player, String regionid) {
		YAMLConnectionHandler provider = YAMLSetup.getYAMLConnection();
		FileConfiguration config = provider.getCubliConfig();

		config.set(cubliPath + "." + player.getUniqueId() + ".regionID", null);
		config.set(cubliPath + "." + player.getUniqueId() + ".remote", null);
		config.set(cubliPath + "." + player.getUniqueId(), null);
		config.set(cubliIDPath + "." + player.getUniqueId(), null);
		provider.saveCubliData();
		return true;
	}

	public static List<String> getSavelist(Player player) {
		YAMLConnectionHandler provider = YAMLSetup.getYAMLConnection();
		FileConfiguration config = provider.getCubliConfig();
		List<String> list = config.getStringList(cubliIDPath + "." + player.getUniqueId());

		return list;

	}

}
