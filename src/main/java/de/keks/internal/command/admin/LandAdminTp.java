package de.keks.internal.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.internal.I18n;
import de.keks.internal.register.CommandSetupAdmin;
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
public class LandAdminTp extends MainCore {

	public LandAdminTp(CommandSetupAdmin handler) {

		super(true);
		this.setupAdmin = handler;
	}

	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.admin.tp")) {

			setupAdmin.executorServiceCommands.submit(new Runnable() {
				public void run() {
					if (args.length <= 3) {
						Player pc = null;
						ProtectedRegion region = null;
						Player p = (Player) sender;
						WorldGuardPlugin wg = setupAdmin.getCubitInstance().getHookManager().getWorldGuardManager()
								.getWorldGuardPlugin();
						RegionManager rm = wg.getRegionManager(p.getWorld());
						if (args.length == 3) {
							region = rm.getRegion(args[2]);
							pc = Bukkit.getPlayer(args[1]);
							if (pc == null) {
								p.sendMessage(I18n.translate("messages.adminNotOnline", args[1]));
								return;
							}

						} else if (args.length == 2) {
							region = rm.getRegion(args[1]);
							pc = (Player) sender;
						} else {
							p.sendMessage(I18n.translate("messages.adminError"));
							return;
						}
						if (region == null) {
							p.sendMessage(I18n.translate("messages.noRegion"));
							return;
						}
						if (!ProtectedRegion.isValidId(region.getId())) {
							p.sendMessage(I18n.translate("messages.noRegion"));
							return;
						}
						final World w = p.getLocation().getWorld();
						final int portX = region.getMinimumPoint().getBlockX() + 8;
						final int portZ = region.getMinimumPoint().getBlockZ() + 8;
						final int portY = getHighestBlockYAt(portX, portZ, w) + 1;
						Location l = new Location(w, portX, portY, portZ);
						p.teleport(l);
						p.sendMessage(I18n.translate("messages.adminTp", region.getId()));

					}
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

	public int getHighestBlockYAt(int x, int z, World world) {
		if (ensureChunkLoaded(x, z, world)) {
			try {
				Thread.sleep(500);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return world.getHighestBlockYAt(x, z);
	}

	public boolean ensureChunkLoaded(int x, int z, World world) {
		Location randlocation = new Location(world, Double.parseDouble(Integer.toString(x)), 0.0,
				Double.parseDouble(Integer.toString(z)));
		Chunk chunk = world.getChunkAt(randlocation);
		if (!world.isChunkLoaded(chunk)) {
			world.loadChunk(chunk);
			return true;
		}
		return true;
	}

}
