package de.keks.internal.core.cApi.KChunk;

import java.util.ArrayList;

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

public class KChunkHighlight {

	public static void buildPaticleSpigot(Player p, Location l, Chunk c, Effect e, int amt) {
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

	public KChunkHighlight(final Player p, final Location l, final Chunk c, final Effect effect) {

		CubitPlugin.inst().getServer().getScheduler().runTaskAsynchronously(CubitPlugin.inst(), new Runnable() {
			@Override
			public void run() {
				sendPaticleSpigot(p, l, c, effect);
			}
		});

	}

	public void sendPaticleSpigot(final Player p, final Location l, final Chunk c, final Effect effect) {
		int loopValue = 0;
		while (loopValue <= 3) {
			buildPaticleSpigot(p, l, c, Effect.FIREWORKS_SPARK, 1);
			if (effect != null) {
				buildPaticleSpigot(p, l, c, effect, 1);
			}
			loopValue++;
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
