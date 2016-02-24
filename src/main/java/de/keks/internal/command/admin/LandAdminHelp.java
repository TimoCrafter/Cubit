package de.keks.internal.command.admin;

import org.bukkit.command.CommandSender;

import de.keks.internal.I18n;
import de.keks.internal.register.CommandSetupAdmin;
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

public class LandAdminHelp extends MainCore {

	public LandAdminHelp(CommandSetupAdmin handler) {
		super(true);
		this.setupAdmin = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, String[] args) {
		if (sender.hasPermission("cubit.admin.help")) {

			if (args.length < 2) {
			} else if (args[1].toString().equalsIgnoreCase("2")) {
				sender.sendMessage(I18n.translate("ladminHelpPage2.help1"));
				sender.sendMessage(I18n.translate("ladminHelpPage2.help2"));
				sender.sendMessage(I18n.translate("ladminHelpPage2.help3"));
				sender.sendMessage(I18n.translate("ladminHelpPage2.help4"));
				sender.sendMessage(I18n.translate("ladminHelpPage2.help5"));
				sender.sendMessage(I18n.translate("ladminHelpPage2.help6"));
				sender.sendMessage(I18n.translate("ladminHelpPage2.help7"));
				sender.sendMessage(I18n.translate("ladminHelpPage2.help8"));
				return true;
			}

			sender.sendMessage(I18n.translate("ladminHelpPage1.help1"));
			sender.sendMessage(I18n.translate("ladminHelpPage1.help2"));
			sender.sendMessage(I18n.translate("ladminHelpPage1.help3"));
			sender.sendMessage(I18n.translate("ladminHelpPage1.help4"));
			sender.sendMessage(I18n.translate("ladminHelpPage1.help5"));

		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
