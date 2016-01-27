package de.keks.internal.core.cApi.KChunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
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

public class InternalHighlight {

	public static Map<UUID, Integer> taskID = new HashMap<UUID, Integer>();
	public static Map<UUID, Integer> taskvalue = new HashMap<UUID, Integer>();

	public static void chunkBorder(Player p, Location l, Chunk c, Effect e, int amt) {
		ArrayList<Location> edgeBlocks = new ArrayList<Location>();
		for (int i = 0; i < 16; i++) {
			for (int ii = -1; ii <= 10; ii++) {
				edgeBlocks.add(c.getBlock(i, (int) (l.getY()) + ii, 15).getLocation());
				edgeBlocks.add(c.getBlock(i, (int) (l.getY()) + ii, 0).getLocation());
				edgeBlocks.add(c.getBlock(0, (int) (l.getY()) + ii, i).getLocation());
				edgeBlocks.add(c.getBlock(15, (int) (l.getY()) + ii, i).getLocation());
			}
		}

		for (Location edgeBlock : edgeBlocks) {
			edgeBlock.setZ(edgeBlock.getBlockZ() + .5);
			edgeBlock.setX(edgeBlock.getBlockX() + .5);
			p.spigot().playEffect(edgeBlock, e, 0, 0, 0f, 0f, 0f, 0.001f, amt, 32);
		}

	}

	public static void startChunkEffect(final Player p, final Location l, final Chunk c, final Effect effect) {
		stopChunkEffect(p);
		final int tid = CubitPlugin.inst().getServer().getScheduler().scheduleSyncRepeatingTask(CubitPlugin.inst(),
				new Runnable() {
					public void run() {
						int value = taskvalue.get(p.getUniqueId());
						taskvalue.remove(p.getUniqueId());
						taskvalue.put(p.getUniqueId(), value + 1);
						chunkBorder(p, l, c, Effect.FIREWORKS_SPARK, 1);
						if (effect != null) {
							chunkBorder(p, l, c, effect, 1);
						}
						if (value >= 5) {
							stopChunkEffect(p);
							taskvalue.remove(p.getUniqueId());
						}
					}
				}, 0L, 30L);
		taskvalue.put(p.getUniqueId(), 1);
		taskID.put(p.getUniqueId(), tid);
	}

	public static void stopChunkEffect(Player p) {
		if (taskID.containsKey(p.getUniqueId())) {
			int tid = taskID.get(p.getUniqueId());
			CubitPlugin.inst().getServer().getScheduler().cancelTask(tid);
			taskID.remove(p.getUniqueId());
		}

	}

}
