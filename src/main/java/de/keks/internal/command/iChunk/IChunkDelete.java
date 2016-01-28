package de.keks.internal.command.iChunk;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.keks.internal.I18n;
import de.keks.internal.register.CommandSetupIChunk;
import de.keks.internal.register.MainCore;
import thirdparty.ftp.it.sauronsoftware.ftp4j.ILandFTP;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class IChunkDelete extends MainCore {
	public IChunkDelete(CommandSetupIChunk handler) {
		super(true);
		this.setupIChunk = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("iLand.iChunk.delete")) {

			setupIChunk.executorServiceCommands.submit(new Runnable() {
				public void run() {
					final Player player = (Player) sender;

					String regionName = args[1];
					ILandFTP.delete(regionName, player.getUniqueId().toString());
					sender.sendMessage(I18n.translate("messages.storeDelete", regionName, player.getName()));

				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
