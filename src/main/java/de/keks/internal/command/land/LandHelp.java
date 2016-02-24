package de.keks.internal.command.land;

import org.bukkit.command.CommandSender;

import de.keks.internal.I18n;
import de.keks.internal.command.config.ConfigValues;
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

public class LandHelp extends MainCore {

	public LandHelp(CommandSetupLand handler) {
		super(false);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(CommandSender sender, String[] args) {
		if (sender.hasPermission("cubit.land.help")) {

			if (args.length < 2) {
			} else if (args[1].toString().equalsIgnoreCase("2")) {
				sender.sendMessage(I18n.translate("landHelpPage2.help1"));
				sender.sendMessage(I18n.translate("landHelpPage2.help2"));
				sender.sendMessage(I18n.translate("landHelpPage2.help3"));
				sender.sendMessage(I18n.translate("landHelpPage2.help4"));
				sender.sendMessage(I18n.translate("landHelpPage2.help5"));
				sender.sendMessage(I18n.translate("landHelpPage2.help6"));
				sender.sendMessage(I18n.translate("landHelpPage2.help7"));
				sender.sendMessage(I18n.translate("landHelpPage2.help8"));
				sender.sendMessage(I18n.translate("landHelpPage2.help9"));
				return true;
			} else if (args[1].toString().equalsIgnoreCase("3")) {
				sender.sendMessage(I18n.translate("landHelpPage3.help1"));
				sender.sendMessage(I18n.translate("landHelpPage3.help2"));
				sender.sendMessage(I18n.translate("landHelpPage3.help3"));
				sender.sendMessage(I18n.translate("landHelpPage3.help4"));
				sender.sendMessage(I18n.translate("landHelpPage3.help5"));
				sender.sendMessage(I18n.translate("landHelpPage3.help6"));
				sender.sendMessage(I18n.translate("landHelpPage3.help7"));
				sender.sendMessage(I18n.translate("landHelpPage3.help8"));
				return true;
			} else if (args[1].toString().equalsIgnoreCase("4")) {
				sender.sendMessage(I18n.translate("landHelpPage4.help1"));
				sender.sendMessage(I18n.translate("landHelpPage4.help2"));
				sender.sendMessage(I18n.translate("landHelpPage4.help3"));
				sender.sendMessage(I18n.translate("landHelpPage4.help4"));
				sender.sendMessage(I18n.translate("landHelpPage4.help5"));
				sender.sendMessage(I18n.translate("landHelpPage4.help6"));
				sender.sendMessage(I18n.translate("landHelpPage4.help7"));
				sender.sendMessage(I18n.translate("landHelpPage4.help8"));
				return true;
			} else if (args[1].toString().equalsIgnoreCase("5")) {
				double biomeprice = ConfigValues.setBiome;
				sender.sendMessage(I18n.translate("landHelpPage5.help1"));
				sender.sendMessage(I18n.translate("landHelpPage5.help2"));
				sender.sendMessage(I18n.translate("landHelpPage5.help3"));
				sender.sendMessage(I18n.translate("landHelpPage5.help4", biomeprice));
				sender.sendMessage(I18n.translate("landHelpPage5.help5"));
				return true;
			}

			sender.sendMessage(I18n.translate("landHelpPage1.help1"));
			sender.sendMessage(I18n.translate("landHelpPage1.help2"));
			sender.sendMessage(I18n.translate("landHelpPage1.help3"));
			sender.sendMessage(I18n.translate("landHelpPage1.help4"));
			sender.sendMessage(I18n.translate("landHelpPage1.help5"));
			sender.sendMessage(I18n.translate("landHelpPage1.help6"));
			sender.sendMessage(I18n.translate("landHelpPage1.help7"));
			sender.sendMessage(I18n.translate("landHelpPage1.help8"));
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}
}
