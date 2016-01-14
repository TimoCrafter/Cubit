package de.keks.internal.core.database.yaml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class YAMLInject {

    public static void savePlayer(Player player) {

    }

    public static long getLastlogin(Player p) {
        long lastlogin = 0;
        return lastlogin;

    }

    public static long getLastloginfromUUID(UUID uuid) {
        long lastlogin = 0;
        return lastlogin;

    }

    public static long getLastloginfromString(String p) {
        long lastloginString = 0;

        @SuppressWarnings("deprecation")
        OfflinePlayer player = Bukkit.getOfflinePlayer(p);
        if (player != null) {
            //Code
        } else {
            Date now = new Date();
            lastloginString = now.getTime();
        }

        return lastloginString;

    }

    public static void setupPlayer(Player player) {

    }

    public static void updatePlayer(Player player) {

    }

    public static void setOffer(String regionid, double data) {

    }

    public static void removeOffer(String regionid) {

    }

    public static double getOffer(String regionid) {
        double offerdata = 0;
        return offerdata;
    }

    public static boolean isOffered(String regionid) {
        boolean isoffered = false;
        return isoffered;
    }

    public static boolean saveRegionfile(Player player, String regionid, Boolean remote) {
        return true;
    }

    public static boolean loadRegionfile(Player player, String regionid) {
        return true;
    }

    public static List<String> getSavelist(Player player) {
        List<String> list = new ArrayList<String>();
        return list;

    }

}
