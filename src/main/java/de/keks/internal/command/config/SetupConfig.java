package de.keks.internal.command.config;

import java.util.ArrayList;
import java.util.HashMap;
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

public class SetupConfig {
	private static HashMap<String, Object> defaultvalue;

	// Plugin.X
	private static String pluginLocale = "Plugin.locale";
	private static String disabledWorlds = "Plugin.excluded-worlds";
	private static String configVersion = "Plugin.version";

	// Land.X
	private static String firstRegionCosts = "Region.price-first-region";
	private static String taxIncreasePerRegion = "Region.tax-increase-per-region";
	private static String maxTaxAmount = "Region.max-price-per-region";
	private static String setBiome = "Region.price-change-biome";
	private static String landSave = "Region.price-store-region-db";
	private static String percentageAtRegionSell = "Region.percent-payback-region-sell";
	private static String buyupMembers = "Region.buyup-members";
	private static String buyupNoMembers = "Region.buyup-nomembers";
	private static String buyupInfo = "Region.buyup-info";
	private static String landBuyChunkBorders = "Region.buy-borders";
	private static String landSellChunkBorders = "Region.sell-borders";

	// MySQL
	private static String isSQL = "MySQL.use";
	private static String sqlHostname = "MySQL.hostname";
	private static String sqlPort = "MySQL.port";
	private static String sqlDatabase = "MySQL.database";
	private static String sqlUsername = "MySQL.username";
	private static String sqlPassword = "MySQL.password";
	private static String sqlDebugmode = "MySQL.debugmode";

	// Ftp
	private static String ftpEnabled = "Ftp.use";
	private static String ftpHostname = "Ftp.hostname";
	private static String ftpPort = "Ftp.port";
	private static String ftpUsername = "Ftp.username";
	private static String ftpPassword = "Ftp.password";

	// RegionLimits
	// Module

	private static String limitEnabled = "SpawnLimit.use";
	private static String limitWorldList = "SpawnLimit.disabledIn";
	// Properties
	private static String limitPropertiesCheckChunkLoad = "SpawnLimit.watch.check-chunk-load";
	private static String limitPropertiesCheckChunkUnload = "SpawnLimit.watch.check-chunk-unload";
	private static String limitPropertiesWatchCreatureSpawn = "SpawnLimit.watch.default-creature-spawn";
	private static String limitPropertiesActiveInspection = "SpawnLimit.watch.active-inspection";
	private static String limitPropertiesInspectionFrequency = "SpawnLimit.watch.inspection-frequency";
	// Spawnreasons
	private static String limitSpawnReasonNatural = "SpawnLimit.spawnreasons.Natural";
	private static String limitSpawnReasonJockey = "SpawnLimit.spawnreasons.Jockey";
	private static String limitSpawnReasonChunkGen = "SpawnLimit.spawnreasons.ChunkGenerate";
	private static String limitSpawnReasonSpawner = "SpawnLimit.spawnreasons.Spawner";
	private static String limitSpawnReasonEgg = "SpawnLimit.spawnreasons.Egg";
	private static String limitSpawnReasonSpawnerEgg = "SpawnLimit.spawnreasons.SpawnerEgg";
	private static String limitSpawnReasonLightning = "SpawnLimit.spawnreasons.Lightning";
	private static String limitSpawnReasonBed = "SpawnLimit.spawnreasons.Bed";
	private static String limitSpawnReasonBuildSnowman = "SpawnLimit.spawnreasons.Snowman";
	private static String limitSpawnReasonBuildIrongolem = "SpawnLimit.spawnreasons.Irongolem";
	private static String limitSpawnReasonBuildWither = "SpawnLimit.spawnreasons.Wither";
	private static String limitSpawnReasonVillageDefense = "SpawnLimit.spawnreasons.VillageDefense";
	private static String limitSpawnReasonVillageInvasion = "SpawnLimit.spawnreasons.VillageInvasion";
	private static String limitSpawnReasonBreeding = "SpawnLimit.spawnreasons.Breeding";
	private static String limitSpawnReasonSlimeSplit = "SpawnLimit.spawnreasons.SlimeSplit";
	private static String limitSpawnReasonReinforcements = "SpawnLimit.spawnreasons.Reinforcements";
	private static String limitSpawnReasonCustom = "SpawnLimit.spawnreasons.Costum";
	private static String limitSpawnReasonDefault = "SpawnLimit.spawnreasons.Default";

	// Entities
	private static String limitEntitiesAnimal = "SpawnLimit.entities-per-region.Animal";
	private static String limitEntitiesMonster = "SpawnLimit.entities-per-region.Monster";
	private static String limitEntitiesNpc = "SpawnLimit.entities-per-region.Npc";
	private static String limitEntitiesOther = "SpawnLimit.entities-per-region.Other";

