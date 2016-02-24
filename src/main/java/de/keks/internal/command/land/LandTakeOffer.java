package de.keks.internal.command.land;

import static de.keks.internal.I18n.translate;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.core.cApi.ChunkApi;
import de.keks.internal.core.tasks.RegionSaveTask;
import de.keks.internal.plugin.hooks.classes.EconomyHook;
import de.keks.internal.register.CommandSetupLand;
import de.keks.internal.register.MainCore;

public class LandTakeOffer extends MainCore {

	/**
	 * Copyright:
	 * <ul>
	 * <li>Autor: Kekshaus</li>
	 * <li>2016</li>
	 * <li>www.minegaming.de</li>
	 * </ul>
	 * 
	 */

	public LandTakeOffer(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, String[] args) {
		if (sender.hasPermission("cubit.land.takeoffer")) {
			final Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);

			setupLand.executorServiceCommands.submit(new Runnable() {

				public void run() {
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

					if (region.isOwner(localplayer)) {
						player.sendMessage(translate("messages.cannotBuyOwnRegion"));
						return;
					}

					if (!setupLand.getOfferManager().isOffered(regionName)) {
						player.sendMessage(translate("messages.regionNotOffered"));
						return;
					}

					double costs = setupLand.getOfferManager().getOffer(regionName);

					if (!hasEnoughToBuy(player, costs)) {
						player.sendMessage(translate("messages.notEnoughMoney"));
						return;
					}
					OfflinePlayer oldowner = null;
					for (UUID uuid : region.getOwners().getUniqueIds()) {
						oldowner = Bukkit.getOfflinePlayer(uuid);
					}
					moneyTransfer(player, oldowner, costs);
					region.getOwners().removeAll();
					region.getMembers().removeAll();
					region.getOwners().addPlayer(localplayer);

					setupLand.getOfferManager().removeOffer(regionName);

					sender.sendMessage(I18n.translate("messages.buyRegion", regionName, costs));

					ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
							Effect.HAPPY_VILLAGER);

					setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
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
