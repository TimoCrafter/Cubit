package de.keks.internal.core.listeners;

import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import de.keks.internal.command.config.ConfigFile;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.command.config.SetupConfig;

public class EntitySpawnEventListener implements Listener {

	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
		if (e.isCancelled())
			return;

		if (ConfigValues.limitPropertiesWatchCreatureSpawn == false)
			return;

		String reason = e.getSpawnReason().toString();

		if (ConfigFile.existPath(SetupConfig.limitSpawnReason + "." + reason) == false) {
			return;
		}

		Chunk c = e.getLocation().getChunk();

		if (ChunkEventListener.CheckChunk(c)) {
			e.setCancelled(true);
			return;
		}

	}
}