	public SetupConfig() {
		ConfigValues.pluginLocale = (String) setupPath(pluginLocale, "enEN");
		List<String> pluginDisabledIn = new ArrayList<String>();
		pluginDisabledIn.add("Plugin.disabledIn.worldx");
		pluginDisabledIn.add("Plugin.disabledIn.worldy");
		pluginDisabledIn.add("Plugin.disabledIn.worldz");
		ConfigValues.disabledWorlds = (String[]) SetupConfig.setupPath(disabledWorlds, pluginDisabledIn);
		ConfigValues.configVersion = (double) setupPath(configVersion, 1.0D);
		ConfigValues.maxTaxAmount = (double) setupPath(maxTaxAmount, 400.00D);

		ConfigValues.taxIncreasePerRegion = (double) setupPath(taxIncreasePerRegion, 10.0D);
		ConfigValues.firstRegionCosts = (double) setupPath(firstRegionCosts, 256.0D);
		ConfigValues.percentageAtRegionSell = (double) setupPath(percentageAtRegionSell, 0.5D);
		ConfigValues.buyupMembers = (double) setupPath(buyupMembers, 20 * 24 * 60 * 60 * 1000);
		ConfigValues.buyupNoMembers = (double) setupPath(buyupNoMembers, 35 * 24 * 60 * 60 * 1000);
		ConfigValues.buyupInfo = (double) setupPath(buyupInfo, 10);
		ConfigValues.landBuyChunkBorders = (Material) setupPath(landBuyChunkBorders, Material.TORCH);
		ConfigValues.landSellChunkBorders = (Material) setupPath(landSellChunkBorders, Material.REDSTONE_TORCH_ON);
		ConfigValues.setBiome = (double) setupPath(setBiome, 300D);
		ConfigValues.landSave = (double) setupPath(landSave, 300D);

		// MySQL
		setupPath(isSQL, false);
		setupPath(sqlHostname, "localhost");
		setupPath(sqlPort, 3306);
		setupPath(sqlDatabase, "MyDatabaseName");
		setupPath(sqlUsername, "MySqlUsername");
		setupPath(sqlPassword, "MySqlPassword");
		setupPath(sqlDebugmode, false);

		// Ftp
		setupPath(ftpEnabled, false);
		setupPath(ftpHostname, "locahost");
		setupPath(ftpPort, 21);
		setupPath(ftpUsername, "MyFtpUsername");
		setupPath(ftpPassword, "MyFtpPassword");

		// RegionLimits
		// Module

		setupPath(limitEnabled, false);
		List<String> limitDisabledIn = new ArrayList<String>();
		limitDisabledIn.add("CubitLimit.disabledIn.worldx");
		limitDisabledIn.add("CubitLimit.disabledIn.worldy");
		limitDisabledIn.add("CubitLimit.disabledIn.worldz");
		setupPath(limitWorldList, limitDisabledIn);
		// Properties
		setupPath(limitPropertiesCheckChunkLoad, false);
		setupPath(limitPropertiesCheckChunkUnload, false);
		setupPath(limitPropertiesWatchCreatureSpawn, true);
		setupPath(limitPropertiesActiveInspection, true);
		setupPath(limitPropertiesInspectionFrequency, 300);
		// Spawnreasons
		setupPath(limitSpawnReasonNatural, true);
		setupPath(limitSpawnReasonJockey, true);
		setupPath(limitSpawnReasonChunkGen, true);
		setupPath(limitSpawnReasonSpawner, true);
		setupPath(limitSpawnReasonEgg, true);
		setupPath(limitSpawnReasonSpawnerEgg, true);
		setupPath(limitSpawnReasonLightning, true);
		setupPath(limitSpawnReasonBed, true);
		setupPath(limitSpawnReasonBuildSnowman, true);
		setupPath(limitSpawnReasonBuildIrongolem, true);
		setupPath(limitSpawnReasonBuildWither, true);
		setupPath(limitSpawnReasonVillageDefense, true);
		setupPath(limitSpawnReasonVillageInvasion, true);
		setupPath(limitSpawnReasonBreeding, true);
		setupPath(limitSpawnReasonSlimeSplit, true);
		setupPath(limitSpawnReasonReinforcements, true);
		setupPath(limitSpawnReasonCustom, true);
		setupPath(limitSpawnReasonDefault, true);

		// Entities
		setupPath(limitEntitiesAnimal, 50);
		setupPath(limitEntitiesMonster, 50);
		setupPath(limitEntitiesNpc, 50);
		setupPath(limitEntitiesOther, 120);

		SkyConfig.saveAndReload();
	}

	private static Object setupPath(String path, Object value) {
		defaultvalue.put(path, value);
		if (!SkyConfig.existPath(path)) {
			SkyConfig.addToPath(path, value);
			return value;
		}
		return SkyConfig.getObject(path);
	}

	public static Object getDefaultValue(String path) {
		return defaultvalue.get(path);

	}
}
