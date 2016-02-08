package de.keks.internal.core.cApi;

import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.iChunk.object.BlockReplaceBlock;
import de.keks.iLand.ILandPlugin;
import de.keks.internal.core.cApi.IChunk.IChunkFacade;
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

	private static boolean iChunk = ILandPlugin.inst().isIChunkInstance();

	/**
	 * <ul>
	 * <li>Load and paste a .iLand schematic file from local iLand folder or a
	 * remote ftp server</li>
	 * <li>Example: world_4_-16.iLand</li>
	 * </ul>
	 * 
	 */
	public static boolean pasteRegion(Player player, String region) {
		if (iChunk) {
			return IChunkFacade.pasteSchematic(player, region);
		} else {
			return KChunkFacade.pasteRegion(player, region);
		}

	}

	/**
	 * <ul>
	 * <li>Save a selected ProtectedRegion in a .iLand schematic file to the
	 * local iLand folder or a remote ftp server</li>
	 * <li>Example: world_4_-16.iLand</li>
	 * </ul>
	 * 
	 */
	public static boolean saveRegion(Player player, ProtectedRegion region) {
		if (iChunk) {
			return IChunkFacade.createSchematic(player, region);
		} else {
			return KChunkFacade.saveRegion(player, region);
		}
	}

	/**
	 * <ul>
	 * <li>Regenerate the chunk on the selected player location</li>
	 * </ul>
	 * 
	 */
	public static boolean regenerateRegion(Player player) {
		if (iChunk) {
			return false;
		} else {
			return KChunkFacade.regenerateRegion(player);
		}
	}

	/**
	 * <ul>
	 * <li>Refresh the chunk on the selected chunk location</li>
	 * <li>Method refreshChunk(chunkX, chunkZ) is deprecated</li>
	 * </ul>
	 * 
	 */
	public static void refreshChunk(Chunk chunk) {
		if (iChunk) {
			return;
		} else {
			KChunkFacade.refreshChunk(chunk);
			return;
		}
	}

	/**
	 * <ul>
	 * <li>Set the biome in a ProtectedRegion</li>
	 * </ul>
	 * 
	 */
	public static boolean setBiome(Player player, String regionid, Biome biome) {
		if (iChunk) {
			return false;
		} else {
			return KChunkFacade.setBiome(player, regionid, biome);
		}
	}

	/**
	 * <ul>
	 * <li>Teleport all players on the selected chunk to the highest Y cord</li>
	 * </ul>
	 * 
	 */
	public static void teleportSave(Chunk chunk) {
		if (iChunk) {
			return;
		} else {
			KChunkFacade.teleportSave(chunk);
			return;
		}
	}

	public static void chunkHighligh(final Player p, final Location l, final Chunk c, final Effect effect) {
		if (MainCore.isSpigot1()) {
			if (iChunk) {
				IChunkFacade.chunkHighlight(p, l, c, effect);
				return;
			} else {
				KChunkFacade.chunkHighlight(p, l, c, effect);
				return;
			}
		}
	}

	public static void chunkBlockHighligh(final Chunk c, final Material material) {
		if (iChunk) {
			IChunkFacade.chunkBlockHighlight(c, material);
			return;
		} else {
			KChunkFacade.chunkBlockHighlight(c, material);
			return;
		}
	}

	public static void replaceBlockID(Chunk chunk, final HashSet<BlockReplaceBlock> blockIDs) {
		if (iChunk) {
			IChunkFacade.replaceBlockID(chunk, blockIDs);
			return;
		} else {
			return;
		}
	}

}
