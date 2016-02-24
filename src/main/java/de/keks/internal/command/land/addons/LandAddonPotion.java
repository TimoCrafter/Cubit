package de.keks.internal.command.land.addons;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.core.cApi.ChunkApi;
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

public class LandAddonPotion extends MainCore {

	public LandAddonPotion(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.flag.pvp")) {

			Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final String statuson = I18n.translate("messages.optionson");
			final String statusoff = I18n.translate("messages.optionsoff");
			final String flag = I18n.translate("optionName.potion");
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);

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
					//////////////////////////////////////////////////////////////////////////////
					if (args.length < 2) {
					} else if (args[1].toString().equalsIgnoreCase("on")) {
						if (region.getFlag(DefaultFlag.POTION_SPLASH) == StateFlag.State.ALLOW) {
							player.sendMessage(I18n.translate("messages.optionsAlready", flag, statuson));
							return;
						}
						region.setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.ALLOW);

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.FLAME);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statuson));
						return;

					} else if (args[1].toString().equalsIgnoreCase("off")) {
						if (region.getFlag(DefaultFlag.POTION_SPLASH) == StateFlag.State.DENY) {
							player.sendMessage(I18n.translate("messages.optionsAlready", flag, statusoff));
							return;
						}
						region.setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.HAPPY_VILLAGER);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statusoff));
						return;
					}
					if (region.getFlag(DefaultFlag.POTION_SPLASH) == StateFlag.State.ALLOW) {
						region.setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.HAPPY_VILLAGER);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statusoff));
						return;
					} else {
						region.setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.ALLOW);

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.FLAME);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statuson));
						return;
					}
					//////////////////////////////////////////////////////////////////////////////
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
