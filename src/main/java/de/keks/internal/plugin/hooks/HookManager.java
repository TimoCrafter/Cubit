package de.keks.internal.plugin.hooks;

import de.keks.iLand.ILandPlugin;
import de.keks.internal.core.tasks.RegisterPluginHooksTask;
import de.keks.internal.plugin.hooks.classes.EconomyHook;
import de.keks.internal.plugin.hooks.classes.VaultHook;
import de.keks.internal.plugin.hooks.classes.WorldEditHook;
import de.keks.internal.plugin.hooks.classes.WorldGuardHook;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */
public class HookManager {

	private WorldGuardHook worldGuardManager;
	private WorldEditHook worldEditManager;
	private VaultHook vaultManager;

	public HookManager(final ILandPlugin iLand) {

		worldGuardManager = new WorldGuardHook(iLand);
		iLand.getServer().getScheduler().scheduleSyncDelayedTask(iLand, new RegisterPluginHooksTask(worldGuardManager));

		worldEditManager = new WorldEditHook(iLand);
		iLand.getServer().getScheduler().scheduleSyncDelayedTask(iLand, new RegisterPluginHooksTask(worldEditManager));

		vaultManager = new VaultHook(iLand);
		iLand.getServer().getScheduler().scheduleSyncDelayedTask(iLand, new RegisterPluginHooksTask(vaultManager));

	}

	public EconomyHook getEconomyManager() {
		return vaultManager;
	}

	public WorldGuardHook getWorldGuardManager() {
		return worldGuardManager;
	}

	public WorldEditHook getWorldEditManager() {
		return worldEditManager;
	}

}
