package de.keks.internal.core.cubli;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class InternalResendChunk {

	private static String getVersion() {
		String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",");
		if (array.length == 4)
			return array[3] + ".";
		return "";
	}

	@SuppressWarnings("deprecation")
	public static void refreshChunk(final Chunk chunk) {

		if (getVersion().contains("1_8_R1")) {
			Version1_8_R1.refreshChunk(chunk);

		} else if (getVersion().contains("1_8_R2")) {
			Version1_8_R2.refreshChunk(chunk);

		} else if (getVersion().contains("1_8_R3")) {
			Version1_8_R3.refreshChunk(chunk);

		} else {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.getWorld().refreshChunk(chunk.getX(), chunk.getZ())) {
				}
			}
		}
	}
}
