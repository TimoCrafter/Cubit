package de.keks.internal.command.config;

import java.util.ArrayList;
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
	// private static HashMap<String, Object> defaultvalue;

	// Plugin.X
	private static String pluginLocale = "Plugin.locale";
	private static String disabledWorlds = "Plugin.disabledWorlds";
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
	public static String limitSpawnReason = "SpawnLimit.entities-per-region";

	// Entities
	private static String limitEntitiesAnimal = "SpawnLimit.entities-per-region.Animal";
	private static String limitEntitiesMonster = "SpawnLimit.entities-per-region.Monster";
	private static String limitEntitiesNpc = "SpawnLimit.entities-per-region.Npc";
	private static String limitEntitiesOther = "SpawnLimit.entities-per-region.Other";
	public static String limitEntitiesDefault = "SpawnLimit.entities-per-region";

	@SuppressWarnings("unchecked")
	public SetupConfig() {

		List<String> pluginDisabledIn = new ArrayList<String>();
		pluginDisabledIn.add("worldx");
		pluginDisabledIn.add("worldy");
		pluginDisabledIn.add("worldz");

		List<String> limitDisabledIn = new ArrayList<String>();
		limitDisabledIn.add("worldx");
		limitDisabledIn.add("worldy");
		limitDisabledIn.add("worldz");

		ConfigValues.pluginLocale = (String) setupPath(pluginLocale, "enEN");
		ConfigValues.disabledWorlds = (List<String>) setupPath(disabledWorlds, pluginDisabledIn);
		ConfigValues.configVersion = (double) setupPath(configVersion, 1.0D);

		ConfigValues.maxTaxAmount = (double) setupPath(maxTaxAmount, 400.00D);
		ConfigValues.taxIncreasePerRegion = (double) setupPath(taxIncreasePerRegion, 10.0D);
		ConfigValues.firstRegionCosts = (double) setupPath(firstRegionCosts, 256.0D);
		ConfigValues.percentageAtRegionSell = (double) setupPath(percentageAtRegionSell, 0.5D);
		ConfigValues.buyupMembers = (double) setupPath(buyupMembers, 20D) * 24 * 60 * 60 * 1000;
		ConfigValues.buyupNoMembers = (double) setupPath(buyupNoMembers, 35D) * 24 * 60 * 60 * 1000;
		ConfigValues.buyupInfo = (double) setupPath(buyupInfo, 10D);
		ConfigValues.landBuyChunkBorders = Material
				.getMaterial((String) setupPath(landBuyChunkBorders, Material.TORCH.name()));
		ConfigValues.landSellChunkBorders = Material
				.getMaterial((String) setupPath(landSellChunkBorders, Material.REDSTONE_TORCH_ON.name()));
		ConfigValues.setBiome = (double) setupPath(setBiome, 300D);
		ConfigValues.landSave = (double) setupPath(landSave, 300D);

		// MySQL
		ConfigValues.isSQL = (boolean) setupPath(isSQL, false);
		ConfigValues.sqlHostname = (String) setupPath(sqlHostname, "localhost");
		ConfigValues.sqlPort = (int) setupPath(sqlPort, 3306);
		ConfigValues.sqlDatabase = (String) setupPath(sqlDatabase, "MyDatabaseName");
		ConfigValues.sqlUsername = (String) setupPath(sqlUsername, "MySqlUsername");
		ConfigValues.sqlPassword = (String) setupPath(sqlPassword, "MySqlPassword");
		ConfigValues.sqlDebugmode = (boolean) setupPath(sqlDebugmode, false);

		// Ftp
		ConfigValues.ftpEnabled = (boolean) setupPath(ftpEnabled, false);
		ConfigValues.ftpHostname = (String) setupPath(ftpHostname, "locahost");
		ConfigValues.ftpPort = (int) setupPath(ftpPort, 21);
		ConfigValues.ftpUsername = (String) setupPath(ftpUsername, "MyFtpUsername");
		ConfigValues.ftpPassword = (String) setupPath(ftpPassword, "MyFtpPassword");

		// RegionLimits
		// Module

		ConfigValues.limitEnabled = (boolean) setupPath(limitEnabled, false);
		ConfigValues.limitWorldList = (List<String>) setupPath(limitWorldList, limitDisabledIn);
		// Properties
		ConfigValues.limitPropertiesCheckChunkLoad = (boolean) setupPath(limitPropertiesCheckChunkLoad, false);
		ConfigValues.limitPropertiesCheckChunkUnload = (boolean) setupPath(limitPropertiesCheckChunkUnload, false);
		ConfigValues.limitPropertiesWatchCreatureSpawn = (boolean) setupPath(limitPropertiesWatchCreatureSpawn, true);
		ConfigValues.limitPropertiesActiveInspection = (boolean) setupPath(limitPropertiesActiveInspection, true);
		ConfigValues.limitPropertiesInspectionFrequency = (int) setupPath(limitPropertiesInspectionFrequency, 300);
		// Spawnreasons
		ConfigValues.limitSpawnReasonNatural = (boolean) setupPath(limitSpawnReasonNatural, true);
		ConfigValues.limitSpawnReasonJockey = (boolean) setupPath(limitSpawnReasonJockey, true);
		ConfigValues.limitSpawnReasonChunkGen = (boolean) setupPath(limitSpawnReasonChunkGen, true);
		ConfigValues.limitSpawnReasonSpawner = (boolean) setupPath(limitSpawnReasonSpawner, true);
		ConfigValues.limitSpawnReasonEgg = (boolean) setupPath(limitSpawnReasonEgg, true);
		ConfigValues.limitSpawnReasonSpawnerEgg = (boolean) setupPath(limitSpawnReasonSpawnerEgg, true);
		ConfigValues.limitSpawnReasonLightning = (boolean) setupPath(limitSpawnReasonLightning, true);
		ConfigValues.limitSpawnReasonBed = (boolean) setupPath(limitSpawnReasonBed, true);
		ConfigValues.limitSpawnReasonBuildSnowman = (boolean) setupPath(limitSpawnReasonBuildSnowman, true);
		ConfigValues.limitSpawnReasonBuildIrongolem = (boolean) setupPath(limitSpawnReasonBuildIrongolem, true);
		ConfigValues.limitSpawnReasonBuildWither = (boolean) setupPath(limitSpawnReasonBuildWither, true);
		ConfigValues.limitSpawnReasonVillageDefense = (boolean) setupPath(limitSpawnReasonVillageDefense, true);
		ConfigValues.limitSpawnReasonVillageInvasion = (boolean) setupPath(limitSpawnReasonVillageInvasion, true);
		ConfigValues.limitSpawnReasonBreeding = (boolean) setupPath(limitSpawnReasonBreeding, true);
		ConfigValues.limitSpawnReasonSlimeSplit = (boolean) setupPath(limitSpawnReasonSlimeSplit, true);
		ConfigValues.limitSpawnReasonReinforcements = (boolean) setupPath(limitSpawnReasonReinforcements, true);
		ConfigValues.limitSpawnReasonCustom = (boolean) setupPath(limitSpawnReasonCustom, true);
		ConfigValues.limitSpawnReasonDefault = (boolean) setupPath(limitSpawnReasonDefault, true);

		// Entities
		ConfigValues.limitEntitiesAnimal = (int) setupPath(limitEntitiesAnimal, 50);
		ConfigValues.limitEntitiesMonster = (int) setupPath(limitEntitiesMonster, 50);
		ConfigValues.limitEntitiesNpc = (int) setupPath(limitEntitiesNpc, 50);
		ConfigValues.limitEntitiesOther = (int) setupPath(limitEntitiesOther, 120);

		ConfigFile.saveAndReload();
	}

	private static Object setupPath(String path, Object value) {
		// defaultvalue.put(path, value);
		if (!ConfigFile.existPath(path)) {
			ConfigFile.addToPath(path, value);
			return value;
		}
		return ConfigFile.getObject(path);
	}

	// public static Object getDefaultValue(String path) {
	// return defaultvalue.get(path);
	//
	// }
}
