package de.keks.internal.core.cApi.IChunk;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.keks.iChunk.IChunkAPI;
import de.keks.internal.core.cApi.KChunk.KChunkFacade;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class IChunkPasteSchemantic {
	public boolean isSuccessful = false;

	public IChunkPasteSchemantic(Player player, String region) {

		File schematicFile = new File("plugins/iLand/saves/" + player.getUniqueId().toString(), region + ".iLand");
		Location loc = new Location(player.getWorld(), player.getLocation().getChunk().getX() * 16, 0,
				player.getLocation().getChunk().getZ() * 16);
		IChunkAPI.streamSchematicAsync(schematicFile, loc);

		// if (schematicFile.exists()) {
		// schematicFile.delete();
		// }
		KChunkFacade.refreshChunk(player.getLocation().getChunk());
		KChunkFacade.teleportSave(player.getLocation().getChunk());

		isSuccessful = true;

	}

}
