package de.keks.internal.core.cApi.KChunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import de.keks.cubit.CubitPlugin;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class InternalRegenerateCO {
	public static boolean regenerate(final Player player) {

		try {
			Bukkit.getScheduler().scheduleSyncDelayedTask(CubitPlugin.inst(), new Runnable() {
				public void run() {
					Chunk c = player.getLocation().getChunk();

					if (player.getWorld().regenerateChunk(c.getX(), c.getZ())) {
						CChunk.refreshChunk(c);
						CChunk.teleportSave(c);
					}
				}
			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
