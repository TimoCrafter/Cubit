package de.keks.internal.command.store;

import static de.keks.internal.I18n.translate;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.ConfigValues;
import de.keks.internal.I18n;
import de.keks.internal.core.cubli.Cubli;
import de.keks.internal.core.database.DataController;
import de.keks.internal.core.tasks.RegionSaveTask;
import de.keks.internal.plugin.hooks.classes.EconomyHook;
import de.keks.internal.register.CommandSetupStore;
import de.keks.internal.register.CubitCore;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CMD_Store_Save extends CubitCore {
	public CMD_Store_Save(CommandSetupStore handler) {
		super(true);
		this.setupStore = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.lstore.save")) {

			final Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);

			setupStore.executorServiceCommands.submit(new Runnable() {
				public void run() {
					Player player = (Player) sender;
					RegionManager manager = getWorldGuard().getRegionManager(world);

					String regionName = getRegionName(chunkX, chunkZ, world);

					if (!manager.hasRegion(regionName)) {
						player.sendMessage(translate("messages.noRegionHere"));
						return;
					}

					ProtectedRegion region = getRegion(world, regionName);
					if (region == null) {
						player.sendMessage(translate("messages.noRegionHere"));
						return;
					}
					if (!region.isOwner(localplayer)) {
						player.sendMessage(translate("messages.noPermissionForRegion"));
						return;
					}

					double costs = ConfigValues.landSave;
					if (!hasEnoughToBuy(player, costs)) {
						player.sendMessage(translate("messages.notEnoughMoney"));
						return;
					}

					player.sendMessage(translate("messages.storeTask", regionName));
					if (DataController.saveRegionSQL(player, region.getId(),
							CubitPlugin.inst().getConfig().getBoolean("ftp.enable"))) {
						moneyTransfer(player, null, costs);
						if (Cubli.saveRegion(player, region)) {
							if (Cubli.regenerateRegion(player)) {
								manager.removeRegion(regionName);
								setupStore.getOfferManager().removeOffer(regionName);
								sender.sendMessage(I18n.translate("messages.storeSave", regionName, region.getId()));
								setupStore.executorServiceRegions
										.submit(new RegionSaveTask(getWorldGuard(), null, world));
							}
						}
					} else {
						sender.sendMessage(I18n.translate("messages.storeSaveAlready"));
					}
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

	private boolean hasEnoughToBuy(Player player, double costs) {
		EconomyHook economyManager = setupStore.getCubitInstance().getHookManager().getEconomyManager();
		return economyManager.getMoney(player) >= costs;
	}

}
