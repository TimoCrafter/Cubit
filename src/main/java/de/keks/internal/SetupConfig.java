package de.keks.internal;

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

    //Plugin.X
    public static String pluginLocale   = "Plugin.locale";
    public static String disabledWorlds = "Plugin.excluded-worlds";
    public static String configVersion  = "Plugin.version";

    //Land.X
    public static String firstRegionCosts       = "Region.price-first-region";
    public static String taxIncreasePerRegion   = "Region.tax-increase-per-region";
    public static String maxTaxAmount           = "Region.max-price-per-region";
    public static String setBiome               = "Region.price-change-biome";
    public static String landSave               = "Region.price-store-region-db";
    public static String percentageAtRegionSell = "Region.percent-payback-region-sell";
    public static String buyupMembers           = "Region.buyup-members";
    public static String buyupNoMembers         = "Region.buyup-nomembers";
    public static String buyupInfo              = "Region.buyup-info";
    public static String landBuyChunkBorders    = "Region.buy-borders";
    public static String landSellChunkBorders   = "Region.sell-borders";

    //MySQL
    public static String isSQL        = "MySQL.use";
    public static String sqlHostname  = "MySQL.hostname";
    public static String sqlPort      = "MySQL.port";
    public static String sqlDatabase  = "MySQL.database";
    public static String sqlUsername  = "MySQL.username";
    public static String sqlPassword  = "MySQL.password";
    public static String sqlDebugmode = "MySQL.debugmode";

    //Ftp
    public static String ftpEnabled  = "Ftp.use";
    public static String ftpHostname = "Ftp.hostname";
    public static String ftpPort     = "Ftp.port";
    public static String ftpUsername = "Ftp.username";
    public static String ftpPassword = "Ftp.password";

    //RegionLimits
    //Module

    public static String limitEnabled                       = "SpawnLimit.use";
    public static String limitWorldList                     = "SpawnLimit.disabledIn";
    //Properties
    public static String limitPropertiesCheckChunkLoad      = "SpawnLimit.watch.check-chunk-load";
    public static String limitPropertiesCheckChunkUnload    = "SpawnLimit.watch.check-chunk-unload";
    public static String limitPropertiesWatchCreatureSpawn  = "SpawnLimit.watch.default-creature-spawn";
    public static String limitPropertiesActiveInspection    = "SpawnLimit.watch.active-inspection";
    public static String limitPropertiesInspectionFrequency = "SpawnLimit.watch.inspection-frequency";
    //Spawnreasons
    public static String limitSpawnReasonNatural            = "SpawnLimit.spawnreasons.Natural";
    public static String limitSpawnReasonJockey             = "SpawnLimit.spawnreasons.Jockey";
    public static String limitSpawnReasonChunkGen           = "SpawnLimit.spawnreasons.ChunkGenerate";
    public static String limitSpawnReasonSpawner            = "SpawnLimit.spawnreasons.Spawner";
    public static String limitSpawnReasonEgg                = "SpawnLimit.spawnreasons.Egg";
    public static String limitSpawnReasonSpawnerEgg         = "SpawnLimit.spawnreasons.SpawnerEgg";
    public static String limitSpawnReasonLightning          = "SpawnLimit.spawnreasons.Lightning";
    public static String limitSpawnReasonBed                = "SpawnLimit.spawnreasons.Bed";
    public static String limitSpawnReasonBuildSnowman       = "SpawnLimit.spawnreasons.Snowman";
    public static String limitSpawnReasonBuildIrongolem     = "SpawnLimit.spawnreasons.Irongolem";
    public static String limitSpawnReasonBuildWither        = "SpawnLimit.spawnreasons.Wither";
    public static String limitSpawnReasonVillageDefense     = "SpawnLimit.spawnreasons.VillageDefense";
    public static String limitSpawnReasonVillageInvasion    = "SpawnLimit.spawnreasons.VillageInvasion";
    public static String limitSpawnReasonBreeding           = "SpawnLimit.spawnreasons.Breeding";
    public static String limitSpawnReasonSlimeSplit         = "SpawnLimit.spawnreasons.SlimeSplit";
    public static String limitSpawnReasonReinforcements     = "SpawnLimit.spawnreasons.Reinforcements";
    public static String limitSpawnReasonCustom             = "SpawnLimit.spawnreasons.Costum";
    public static String limitSpawnReasonDefault            = "SpawnLimit.spawnreasons.Default";

    //Entities
    public static String limitEntitiesAnimal  = "SpawnLimit.entities-per-region.Animal";
    public static String limitEntitiesMonster = "SpawnLimit.entities-per-region.Monster";
    public static String limitEntitiesNpc     = "SpawnLimit.entities-per-region.Npc";
    public static String limitEntitiesOther   = "SpawnLimit.entities-per-region.Other";

    public SetupConfig() {
        ConfigValues.pluginLocale = (String)setupPath(pluginLocale, "enEN");
        List<String> pluginDisabledIn = new ArrayList<String>();
        pluginDisabledIn.add("Plugin.disabledIn.worldx");
        pluginDisabledIn.add("Plugin.disabledIn.worldy");
        pluginDisabledIn.add("Plugin.disabledIn.worldz");
        ConfigValues.disabledWorlds = (String[])SetupConfig.setupPath(disabledWorlds, pluginDisabledIn);
        ConfigValues.configVersion = (double)setupPath(configVersion, 1.0D);
        ConfigValues.maxTaxAmount = (double)setupPath(maxTaxAmount, 400.00D);

        ConfigValues.taxIncreasePerRegion = (double)setupPath(taxIncreasePerRegion, 10.0D);
        ConfigValues.firstRegionCosts = (double)setupPath(firstRegionCosts, 256.0D);
        ConfigValues.percentageAtRegionSell = (double)setupPath(percentageAtRegionSell, 0.5D);
        ConfigValues.buyupMembers = (double)setupPath(buyupMembers, 20 * 24 * 60 * 60 * 1000);
        ConfigValues.buyupNoMembers = (double)setupPath(buyupNoMembers, 35 * 24 * 60 * 60 * 1000);
        ConfigValues.buyupInfo = (double)setupPath(buyupInfo, 10);
        ConfigValues.landBuyChunkBorders = (Material)setupPath(landBuyChunkBorders, Material.TORCH);
        ConfigValues.landSellChunkBorders = (Material)setupPath(landSellChunkBorders, Material.REDSTONE_TORCH_ON);
        ConfigValues.setBiome = (double)setupPath(setBiome, 300D);
        ConfigValues.landSave = (double)setupPath(landSave, 300D);

        //MySQL
        setupPath(isSQL, false);
        setupPath(sqlHostname, "localhost");
        setupPath(sqlPort, 3306);
        setupPath(sqlDatabase, "MyDatabaseName");
        setupPath(sqlUsername, "MySqlUsername");
        setupPath(sqlPassword, "MySqlPassword");
        setupPath(sqlDebugmode, false);

        //Ftp
        setupPath(ftpEnabled, false);
        setupPath(ftpHostname, "locahost");
        setupPath(ftpPort, 21);
        setupPath(ftpUsername, "MyFtpUsername");
        setupPath(ftpPassword, "MyFtpPassword");

        //RegionLimits
        //Module

        setupPath(limitEnabled, false);
        List<String> limitDisabledIn = new ArrayList<String>();
        limitDisabledIn.add("CubitLimit.disabledIn.worldx");
        limitDisabledIn.add("CubitLimit.disabledIn.worldy");
        limitDisabledIn.add("CubitLimit.disabledIn.worldz");
        setupPath(limitWorldList, limitDisabledIn);
        //Properties
        setupPath(limitPropertiesCheckChunkLoad, false);
        setupPath(limitPropertiesCheckChunkUnload, false);
        setupPath(limitPropertiesWatchCreatureSpawn, true);
        setupPath(limitPropertiesActiveInspection, true);
        setupPath(limitPropertiesInspectionFrequency, 300);
        //Spawnreasons
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

        //Entities
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
