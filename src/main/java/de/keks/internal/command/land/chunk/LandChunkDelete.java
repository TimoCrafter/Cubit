package de.keks.internal.command.land.chunk;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.keks.internal.I18n;
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

public class LandChunkDelete extends MainCore {
	public LandChunkDelete(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.landEdit.delete")) {

			setupLand.executorServiceCommands.submit(new Runnable() {
				public void run() {
					final Player player = (Player) sender;

					String regionName = args[1];
					sender.sendMessage(I18n.translate("messages.storeDelete", regionName, player.getName()));

				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
