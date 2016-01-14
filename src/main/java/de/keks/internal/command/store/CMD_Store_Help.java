package de.keks.internal.command.store;

import org.bukkit.command.CommandSender;

import de.keks.internal.I18n;
import de.keks.internal.register.CommandSetupStore;
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

public class CMD_Store_Help extends CubitCore {

	public CMD_Store_Help(CommandSetupStore handler) {
		super(true);
		this.setupStore = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, String[] args) {
		if (sender.hasPermission("cubit.lstore.help")) {

			if (args.length < 2) {
			} else if (args[1].toString().equalsIgnoreCase("2")) {
				sender.sendMessage(I18n.translate("lstoreHelpPage2.help1"));
				sender.sendMessage(I18n.translate("lstoreHelpPage2.help2"));
				sender.sendMessage(I18n.translate("lstoreHelpPage2.help3"));
				sender.sendMessage(I18n.translate("lstoreHelpPage2.help4"));
				sender.sendMessage(I18n.translate("lstoreHelpPage2.help5"));
				return true;
			}

			sender.sendMessage(I18n.translate("lstoreHelpPage1.help1"));
			sender.sendMessage(I18n.translate("lstoreHelpPage1.help2"));
			sender.sendMessage(I18n.translate("lstoreHelpPage1.help3"));
			sender.sendMessage(I18n.translate("lstoreHelpPage1.help4"));
			sender.sendMessage(I18n.translate("lstoreHelpPage1.help5"));

		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
