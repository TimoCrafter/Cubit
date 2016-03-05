package de.keks.cubit;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import de.keks.internal.command.config.ConfigFile;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.core.database.DatabaseManager;
import de.keks.internal.core.entitylimit.EntityLimitClass;
import de.keks.internal.core.listeners.PlayerLoginEventListener;
import de.keks.internal.core.tasks.SetupAdminCommandsTask;
import de.keks.internal.core.tasks.SetupLandCommandsTask;
import de.keks.internal.core.tasks.SetupLanguageTask;
import de.keks.internal.plugin.hooks.HookManager;
import de.keks.internal.register.CommandSetupAdmin;
import de.keks.internal.register.CommandSetupLand;
import de.keks.internal.register.MainCore;
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

	public int cubitTaskTime = 1;
	private static CubitPlugin inst;
	public PluginDescriptionFile pdf;
	private HookManager hookManager;
	private CommandSetupLand landCommandHandler;
	private CommandSetupAdmin adminCommandHandler;
	private static ConfigFile skyconfig;

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
		skyconfig = new ConfigFile(this);
		registerListenerAndHandler();
		if (!checkAndSetupPlugins()) {
			this.setEnabled(false);
		} else {
			setupCommands();
		}

	}

	private void setupCommands() {
		landCommandHandler = new CommandSetupLand(this);
		adminCommandHandler = new CommandSetupAdmin(this);
		this.getServer().getScheduler().runTask(this, new SetupLandCommandsTask(this, landCommandHandler));
		this.getServer().getScheduler().runTask(this, new SetupAdminCommandsTask(this, adminCommandHandler));
		getCommand("land").setExecutor(landCommandHandler);
		getCommand("ladmin").setExecutor(adminCommandHandler);

	}

	private void registerListenerAndHandler() {
		getServer().getPluginManager().registerEvents(new PlayerLoginEventListener(), this);
		getServer().getScheduler().runTask(this, new SetupLanguageTask(this));
		hookManager = new HookManager(this);
	}

	private boolean checkAndSetupPlugins() {
		if (!DatabaseManager.setupManager()) {
			return false;
		}

		if (ConfigValues.limitEnabled) {
			EntityLimitClass.register(this);
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

		if (!MainCore.isSpigot()) {
			this.getLogger().info("Warning: You are using craftbukkit. Particle effects will not work!");
			this.getLogger().info("Warning: For enable particle effects, use spigot instead of craftbukkit!");
		}
		return true;

	}

	public HookManager getHookManager() {
		return hookManager;
	}

	public static ConfigFile getSkyConfig() {
		return CubitPlugin.skyconfig;
	}

}
