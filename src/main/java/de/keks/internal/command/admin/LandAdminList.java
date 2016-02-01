package de.keks.internal.command.admin;

import static de.keks.internal.I18n.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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

public class LandAdminList extends MainCore {

	public LandAdminList(CommandSetupAdmin handler) {

		super(true);
		this.setupAdmin = handler;
	}

	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("iLand.admin.list")) {
			setupAdmin.executorServiceCommands.submit(new Runnable() {
				public void run() {
					if (args.length <= 3) {
						int pageNumb = 0;
						Player p = (Player) sender;
						WorldGuardPlugin wg = setupAdmin.getILandInstance().getHookManager().getWorldGuardManager()
								.getWorldGuardPlugin();
						RegionManager rm = wg.getRegionManager(p.getWorld());
						try {
							if (args.length == 3) {
								int number = Integer.valueOf(args[2]);
								if (number < 1) {
									pageNumb = 0;
								} else {
									pageNumb = Integer.valueOf(args[2]) - 1;
								}

							} else if (args.length == 2) {
								pageNumb = 0;
							} else {
								p.sendMessage(translate("messages.adminError"));
								return;
							}
						} catch (Exception e) {
							p.sendMessage(translate("messages.notANumber", args[2]));
							return;
						}

						@SuppressWarnings("deprecation")
						OfflinePlayer pc = Bukkit.getOfflinePlayer(args[1]);

						if (pc == null) {
							p.sendMessage(translate("messages.adminNoPlayer"));
							return;
						}
						int rgCount = rm.getRegionCountOfPlayer(wg.wrapOfflinePlayer(pc));
						if (pageNumb * 10 > rgCount) {
							sender.sendMessage(translate("messages.adminErrorPage", args[1]));
							return;
						}
						List<String> subList = getLandsOfPlayer(pc, p.getWorld()).subList(pageNumb * 10,
								pageNumb * 10 + 10 > rgCount ? rgCount : pageNumb * 10 + 10);
						sender.sendMessage(translate("messages.adminPage", args[1], rgCount, (pageNumb * 10 + 1),
								(pageNumb * 10 + 10)));
						int counter = pageNumb * 10 + 1;

						for (String name : subList) {
							ProtectedRegion region = rm.getRegion(name);
							final int posX = region.getMinimumPoint().getBlockX() + 8;
							final int posZ = region.getMinimumPoint().getBlockZ() + 8;
							sender.sendMessage(
									"§a" + counter + ". §6" + name + " §a(X,Z)   [" + posX + ", " + posZ + "]");
							counter++;
						}
					}
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

	public List<String> getLandsOfPlayer(OfflinePlayer p, World world) {
		WorldGuardPlugin wg = setupAdmin.getILandInstance().getHookManager().getWorldGuardManager()
				.getWorldGuardPlugin();
		RegionManager rm = wg.getRegionManager(world);
		List<String> toReturn = new ArrayList<String>();

		for (Map.Entry<String, ProtectedRegion> entry : rm.getRegions().entrySet()) {
			if (entry.getValue().getOwners().contains(p.getUniqueId())) {
				toReturn.add(entry.getKey());
			}
		}

		return toReturn;
	}

}
