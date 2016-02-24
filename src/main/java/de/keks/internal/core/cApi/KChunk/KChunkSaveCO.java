package de.keks.internal.core.cApi.KChunk;

import java.io.File;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
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

@SuppressWarnings("deprecation")
public class KChunkSaveCO {
	public static boolean saveRegion(Player player, ProtectedRegion region) {
		try {
			Vector min = new Vector(region.getMinimumPoint());
			Vector max = new Vector(region.getMaximumPoint());
			EditSession es = WorldEdit.getInstance().getEditSessionFactory()
					.getEditSession(new BukkitWorld(player.getWorld()), 0x3b9ac9ff);
			CuboidClipboard c1 = new CuboidClipboard(max.subtract(min).add(new Vector(1, 1, 1)), min);
			c1.copy(es);

			File saves = new File("plugins/Cubit/" + File.separator + "saves");
			if (!saves.exists()) {
				saves.mkdirs();
			}

			File uuid = new File("plugins/Cubit/saves" + File.separator + player.getUniqueId().toString());
			if (!uuid.exists()) {
				uuid.mkdirs();
			}

			File file = new File("plugins/Cubit/saves/" + player.getUniqueId().toString(), region.getId() + ".cubit");
			if (file.exists()) {
				file.delete();
			}
			c1.saveSchematic(file);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

}
