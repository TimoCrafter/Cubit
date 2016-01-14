package de.keks.internal.core.listeners;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import de.keks.internal.core.entitylimit.Config;

public class EntityListener implements Listener {

	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
		if (e.isCancelled())
			return;

		if (Config.getBoolean("CubitLimit.properties.watch-creature-spawns") == false)
			return;

		String reason = e.getSpawnReason().toString();

		if (!Config.getBoolean("CubitLimit.spawn-reasons." + reason)
				|| Config.getBoolean("CubitLimit.spawn-reasons." + reason) == false) {
			return;
		}

		Chunk c = e.getLocation().getChunk();

		if (WorldListener.CheckChunk(c)) {
			e.setCancelled(true);
			return;
		}

	}
}
