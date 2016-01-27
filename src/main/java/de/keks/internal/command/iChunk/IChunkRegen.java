package de.keks.internal.command.iChunk;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.core.cApi.KChunk.CChunk;
import de.keks.internal.register.CommandSetupIChunk;
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

public class IChunkRegen extends CubitCore {
	public IChunkRegen(CommandSetupIChunk handler) {
		super(true);
		this.setupIChunk = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.iChunk.regen")) {

			setupIChunk.executorServiceCommands.submit(new Runnable() {
				public void run() {
					final Player player = (Player) sender;

					Bukkit.getScheduler().scheduleSyncDelayedTask(CubitPlugin.inst(), new Runnable() {
						public void run() {
							if (CChunk.regenerateRegion(player)) {
								sender.sendMessage(I18n.translate("messages.storeRegen"));
							}
						}
					});
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
