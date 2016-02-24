package de.keks.internal.command.land;

import static de.keks.internal.I18n.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.internal.I18n;
import de.keks.internal.core.tasks.RegionSaveTask;
import de.keks.internal.register.CommandSetupLand;
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

public class LandMemberClearAll extends MainCore {

	public LandMemberClearAll(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.land.cleanmember")) {

			final Player player = (Player) sender;
			final World world = player.getWorld();

			setupLand.executorServiceCommands.submit(new Runnable() {
				public void run() {
					WorldGuardPlugin wg = setupLand.getCubitInstance().getHookManager().getWorldGuardManager()
							.getWorldGuardPlugin();
					RegionManager rm = wg.getRegionManager(player.getWorld());

					List<String> regionListOfPlayer = getLandsOfPlayer(player);
					if (regionListOfPlayer.size() <= 30) {

						for (String id : regionListOfPlayer) {
							ProtectedRegion region = rm.getRegion(id);
							DefaultDomain dd = region.getMembers();

							dd.removeAll();

							region.setMembers(dd);
						}
					} else {

						int loops = regionListOfPlayer.size() / 30 + 1;
						for (int i = 0; i < loops; i++) {
							List<String> list = regionListOfPlayer.subList(i * 30,
									30 * i + 29 >= regionListOfPlayer.size() ? regionListOfPlayer.size() : 30 * i + 29);
							for (String id : list) {
								ProtectedRegion region = rm.getRegion(id);
								DefaultDomain dd = region.getMembers();

								dd.removeAll();

								region.setMembers(dd);
							}
						}

					}

					setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
					player.sendMessage(translate("messages.memberClean", "ALL LANDS"));
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}

		return true;
	}

	public List<String> getLandsOfPlayer(Player p) {
		WorldGuardPlugin wg = setupLand.getCubitInstance().getHookManager().getWorldGuardManager()
				.getWorldGuardPlugin();
		RegionManager rm = wg.getRegionManager(p.getWorld());
		List<String> toReturn = new ArrayList<String>();

		for (Map.Entry<String, ProtectedRegion> entry : rm.getRegions().entrySet()) {
			if (entry.getValue().getOwners().contains(p.getUniqueId())) {
				toReturn.add(entry.getKey());
			}
		}

		return toReturn;
	}

}
