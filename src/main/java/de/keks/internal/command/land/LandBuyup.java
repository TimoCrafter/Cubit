package de.keks.internal.command.land;

import static de.keks.internal.I18n.translate;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.core.cApi.ChunkApi;
import de.keks.internal.plugin.hooks.classes.EconomyHook;
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

public class LandBuyup extends MainCore {
	private static final SimpleDateFormat LAST_SEEN = new SimpleDateFormat("dd.MM.yyyy");

	public LandBuyup(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.land.buyup")) {
			final Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);

			setupLand.executorServiceCommands.submit(new Runnable() {
				public void run() {
					if (isServerRegion(chunkX, chunkZ, world)) {
						sender.sendMessage(I18n.translate("messages.isServerregion"));
						return;
					}
					RegionManager manager = getWorldGuard().getRegionManager(world);
					String regionName = getRegionName(chunkX, chunkZ, world);

					if (!manager.hasRegion(regionName)) {
						player.sendMessage(translate("messages.noRegionHere"));
						return;
					}

					ProtectedRegion region = getRegion(world, regionName);
					if (region.isOwner(localplayer)) {
						player.sendMessage(translate("messages.cannotBuyOwnRegion"));
						return;
					}

					double costs = calculateCosts(player, world, true);
					if (!hasEnoughToBuy(player, costs)) {
						player.sendMessage(translate("messages.notEnoughMoney"));
						return;
					}

					if (!wasPlayerTooLongOff(getRegion(world, regionName), player)) {
						int buytime;
						if (region.isMember(localplayer)) {
							buytime = (int) ConfigValues.buyupMembers;
						} else {
							buytime = (int) ConfigValues.buyupNoMembers;
						}

						sender.sendMessage(I18n.translate("messages.errorBuyup", buytime));
						long buyDate = getBuyupInfoDate(region, player);
						sender.sendMessage(
								I18n.translate("messages.regionBuyupInfoDate", LAST_SEEN.format(new Date(buyDate))));
						return;
					}
					region.getMembers().removeAll();
					region.getOwners().removeAll();
					region.getOwners().addPlayer(localplayer);

					moneyTransfer(player, null, costs);

					ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
							Effect.HAPPY_VILLAGER);

					sender.sendMessage(I18n.translate("messages.buyRegion", regionName, costs));
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

	private boolean hasEnoughToBuy(Player player, double costs) {
		EconomyHook economyManager = setupLand.getCubitInstance().getHookManager().getEconomyManager();
		return economyManager.getMoney(player) >= costs;
	}
}
