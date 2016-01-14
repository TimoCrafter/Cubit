package de.keks.internal.core.tasks;

import org.bukkit.World;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class RegionSaveTask implements Runnable {

	private World world;
	private ProtectedRegion region;
	private WorldGuardPlugin worldGuard;

	public RegionSaveTask(WorldGuardPlugin pl, ProtectedRegion region, World world) {
		this.region = region;
		this.world = world;
		this.worldGuard = pl;
	}

	public void run() {
		try {
			RegionManager manager = worldGuard.getRegionManager(world);
			if (region != null) {
				manager.addRegion(region);
			}
			manager.saveChanges();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
