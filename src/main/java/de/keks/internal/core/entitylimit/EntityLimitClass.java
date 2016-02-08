package de.keks.internal.core.entitylimit;

import org.bukkit.plugin.Plugin;

import de.keks.iLand.ILandPlugin;
import de.keks.internal.core.listeners.EntitySpawnEventListener;
import de.keks.internal.core.listeners.ChunkEventListener;
import de.keks.internal.core.tasks.EntityGarbageCollectionTask;

public class EntityLimitClass {
	public static void register(Plugin pl) {
		pl.getServer().getPluginManager().registerEvents(new EntitySpawnEventListener(), pl);
		pl.getServer().getPluginManager().registerEvents(new ChunkEventListener(), pl);
		pl.getServer().getScheduler().runTaskTimer(pl, new EntityGarbageCollectionTask(ILandPlugin.inst()), 0L,
				120 * 20L);
	}

}
