package de.keks.internal.core.cApi.IChunk;

import java.util.HashSet;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.iChunk.IChunkAPI;
import de.keks.iChunk.object.BlockReplaceBlock;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class IChunkFacade {

	/**
	 * <ul>
	 * <li>Load and paste a .Cubit schematic file from local Cubit folder or a
	 * remote ftp server</li>
	 * <li>Example: world_4_-16.cubit</li>
	 * </ul>
	 * 
	 */
	public static boolean pasteSchematic(Player player, String region) {
		IChunkPasteSchemantic newSchematic = new IChunkPasteSchemantic(player, region);
		return newSchematic.isSuccessful;
	}

	/**
	 * <ul>
	 * <li>Save a selected ProtectedRegion in a .cubit schematic file to the
	 * local Cubit folder or a remote ftp server</li>
	 * <li>Example: world_4_-16.cubit</li>
	 * </ul>
	 * 
	 */
	public static boolean createSchematic(Player player, ProtectedRegion region) {
		IChunkCreateSchemantic newSchematic = new IChunkCreateSchemantic(player, region);
		return newSchematic.isSuccessful;
	}

	/**
	 * <ul>
	 * <li>Regenerate the chunk on the selected player location</li>
	 * </ul>
	 * 
	 */
	/*
	 * public static boolean regenerateRegion(Player player) { return
	 * KChunkRegenerateCO.regenerate(player); }
	 */

	/**
	 * <ul>
	 * <li>Refresh the chunk on the selected chunk location</li>
	 * <li>Method refreshChunk(chunkX, chunkZ) is deprecated</li>
	 * </ul>
	 * 
	 */
	/*
	 * public static void refreshChunk(Chunk chunk) {
	 * KChunkResendChunk.refreshChunk(chunk); return; }
	 */

	/**
	 * <ul>
	 * <li>Set the biome in a ProtectedRegion</li>
	 * </ul>
	 * 
	 */
	/*
	 * public static boolean setBiome(Player player, String regionid, Biome
	 * biome) { return KChunkBiomeChange.createCubitBiome(player, regionid,
	 * biome); }
	 */

	/**
	 * <ul>
	 * <li>Teleport all players on the selected chunk to the highest Y cord</li>
	 * </ul>
	 * 
	 */

	/*
	 * public static void teleportSave(Chunk chunk) {
	 * KChunkTeleport.saveTeleport(chunk); return; }
	 */

	public static void chunkHighlight(final Player p, final Location l, final Chunk c, final Effect effect) {
		IChunkAPI.chunkHighlight(p, l, c, effect);
		return;
	}

	public static void chunkBlockHighlight(final Chunk c, final Material material) {
		IChunkAPI.chunkBlockHighlight(c, material);
		return;
	}

	public static void replaceBlockID(Chunk chunk, final HashSet<BlockReplaceBlock> blockIDs) {
		IChunkAPI.chunkReplaceBlock(chunk, blockIDs);
		return;
	}

}
