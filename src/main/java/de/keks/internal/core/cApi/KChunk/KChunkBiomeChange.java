package de.keks.internal.core.cApi.KChunk;

import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.command.config.ConfigValues;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class KChunkBiomeChange {

	@SuppressWarnings("deprecation")
	public static boolean createCubitBiome(final Player player, final String regionID, final Biome biome) {
		try {
			Bukkit.getScheduler().scheduleAsyncDelayedTask(CubitPlugin.inst(), new Runnable() {
				public void run() {
					if (KChunkBiomeChangeCorner.setWGBiome(player, regionID, biome)) {
						KChunkFacade.refreshChunk(player.getLocation().getChunk());
						double costs = ConfigValues.setBiome;
						player.sendMessage(I18n.translate("messages.buyBiome", regionID, costs));
					}

				}
			});
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static void replaceBiomePoints(HashSet<int[]> points, World world, Biome biome, Player player) {

		int counter = 0;
		int jump = 0;
		int sweep = 1000;

		int maxCount = points.size();

		int jumpAt = 10;
		if (maxCount > 1000 * sweep)
			jumpAt = 1;
		else if (maxCount > 350 * sweep)
			jumpAt = 2;
		else if (maxCount > 100 * sweep)
			jumpAt = 5;

		HashSet<int[]> cache = new HashSet<int[]>();
		HashSet<String> loadedChunks = new HashSet<String>();

		for (int[] point : points) {
			KChunkBiomeChange.setBiomeAt(world, point[0], point[1], biome, cache, loadedChunks);
			if (((counter * 100 / maxCount) % jumpAt) == 0) {
				int percent = counter * 100 / maxCount;
				if (jump != percent) {
					if (Thread.interrupted()) {
						player.sendMessage("[Cubit] ... Cubit Task abgebrochen!");
						return;
					}
					jump = percent;
				}
			}
			counter++;

		}

		if (Thread.interrupted()) {
			return;
		}

		KChunkBiomeChange.flushBiomeSetHunks(world, biome, cache, loadedChunks);

	}

	static void setBiomeAt(World world, int x, int z, Biome biome, HashSet<int[]> blockHunk,
			HashSet<String> loadedChunks) {

		if (blockHunk.size() < 1000) {
			blockHunk.add(new int[] { x, z });
			return;
		}
		blockHunk.add(new int[] { x, z });

		KChunkBiomeChange.flushBiomeSetHunks(world, biome, blockHunk, loadedChunks);

	}

	static void flushBiomeSetHunks(World world, Biome biome, HashSet<int[]> blockHunk, HashSet<String> loadedChunks) {
		KChunkBiomeChange.setBiomeOnMainThread(world, blockHunk, biome, loadedChunks);
		blockHunk.clear();
	}

	static void setBiomeOnMainThread(final World world, final HashSet<int[]> points, final Biome biome,
			final HashSet<String> loadedChunks) {
		Future<String> returnFuture = Bukkit.getScheduler().callSyncMethod(CubitPlugin.inst(), new Callable<String>() {
			public String call() {

				for (int[] blockLoc : points) {
					world.setBiome(blockLoc[0], blockLoc[1], biome);
				}

				return null;
			}
		});

		while (!returnFuture.isDone()) {
		}
		;

	}

}
