package de.keks.internal.command.store;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.keks.internal.I18n;
import de.keks.internal.register.CommandSetupStore;
import de.keks.internal.register.CubitCore;
import thirdparty.ftp.it.sauronsoftware.ftp4j.CubitFTP;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CMD_Store_Delete extends CubitCore {
	public CMD_Store_Delete(CommandSetupStore handler) {
		super(true);
		this.setupStore = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.lstore.delete")) {

			setupStore.executorServiceCommands.submit(new Runnable() {
				public void run() {
					final Player player = (Player) sender;

					String regionName = args[1];
					CubitFTP.delete(regionName, player.getUniqueId().toString());
					sender.sendMessage(I18n.translate("messages.storeDelete", regionName, player.getName()));

				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
