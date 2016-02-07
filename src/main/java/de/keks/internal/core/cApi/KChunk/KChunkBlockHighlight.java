package de.keks.internal.core.cApi.KChunk;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class KChunkBlockHighlight implements Runnable {

	private Plugin plugin;
	private Chunk chunk;
	private Material torchMaterial;

	private TorchPlaceWork work;

	public KChunkBlockHighlight(Plugin plugin, Chunk chunk, Material torchMaterial) {
		this.plugin = plugin;
		this.chunk = chunk;
		this.torchMaterial = torchMaterial;

		work = new TorchPlaceWork();
	}

	@Override
	public void run() {
		if (work.isWorkDone()) {
			return;
		}

		for (int count = 0; count < 4 && !work.isWorkDone(); work.i++, count++) {
			Location block1 = chunk.getBlock(work.i, 0, 0).getLocation();
			block1.setY(getY(chunk.getWorld(), block1.getBlockX(), block1.getBlockZ()));
			chunk.getWorld().getBlockAt(block1).setType(torchMaterial);

			Location block2 = chunk.getBlock(15, 0, work.i).getLocation();
			block2.setY(getY(chunk.getWorld(), block2.getBlockX(), block2.getBlockZ()));
			chunk.getWorld().getBlockAt(block2).setType(torchMaterial);

			Location block3 = chunk.getBlock((15 - work.i), 0, 15).getLocation();
			block3.setY(getY(chunk.getWorld(), block3.getBlockX(), block3.getBlockZ()));
			chunk.getWorld().getBlockAt(block3).setType(torchMaterial);

			Location block4 = chunk.getBlock(0, 0, (15 - work.i)).getLocation();
			block4.setY(getY(chunk.getWorld(), block4.getBlockX(), block4.getBlockZ()));
			chunk.getWorld().getBlockAt(block4).setType(torchMaterial);
		}
		if (!work.isWorkDone()) {
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this);
		}
	}

	public class TorchPlaceWork {
		public int i = 0;

		public boolean isWorkDone() {
			return i == 15;
		}
	}

	public int getY(World world, int x, int z) {
		int y;
		for (y = 255; y > 0; y--) {
			if ((world.getBlockAt(x, y, z).getType() != Material.AIR)
					&& (world.getBlockAt(x, y, z).getType() != Material.ENDER_PORTAL_FRAME)
					&& (world.getBlockAt(x, y, z).getType() != Material.BEDROCK)) {
				if ((world.getBlockAt(x, y, z).getType() != Material.TORCH)
						&& (world.getBlockAt(x, y, z).getType() != Material.REDSTONE_TORCH_ON)
						&& (world.getBlockAt(x, y, z).getType() != Material.DEAD_BUSH)
						&& (world.getBlockAt(x, y, z).getType() != Material.DOUBLE_PLANT)
						&& (world.getBlockAt(x, y, z).getType() != Material.SNOW)) {
					break;
				}
				y--;
				break;
			}
		}
		return y + 1;
	}
}
