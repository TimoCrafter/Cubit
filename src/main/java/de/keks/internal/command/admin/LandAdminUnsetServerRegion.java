package de.keks.internal.command.admin;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.managers.RegionManager;

import de.keks.internal.I18n;
import de.keks.internal.core.cApi.ChunkApi;
import de.keks.internal.core.tasks.RegionSaveTask;
import de.keks.internal.register.CommandSetupAdmin;
import de.keks.internal.register.MainCore;

public class LandAdminUnsetServerRegion extends MainCore {

	/**
	 * Copyright:
	 * <ul>
	 * <li>Autor: Kekshaus</li>
	 * <li>2016</li>
	 * <li>www.minegaming.de</li>
	 * </ul>
	 * 
	 */

	public LandAdminUnsetServerRegion(CommandSetupAdmin handler) {
		super(true);
		this.setupAdmin = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.admin.unsetserverregion")) {

			final Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			setupAdmin.executorServiceCommands.submit(new Runnable() {
				@Override
				public void run() {
					RegionManager manager = getWorldGuard().getRegionManager(world);
					String serverRegionName = getServerRegionName(chunkX, chunkZ, world);

					if (manager.hasRegion(serverRegionName)) {
						manager.removeRegion(serverRegionName);

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.LARGE_SMOKE);

						sender.sendMessage(I18n.translate("messages.unsetServerregion", serverRegionName));

					} else {
						sender.sendMessage(I18n.translate("messages.isNotServerregion"));
					}
					setupAdmin.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}
}
