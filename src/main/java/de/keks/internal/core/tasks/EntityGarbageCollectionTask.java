package de.keks.internal.core.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.command.config.ConfigFile;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.command.config.SetupConfig;
import de.keks.internal.core.entitylimit.MobGroupCompare;

public class EntityGarbageCollectionTask implements Runnable {

	private CubitPlugin plugin;

	public EntityGarbageCollectionTask(CubitPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		for (World world : Bukkit.getWorlds()) {
			for (final Chunk c : world.getLoadedChunks()) {
				new BukkitRunnable() {
					@Override
					public void run() {
						if (ConfigValues.limitWorldList.contains(c.getWorld().getName())) {
							return;
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
						for (final Map.Entry<String, ArrayList<Entity>> entry : types.entrySet()) {
							String eType = entry.getKey();
							int limit = ConfigFile.getInteger(SetupConfig.limitEntitiesDefault + "." + eType);
							if (entry.getValue().size() > limit) {
								for (int i = entry.getValue().size() - 1; i >= limit; i--) {
									final int ii = i;
									new BukkitRunnable() {
										@Override
										public void run() {
											entry.getValue().get(ii).remove();
										}
									}.runTask(plugin);
								}
							}
						}
					}
				}.runTaskAsynchronously(plugin);
			}
		}
	}
}
