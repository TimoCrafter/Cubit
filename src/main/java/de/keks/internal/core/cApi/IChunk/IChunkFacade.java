package de.keks.internal.core.cApi.IChunk;

import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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
	 * <li>Load and paste a .iLand schematic file from local iLand folder or a
	 * remote ftp server</li>
	 * <li>Example: world_4_-16.iLand</li>
	 * </ul>
	 * 
	 */
	public static boolean pasteSchematic(Player player, String region) {
		IChunkPasteSchemantic newSchematic = new IChunkPasteSchemantic(player, region);
		return newSchematic.isSuccessful;
	}

	/**
	 * <ul>
	 * <li>Save a selected ProtectedRegion in a .iLand schematic file to the
	 * local iLand folder or a remote ftp server</li>
	 * <li>Example: world_4_-16.iLand</li>
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
	 * biome) { return KChunkBiomeChange.createILandBiome(player, regionid,
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

}
