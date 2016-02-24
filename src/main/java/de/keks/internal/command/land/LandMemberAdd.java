package de.keks.internal.command.land;

import static de.keks.internal.I18n.translate;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
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

public class LandMemberAdd extends MainCore {

	public LandMemberAdd(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.land.addmember")) {

			final Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);

			setupLand.executorServiceCommands.submit(new Runnable() {
				public void run() {
					if (args.length < 2) {
						sender.sendMessage(translate("messages.notEnoughArguments"));
						return;
					}

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
					@SuppressWarnings("deprecation")
					OfflinePlayer oplayer = Bukkit.getOfflinePlayer(args[1]);

					if (oplayer == null) {
						sender.sendMessage("gibts nit");
						return;
					}
					LocalPlayer olocalplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
							.getWorldGuardPlugin().wrapOfflinePlayer(oplayer);

					if (region.isOwner(olocalplayer)) {
						player.sendMessage(translate("messages.memberAddOwnRegion"));
						return;
					}
					if (region.isMember(olocalplayer)) {
						player.sendMessage(translate("messages.memberAddAlready", args[1], regionName));
						return;
					}
					region.getMembers().addPlayer(olocalplayer);

					ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(), Effect.HEART);

					player.sendMessage(translate("messages.memberAdd", args[1], regionName));
					setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}

		return true;
	}

}
