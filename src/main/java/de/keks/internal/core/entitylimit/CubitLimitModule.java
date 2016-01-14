package de.keks.internal.core.entitylimit;

import org.bukkit.plugin.Plugin;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.core.listeners.EntityListener;
import de.keks.internal.core.listeners.WorldListener;
import de.keks.internal.core.tasks.EntityGarbageCollectionTask;

public class CubitLimitModule {
	public static void start(Plugin pl) {
		// Register our event listener.
		pl.getServer().getPluginManager().registerEvents(new EntityListener(), pl);
		pl.getServer().getPluginManager().registerEvents(new WorldListener(), pl);
		// Start Mob GC Task
		pl.getServer().getScheduler().runTaskTimer(pl, new EntityGarbageCollectionTask(CubitPlugin.inst()), 0L,
				120 * 20L);
		CubitPlugin.inst().getLogger().info("[Module] CubitLimit loaded!");
	}

}
