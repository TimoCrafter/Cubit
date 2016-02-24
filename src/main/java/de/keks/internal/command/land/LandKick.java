package de.keks.internal.command.land;

import static de.keks.internal.I18n.translate;

import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
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
 * <li>Autor: Shadow, Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class LandKick extends MainCore {

	public LandKick(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (!sender.hasPermission("cubit.land.kick")) {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
			return false;
		}

		final Player player = (Player) sender;
		final int chunkX = player.getLocation().getChunk().getX();
		final int chunkZ = player.getLocation().getChunk().getZ();
		final World world = player.getWorld();
		final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin()
				.wrapPlayer(player);

		setupLand.executorServiceCommands.submit(new Runnable() {
			public void run() {
				String regionName = getRegionName(chunkX, chunkZ, world);

				if (!ProtectedRegion.isValidId(regionName)) {
					player.sendMessage(translate("messages.noRegionHere"));
					return;
				}

				final ProtectedRegion region = getRegion(world, regionName);
				if (region == null) {
					player.sendMessage(translate("messages.noRegionHere"));
					return;
				}

				if (!region.isOwner(localplayer)) {
					player.sendMessage(translate("messages.noPermissionForRegion"));
					return;
				}

				ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
						Effect.COLOURED_DUST);

				player.sendMessage(translate("messages.kickNoMembersInfo"));

				final Chunk c = player.getLocation().getChunk();
				final ArrayList<Player> playersToKick = new ArrayList<Player>();
				for (Entity e : c.getEntities()) {
					if (e instanceof Player) {

						Player p = (Player) e;
						if (!region.getOwners().getUniqueIds().contains(p.getUniqueId())
								&& !region.getMembers().getUniqueIds().contains(p.getUniqueId())) {
							// p.sendMessage(translate("messages.kickInfo"));
							if (!p.hasPermission("cubit.land.kickbypass")) {
								playersToKick.add(p);
							}
						}
					}
				}
				CubitPlugin.inst().getServer().getScheduler().scheduleSyncDelayedTask(CubitPlugin.inst(),
						new Runnable() {

					@Override
					public void run() {
						for (Player p : playersToKick) {
							if (getRegion(world, getRegionName(p.getLocation().getChunk().getX(),
									p.getLocation().getChunk().getZ(), world)).getOwners()
											.contains(player.getUniqueId())) {
								p.sendMessage(translate("messages.kickedInfo"));
								p.teleport(p.getWorld().getSpawnLocation());
								player.sendMessage(translate("messages.kickedInfoOwner"));
							}
						}
					}

				}, 40);
			}
		});

		return true;
	}

}
