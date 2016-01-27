package de.keks.internal.core.cApi.KChunk;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
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

public class CChunk {

	/**
	 * <ul>
	 * <li>Load and paste a .cubit schematic file from local cubit folder or a
	 * remote ftp server</li>
	 * <li>Example: world_4_-16.cubit</li>
	 * </ul>
	 * 
	 */
	public static boolean pasteRegion(Player player, String region) {
		return InternalPasteCO.loadRegion(player, region);
	}

	/**
	 * <ul>
	 * <li>Save a selected ProtectedRegion in a .cubit schematic file to the
	 * local cubit folder or a remote ftp server</li>
	 * <li>Example: world_4_-16.cubit</li>
	 * </ul>
	 * 
	 */
	public static boolean saveRegion(Player player, ProtectedRegion region) {
		return InternalSaveCO.saveRegion(player, region);
	}

	/**
	 * <ul>
	 * <li>Regenerate the chunk on the selected player location</li>
	 * </ul>
	 * 
	 */
	public static boolean regenerateRegion(Player player) {
		return InternalRegenerateCO.regenerate(player);
	}

	/**
	 * <ul>
	 * <li>Refresh the chunk on the selected chunk location</li>
	 * <li>Method refreshChunk(chunkX, chunkZ) is deprecated</li>
	 * </ul>
	 * 
	 */
	public static void refreshChunk(Chunk chunk) {
		InternalResendChunk.refreshChunk(chunk);
		return;
	}

	/**
	 * <ul>
	 * <li>Set the biome in a ProtectedRegion</li>
	 * </ul>
	 * 
	 */
	public static boolean setBiome(Player player, String regionid, Biome biome) {
		return InternalBiomeChange.createCubitBiome(player, regionid, biome);
	}

	/**
	 * <ul>
	 * <li>Teleport all players on the selected chunk to the highest Y cord</li>
	 * </ul>
	 * 
	 */
	public static void teleportSave(Chunk chunk) {
		InternalTeleport.saveTeleport(chunk);
		return;
	}

}
