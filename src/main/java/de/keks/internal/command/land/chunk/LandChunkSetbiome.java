package de.keks.internal.command.land.chunk;

import static de.keks.internal.I18n.translate;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.core.cApi.ChunkApi;
import de.keks.internal.core.tasks.RegionSaveTask;
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

public class LandChunkSetbiome extends MainCore

{
	public LandChunkSetbiome(CommandSetupLand handler, Biome biome) {
		super(true);
		this.setupLand = handler;
	}

	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.landEdit.setbiome")) {

			Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);
			if (args.length == 2) {
				this.setupLand.executorServiceCommands.submit(new Runnable() {
					public void run() {
						Player player = (Player) sender;
						String regionName = getRegionName(chunkX, chunkZ, world);
						if (!ProtectedRegion.isValidId(regionName)) {
							player.sendMessage(I18n.translate("messages.noRegionHere", new Object[0]));
							return;
						}
						ProtectedRegion region = getRegion(world, regionName);
						if (region == null) {
							player.sendMessage(I18n.translate("messages.noRegionHere", new Object[0]));
							return;
						}
						if (!region.isOwner(localplayer)) {
							player.sendMessage(I18n.translate("messages.noPermissionForRegion", new Object[0]));
							return;
						}
						try {
							Biome.valueOf(args[1].toUpperCase());
						} catch (IllegalArgumentException x) {
							sender.sendMessage(I18n.translate("messages.noBiome"));
							return;
						}
						if (player.getLocation().getBlock().getBiome() == Biome.valueOf(args[1].toUpperCase())) {
							sender.sendMessage(I18n.translate("messages.alreadyBiome"));
							return;
						}
						double costs = ConfigValues.setBiome;
						if (!hasEnoughToBuy(player, costs)) {
							player.sendMessage(translate("messages.notEnoughMoney"));
							return;
						}
						sender.sendMessage(I18n.translate("messages.storeTask", regionName));
						if (ChunkApi.setBiome(player, regionName, Biome.valueOf(args[1].toUpperCase()))) {
							moneyTransfer(player, null, costs);
						}

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.COLOURED_DUST);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
					}
				});
				return true;

			} else {
				sender.sendMessage(I18n.translate("messages.errorBiome"));
			}

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