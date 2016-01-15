/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 **/
package de.keks.internal.command.config;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Initialize SkyConfig.class instance!<br>
 * Universal class for each other spigot/bukkit plugins.<br>
 * Based on plugin import and forwart.<br>
 * Write in onEnable() method.<br>
 * Example: <b>new (SkyConfig(this))</b>
 **/

public class ConfigFile {

	private static FileConfiguration configfile;
	private static Plugin pl;

	public ConfigFile(Plugin pl) {
		pl.saveDefaultConfig();
		ConfigFile.pl = pl;
		configfile = pl.getConfig();
		new SetupConfig();
	}

	public static boolean existPath(String path) {
		if (configfile.contains(path)) {
			return true;
		}
		return false;

	}

	public static boolean addToPath(String path, Object value) {
		try {
			configfile.set(path, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void saveAndReload() {
		pl.saveConfig();
		pl.reloadConfig();
		return;
	}

	/* getString(path, defaultvalue); String method for configfile */
	public static String getString(String path, Object defaultvalue) {
		String stringvalue;
		if (!existPath(path)) {
			if (addToPath(path, defaultvalue)) {
				saveAndReload();
			}
		}
		stringvalue = configfile.getString(path);
		return stringvalue;

	}

	/* getStringList(path, defaultvalue); StringList method for configfile */
	public static List<String> getStringList(String path, Object defaultvalue) {
		List<String> stringlistvalue;
		if (!existPath(path)) {
			if (addToPath(path, defaultvalue)) {
				saveAndReload();
			}
		}
		stringlistvalue = configfile.getStringList(path);
		return stringlistvalue;

	}

	/* getInteger(path, defaultvalue); Integer method for configfile */
	public static int getInteger(String path, Object defaultvalue) {
		int intvalue;
		if (!existPath(path)) {
			if (addToPath(path, defaultvalue)) {
				saveAndReload();
			}
		}
		intvalue = configfile.getInt(path);
		return intvalue;

	}

	/* getInteger(path, defaultvalue); Integer method for configfile */
	public static Object getObject(String path) {
		return configfile.get(path);

	}

	/* getDouble(path, defaultvalue); Double method for configfile */
	public static double getDouble(String path, Object defaultvalue) {
		double doublevalue;
		if (!existPath(path)) {
			if (addToPath(path, defaultvalue)) {
				saveAndReload();
			}
		}
		doublevalue = configfile.getDouble(path);
		return doublevalue;

	}

	/* getBoolan (path, defaultvalue); Boolean method for configfile */
	public static boolean getBoolean(String path, Object defaultvalue) {
		boolean booleanvalue;
		if (!existPath(path)) {
			if (addToPath(path, defaultvalue)) {
				saveAndReload();
			}
		}
		booleanvalue = configfile.getBoolean(path);
		return booleanvalue;
	}

	/* getLong(path, defaultvalue); Long method for configfile */
	public static long getLong(String path, Object defaultvalue) {
		long longvalue;
		if (!existPath(path)) {
			if (addToPath(path, defaultvalue)) {
				saveAndReload();
			}
		}
		longvalue = configfile.getLong(path);
		return longvalue;
	}

	/* getString(path, defaultvalue); String method for configfile */
	public static String getString(String path) {
		return configfile.getString(path);

	}

	/* getStringList(path, defaultvalue); StringList method for configfile */
	public static List<String> getStringList(String path) {

		return configfile.getStringList(path);

	}

	/* getInteger(path, defaultvalue); Integer method for configfile */
	public static int getInteger(String path) {
		return configfile.getInt(path);

	}

	/* getDouble(path, defaultvalue); Double method for configfile */
	public static double getDouble(String path) {
		return configfile.getDouble(path);

	}

	/* getBoolan (path, defaultvalue); Boolean method for configfile */
	public static boolean getBoolean(String path) {

		return configfile.getBoolean(path);
	}

	/* getLong(path, defaultvalue); Long method for configfile */
	public static long getLong(String path) {
		return configfile.getLong(path);
	}

}
