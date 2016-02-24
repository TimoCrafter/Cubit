package de.keks.internal.core.cApi.KChunk;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalPlayer;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;

import de.keks.cubit.CubitPlugin;

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
public class KChunkPasteCO {
	public static boolean loadRegion(final Player player, final String region) {

		Bukkit.getScheduler().scheduleSyncDelayedTask(CubitPlugin.inst(), new Runnable() {
			public void run() {
				WorldEditPlugin we = CubitPlugin.inst().getHookManager().getWorldEditManager().getWorldEditPlugin();
				final Vector min;
				final Vector2D min2D;
				CuboidClipboard cc;
				try {
					cc = SchematicFormat.MCEDIT.load(
							new File("plugins/Cubit/saves/" + player.getUniqueId().toString(), region + ".cubit"));
					LocalPlayer wePly = we.wrapPlayer(player);
					EditSession es = we.getSession(player).createEditSession(wePly);
					min2D = new Vector2D(player.getLocation().getChunk().getX() * 16,
							player.getLocation().getChunk().getZ() * 16);
					min = new Vector(min2D.getBlockX(), 0, min2D.getBlockZ());
					com.sk89q.worldedit.Vector myV = min.toBlockVector();
					cc.paste(es, myV, false);
				} catch (DataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (MaxChangedBlocksException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
				File remove = new File("plugins/Cubit/saves/" + player.getUniqueId().toString(), region + ".cubit");
				if (remove.exists()) {
					remove.delete();
				}
				KChunkFacade.refreshChunk(player.getLocation().getChunk());
				KChunkFacade.teleportSave(player.getLocation().getChunk());
			}
		});
		return true;
	}
}
