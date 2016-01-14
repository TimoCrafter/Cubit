package de.keks.cubit;

import java.util.HashMap;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.keks.internal.ConfigValues;
import de.keks.internal.SkyConfig;
import de.keks.internal.core.database.DatabaseManager;
import de.keks.internal.core.entitylimit.CubitLimitModule;
import de.keks.internal.core.listeners.CubitListener;
import de.keks.internal.core.tasks.SetupAdminCommandsTask;
import de.keks.internal.core.tasks.SetupLandCommandsTask;
import de.keks.internal.core.tasks.SetupLanguageTask;
import de.keks.internal.core.tasks.SetupStoreCommandsTask;
import de.keks.internal.plugin.hooks.HookManager;
import de.keks.internal.register.CommandSetupAdmin;
import de.keks.internal.register.CommandSetupLand;
import de.keks.internal.register.CommandSetupStore;
import net.milkbowl.vault.economy.Economy;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CubitPlugin extends JavaPlugin {
    public HashMap<String, Long> cubitLandTask  = new HashMap<String, Long>();
    public HashMap<String, Long> cubitAdminTask = new HashMap<String, Long>();
    public HashMap<String, Long> cubitStoreTask = new HashMap<String, Long>();

    public int                   cubitTaskTime = 1;
    private static CubitPlugin   inst;
    public PluginDescriptionFile pdf;
    private HookManager          hookManager;
    private CommandSetupLand     landCommandHandler;
    private CommandSetupAdmin    adminCommandHandler;
    private CommandSetupStore    storeCommandHandler;
    private static SkyConfig     skyconfig;

    public static CubitPlugin inst() {
        return inst;
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(inst);
    }

    @Override
    public void onEnable() {
        pdf = this.getDescription();
        inst = this;
        this.saveDefaultConfig();
        skyconfig = new SkyConfig(this);
        new ConfigValues();
        setupCommands();
        registerListenerAndHandler();
        if (!checkAndSetupPlugins()) {
            this.setEnabled(false);
        }

    }

    private void setupCommands() {
        landCommandHandler = new CommandSetupLand(this);
        adminCommandHandler = new CommandSetupAdmin(this);
        storeCommandHandler = new CommandSetupStore(this);
        this.getServer().getScheduler().runTask(this, new SetupLandCommandsTask(this, landCommandHandler));
        this.getServer().getScheduler().runTask(this, new SetupAdminCommandsTask(this, adminCommandHandler));
        this.getServer().getScheduler().runTask(this, new SetupStoreCommandsTask(this, storeCommandHandler));
        getCommand("land").setExecutor(landCommandHandler);
        getCommand("ladmin").setExecutor(adminCommandHandler);
        getCommand("lstore").setExecutor(storeCommandHandler);

    }

    private void registerListenerAndHandler() {
        getServer().getPluginManager().registerEvents(new CubitListener(), this);
        getServer().getScheduler().runTask(this, new SetupLanguageTask(this));
        hookManager = new HookManager(this);
    }

    private boolean checkAndSetupPlugins() {
        if (!DatabaseManager.setupManager()) {
            return false;
        }

        if (this.getConfig().getBoolean("CubitLimit.enable")) {
            CubitLimitModule.start(this);
        }

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            this.getLogger().info("Vault found! Hooked");
            RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
            if (rsp == null) {
                this.getLogger().info("No vault supported economy plugin is loaded!");
                return false;
            }
            this.getLogger().info("Economy plugin found! Hooked");
        } else {
            this.getLogger().info("Vault NOT found!");
            return false;
        }

        if (getServer().getPluginManager().getPlugin("WorldGuard") != null) {
            this.getLogger().info("WorldGuard found! Hooked");
        } else {
            this.getLogger().info("WorldGuard NOT found!");
            return false;
        }

        if (getServer().getPluginManager().getPlugin("WorldEdit") != null) {
            this.getLogger().info("WorldEdit found! Hooked");
        } else {
            this.getLogger().info("WorldEdit NOT found!");
            return false;
        }

        if (!isSpigot()) {
            this.getLogger().info("Warning: You are using craftbukkit. Particle effects will not work!");
            this.getLogger().info("Warning: For enable particle effects, use spigot instead of craftbukkit!");
        }
        return true;

    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public int scheduleSyncRepeatingTask(Runnable run, long delay) {
        return scheduleSyncRepeatingTask(run, delay, delay);
    }

    public int scheduleSyncRepeatingTask(Runnable run, long start, long delay) {
        return inst.getServer().getScheduler().scheduleSyncRepeatingTask(inst, run, start, delay);
    }

    public void cancelTask(int taskID) {
        inst.getServer().getScheduler().cancelTask(taskID);
    }

    public static boolean isSpigot() {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }

    public static SkyConfig getSkyConfig() {
        return CubitPlugin.skyconfig;
    }

}
