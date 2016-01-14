package de.keks.internal.core.database;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.keks.internal.ConfigValues;
import de.keks.internal.SkyConfig;
import de.keks.internal.core.database.mysql.SQLInject;
import de.keks.internal.core.database.yaml.YAMLInject;

public class DataController {

    public static long getLastLogin(Player player) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.getLastlogin(player);
        }
        return YAMLInject.getLastlogin(player);

    }

    public static long getLastLogin(String player) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.getLastloginfromString(player);
        }
        return YAMLInject.getLastloginfromString(player);

    }

    public static long getLastLogin(UUID uuid) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.getLastloginfromUUID(uuid);
        }
        return YAMLInject.getLastloginfromUUID(uuid);

    }

    public static double getOfferdata(String regionid) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.getOffer(regionid);
        }
        return YAMLInject.getOffer(regionid);

    }

    public static boolean isOffered(String regionid) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.isOffered(regionid);
        }
        return YAMLInject.isOffered(regionid);

    }

    public static void addOfferdata(String regionid, double data) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            SQLInject.setOffer(regionid, data);
            return;
        }
        YAMLInject.setOffer(regionid, data);

    }

    public static void removeOfferdata(String regionid) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            SQLInject.removeOffer(regionid);
            return;
        }
        YAMLInject.removeOffer(regionid);

    }

    public static boolean saveRegionSQL(Player player, String regionid, Boolean remote) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.saveRegionfile(player, regionid, remote);
        }
        return YAMLInject.saveRegionfile(player, regionid, remote);

    }

    public static boolean pasteRegionSQL(Player player, String regionid) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.loadRegionfile(player, regionid);
        }
        return YAMLInject.loadRegionfile(player, regionid);

    }

    public static List<String> getSavelist(Player player) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            return SQLInject.getSavelist(player);
        }
        return YAMLInject.getSavelist(player);

    }

    public static void savePlayer(Player player) {
        if (SkyConfig.getBoolean(ConfigValues.isSQL, ConfigValues.getDefaultValue(ConfigValues.isSQL))) {
            SQLInject.savePlayer(player);
            return;
        }
        YAMLInject.savePlayer(player);
    }
}
