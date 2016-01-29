package de.keks.internal.plugin.hooks.classes;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

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

public class WorldEditHook implements HookCheck {

	private Plugin plugin;

	private WorldEditPlugin worldEdit;

	private boolean loaded = false;

	public WorldEditHook(Plugin plugin) {
		this.plugin = plugin;
	}

	public WorldEditPlugin getWorldEditPlugin() {
		return worldEdit;
	}

	public void load() {
		Plugin worldEditPlugin = plugin.getServer().getPluginManager().getPlugin("WorldEdit");

		if (worldEditPlugin == null || !(worldEditPlugin instanceof WorldEditPlugin)) {
			plugin.getLogger().severe("Plugin not found: WorldEdit");
		} else {
			worldEdit = (WorldEditPlugin) worldEditPlugin;
			loaded = true;
		}
	}

	public boolean isLoaded() {
		return loaded;
	}

}