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
import de.keks.internal.core.entitylimit.Config;
import de.keks.internal.core.entitylimit.MobGroupCompare;

public class WorldListener implements Listener {
    HashMap<Chunk, Integer> chunkTasks = new HashMap<Chunk, Integer>();

    class inspectTask extends BukkitRunnable {
        Chunk c;

        public inspectTask(Chunk c) {
            this.c = c;
        }

        @Override
        public void run() {
            if (!c.isLoaded()) {
                CubitPlugin.inst().cancelTask(taskID);
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
        if (Config.getBoolean("CubitLimit.properties.active-inspections")) {
            inspectTask task = new inspectTask(e.getChunk());
            int taskID = CubitPlugin.inst().scheduleSyncRepeatingTask(task, Config.getInt("CubitLimit.properties.inspection-frequency") * 20L);
            task.setId(taskID);

            chunkTasks.put(e.getChunk(), taskID);
        }

        if (Config.getBoolean("CubitLimit.properties.check-chunk-load"))
            CheckChunk(e.getChunk());
    }

    @EventHandler
    public void onChunkUnloadEvent(final ChunkUnloadEvent e) {

        if (chunkTasks.containsKey(e.getChunk())) {
            CubitPlugin.inst().getServer().getScheduler().cancelTask(chunkTasks.get(e.getChunk()));
            chunkTasks.remove(e.getChunk());
        }

        if (Config.getBoolean("CubitLimit.properties.check-chunk-unload"))
            CheckChunk(e.getChunk());
    }

    public static boolean CheckChunk(Chunk c) {
        if (Config.getStringList("CubitLimit.excluded-worlds").contains(c.getWorld().getName())) {
            return false;
        }

        Entity[] ents = c.getEntities();

        HashMap<String, ArrayList<Entity>> types = new HashMap<String, ArrayList<Entity>>();

        for (int i = ents.length - 1; i >= 0; i--) {
            EntityType t = ents[i].getType();

            String eType = t.toString();
            String eGroup = MobGroupCompare.getMobGroup(ents[i]);

            if (Config.contains("CubitLimit.entities." + eType)) {
                if (!types.containsKey(eType))
                    types.put(eType, new ArrayList<Entity>());
                types.get(eType).add(ents[i]);
            }

            if (Config.contains("CubitLimit.entities." + eGroup)) {
                if (!types.containsKey(eGroup))
                    types.put(eGroup, new ArrayList<Entity>());
                types.get(eGroup).add(ents[i]);
            }
        }

        for (Entry<String, ArrayList<Entity>> entry : types.entrySet()) {
            String eType = entry.getKey();
            int limit = Config.getInt("CubitLimit.entities." + eType);

            if (entry.getValue().size() >= limit) {
                return true;

            }
        }

        return false;

    }

}
