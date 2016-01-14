package de.keks.internal.plugin.hooks.classes;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.keks.internal.plugin.hooks.HookCheck;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class WorldGuardHook implements HookCheck {

	private Plugin plugin;

	private WorldGuardPlugin worldGuard;

	private boolean loaded = false;

	public WorldGuardHook(Plugin plugin) {
		this.plugin = plugin;
	}

	public WorldGuardPlugin getWorldGuardPlugin() {
		return worldGuard;
	}

	@Override
	public void load() {
		Plugin worldGuardPlugin = plugin.getServer().getPluginManager().getPlugin("WorldGuard");

		if (worldGuardPlugin == null || !(worldGuardPlugin instanceof WorldGuardPlugin)) {
			if (WorldGuardPlugin.inst() != null) {
				worldGuard = WorldGuardPlugin.inst();
				loaded = true;
				return;
			} else {
				plugin.getLogger().severe("Plugin not found: WorldGuard");
			}
		} else {
			worldGuard = (WorldGuardPlugin) worldGuardPlugin;
		}
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}
}
