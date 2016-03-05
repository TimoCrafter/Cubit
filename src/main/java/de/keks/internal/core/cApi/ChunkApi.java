package de.keks.internal.core.cApi;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.internal.core.cApi.KChunk.KChunkFacade;
import de.keks.internal.register.MainCore;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class ChunkApi {

	/**
	 * <ul>
	 * <li>Load and paste a .cubit schematic file from local Cubit folder or a
	 * remote ftp server</li>
	 * <li>Example: world_4_-16.cubit</li>
	 * </ul>
	 * 
	 */
	public static boolean pasteRegion(Player player, String region) {

		return KChunkFacade.pasteRegion(player, region);

	}

	/**
	 * <ul>
	 * <li>Save a selected ProtectedRegion in a .cubit schematic file to the
	 * local Cubit folder or a remote ftp server</li>
	 * <li>Example: world_4_-16.cubit</li>
	 * </ul>
	 * 
	 */
	public static boolean saveRegion(Player player, ProtectedRegion region) {

		return KChunkFacade.saveRegion(player, region);

	}

	/**
	 * <ul>
	 * <li>Regenerate the chunk on the selected player location</li>
	 * </ul>
	 * 
	 */
	public static boolean regenerateRegion(Player player) {

		return KChunkFacade.regenerateRegion(player);

	}

	/**
	 * <ul>
	 * <li>Refresh the chunk on the selected chunk location</li>
	 * <li>Method refreshChunk(chunkX, chunkZ) is deprecated</li>
	 * </ul>
	 * 
	 */
	public static void refreshChunk(Chunk chunk) {

		KChunkFacade.refreshChunk(chunk);
		return;

	}

	/**
	 * <ul>
	 * <li>Set the biome in a ProtectedRegion</li>
	 * </ul>
	 * 
	 */
	public static boolean setBiome(Player player, String regionid, Biome biome) {

		return KChunkFacade.setBiome(player, regionid, biome);

	}

	/**
	 * <ul>
	 * <li>Teleport all players on the selected chunk to the highest Y cord</li>
	 * </ul>
	 * 
	 */
	public static void teleportSave(Chunk chunk) {

		KChunkFacade.teleportSave(chunk);

	}

	public static void chunkHighligh(final Player p, final Location l, final Chunk c, final Effect effect) {
		if (MainCore.isSpigot()) {

			KChunkFacade.chunkHighlight(p, l, c, effect);
			return;

		}
	}

	public static void chunkBlockHighligh(final Chunk c, final Material material) {

		KChunkFacade.chunkBlockHighlight(c, material);
		return;

	}

}
