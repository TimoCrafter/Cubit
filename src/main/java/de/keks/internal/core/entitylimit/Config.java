package de.keks.internal.core.entitylimit;

import java.util.List;

import de.keks.cubit.CubitPlugin;

public class Config {
	public static boolean getBoolean(String property) {
		return CubitPlugin.inst().getConfig().getBoolean(property);
	}

	public static int getInt(String property) {
		return CubitPlugin.inst().getConfig().getInt(property);
	}

	public static String getString(String property) {
		return CubitPlugin.inst().getConfig().getString(property);
	}

	public static String getString(String property, Object... args) {
		return String.format(CubitPlugin.inst().getConfig().getString(property), args);
	}

	public static boolean contains(String property) {
		return CubitPlugin.inst().getConfig().contains(property);
	}

	public static List<String> getStringList(String property) {
		return CubitPlugin.inst().getConfig().getStringList(property);
	}

}
