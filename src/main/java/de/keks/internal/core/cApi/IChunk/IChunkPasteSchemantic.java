package de.keks.internal.core.cApi.IChunk;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.keks.iChunk.IChunkAPI;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.core.cApi.KChunk.KChunkFacade;
import thirdparty.ftp.it.sauronsoftware.ftp4j.ILandFTP;

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

		if (ConfigValues.ftpEnabled) {
			File local = new File("plugins/iLand/saves/" + player.getUniqueId().toString(), region + ".iLand");
			File saves = new File("plugins/iLand/" + File.separator + "saves");
			if (!saves.exists()) {
				saves.mkdirs();
			}

			File uuid = new File("plugins/iLand/saves" + File.separator + player.getUniqueId().toString());
			if (!uuid.exists()) {
				uuid.mkdirs();
			}

			if (ILandFTP.download(region, local, player.getUniqueId().toString())) {
			}
		}

		File schematicFile = new File("plugins/iLand/saves/" + player.getUniqueId().toString(), region + ".iLand");
		Location loc = new Location(player.getWorld(), player.getLocation().getChunk().getX() * 16, 0,
				player.getLocation().getChunk().getZ() * 16);
		IChunkAPI.streamSchematicAsync(schematicFile, loc);

		if (schematicFile.exists()) {
			schematicFile.delete();
		}
		KChunkFacade.refreshChunk(player.getLocation().getChunk());
		KChunkFacade.teleportSave(player.getLocation().getChunk());

		isSuccessful = true;

	}

}
