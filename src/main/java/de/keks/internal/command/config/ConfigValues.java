package de.keks.internal.command.config;

import java.util.List;

import org.bukkit.Material;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class ConfigValues {

	// Plugin.X
	public static String pluginLocale;
	public static List<String> disabledWorlds;
	public static double configVersion;

	// Land.X
	public static double firstRegionCosts;
	public static double taxIncreasePerRegion;
	public static double maxTaxAmount;
	public static double setBiome;;
	public static double landSave;
	public static double percentageAtRegionSell;
	public static double buyupMembers;
	public static double buyupNoMembers;
	public static double buyupInfo;
	public static Material landBuyChunkBorders;
	public static Material landSellChunkBorders;

	// MySQL
	public static boolean isSQL;
	public static String sqlHostname;
	public static int sqlPort;
	public static String sqlDatabase;
	public static String sqlUsername;
	public static String sqlPassword;
	public static boolean sqlDebugmode;;

	// RegionLimits
	// Module

	public static boolean limitEnabled;
	public static List<String> limitWorldList;
	// Properties
	public static boolean limitPropertiesCheckChunkLoad;
	public static boolean limitPropertiesCheckChunkUnload;
	public static boolean limitPropertiesWatchCreatureSpawn;
	public static boolean limitPropertiesActiveInspection;
	public static int limitPropertiesInspectionFrequency;
	// Spawnreasons
	public static boolean limitSpawnReasonNatural;
	public static boolean limitSpawnReasonJockey;
	public static boolean limitSpawnReasonChunkGen;
	public static boolean limitSpawnReasonSpawner;
	public static boolean limitSpawnReasonEgg;
	public static boolean limitSpawnReasonSpawnerEgg;
	public static boolean limitSpawnReasonLightning;
	public static boolean limitSpawnReasonBed;
	public static boolean limitSpawnReasonBuildSnowman;
	public static boolean limitSpawnReasonBuildIrongolem;
	public static boolean limitSpawnReasonBuildWither;
	public static boolean limitSpawnReasonVillageDefense;
	public static boolean limitSpawnReasonVillageInvasion;
	public static boolean limitSpawnReasonBreeding;
	public static boolean limitSpawnReasonSlimeSplit;
	public static boolean limitSpawnReasonReinforcements;
	public static boolean limitSpawnReasonCustom;
	public static boolean limitSpawnReasonDefault;

	// Entities
	public static int limitEntitiesAnimal;
	public static int limitEntitiesMonster;
	public static int limitEntitiesNpc;
	public static int limitEntitiesOther;

}
