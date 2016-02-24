package de.keks.internal.core.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.command.config.ConfigFile;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.command.config.SetupConfig;
import de.keks.internal.core.entitylimit.MobGroupCompare;
import de.keks.internal.register.MainCore;

public class ChunkEventListener implements Listener {
	HashMap<Chunk, Integer> chunkTasks = new HashMap<Chunk, Integer>();

	class inspectTask extends BukkitRunnable {
		Chunk c;

		public inspectTask(Chunk c) {
			this.c = c;
		}

		@Override
		public void run() {
			if (!c.isLoaded()) {
				MainCore.cancelTask(taskID);
				return;
			}

			CheckChunk(c);
		}

		int taskID;

		public void setId(int taskID) {
			this.taskID = taskID;

		}

	}

	@EventHandler
	public void onChunkLoadEvent(final ChunkLoadEvent e) {
		if (ConfigValues.limitPropertiesActiveInspection) {
			inspectTask task = new inspectTask(e.getChunk());
			int taskID = MainCore.runSyncRepeatingTask(task,
					ConfigValues.limitPropertiesInspectionFrequency * 20L);
			task.setId(taskID);

			chunkTasks.put(e.getChunk(), taskID);
		}

		if (ConfigValues.limitPropertiesCheckChunkLoad)
			CheckChunk(e.getChunk());
	}

	@EventHandler
	public void onChunkUnloadEvent(final ChunkUnloadEvent e) {

		if (chunkTasks.containsKey(e.getChunk())) {
			CubitPlugin.inst().getServer().getScheduler().cancelTask(chunkTasks.get(e.getChunk()));
			chunkTasks.remove(e.getChunk());
		}

		if (ConfigValues.limitPropertiesCheckChunkUnload)
			CheckChunk(e.getChunk());
	}

	public static boolean CheckChunk(Chunk c) {
		if (ConfigValues.limitWorldList.contains(c.getWorld().getName())) {
			return false;
		}

		Entity[] ents = c.getEntities();

		HashMap<String, ArrayList<Entity>> types = new HashMap<String, ArrayList<Entity>>();

		for (int i = ents.length - 1; i >= 0; i--) {
			EntityType t = ents[i].getType();

			String eType = t.toString();
			String eGroup = MobGroupCompare.getMobGroup(ents[i]);

			if (ConfigFile.existPath(SetupConfig.limitEntitiesDefault + "." + eType)) {
				if (!types.containsKey(eType))
					types.put(eType, new ArrayList<Entity>());
				types.get(eType).add(ents[i]);
			}

			if (ConfigFile.existPath(SetupConfig.limitEntitiesDefault + "." + eGroup)) {
				if (!types.containsKey(eGroup))
					types.put(eGroup, new ArrayList<Entity>());
				types.get(eGroup).add(ents[i]);
			}
		}

		for (Entry<String, ArrayList<Entity>> entry : types.entrySet()) {
			String eType = entry.getKey();
			int limit = ConfigFile.getInteger(SetupConfig.limitEntitiesDefault + "." + eType);

			if (entry.getValue().size() >= limit) {
				return true;

			}
		}

		return false;

	}

}
