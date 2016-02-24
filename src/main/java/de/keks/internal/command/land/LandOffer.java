package de.keks.internal.command.land;

import static de.keks.internal.I18n.translate;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.core.cApi.ChunkApi;
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

public class LandOffer extends MainCore {
	public LandOffer(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.land.offer")) {

			final Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);

			setupLand.executorServiceCommands.submit(new Runnable() {
				public void run() {
					Player player = (Player) sender;
					String regionName = getRegionName(chunkX, chunkZ, world);

					if (!ProtectedRegion.isValidId(regionName)) {
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

					if (args.length < 2) {
						if (!setupLand.getOfferManager().addOffer(regionName, 0, player.getUniqueId())) {
							player.sendMessage(translate("messages.offerError"));
						}
					} else {
						if (!NumberUtils.isNumber(args[1])) {
							player.sendMessage(translate("messages.notANumber", args[1]));
						} else {
							if (!setupLand.getOfferManager().addOffer(regionName, NumberUtils.toDouble(args[1]),
									player.getUniqueId())) {
								player.sendMessage(translate("messages.offerError"));
							} else {
								String wert1 = "0";
								String wert2 = args[1].toLowerCase();
								if (wert2.equalsIgnoreCase(wert1)) {
									player.sendMessage(translate("messages.offerCancel", regionName));

									ChunkApi.chunkHighligh(player, player.getLocation(),
											player.getLocation().getChunk(), Effect.WATERDRIP);

								} else {

									ChunkApi.chunkHighligh(player, player.getLocation(),
											player.getLocation().getChunk(), Effect.LAVADRIP);

									String formattedMoney = setupLand.getCubitInstance().getHookManager()
											.getEconomyManager().formatMoney(NumberUtils.toDouble(args[1]));
									player.sendMessage(translate("messages.offerLand", regionName));
									setupLand.getCubitInstance().getServer().broadcastMessage(translate(
											"messages.offerInfoGlobal", sender.getName(), regionName, formattedMoney));
								}
							}
						}
					}
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}
}
