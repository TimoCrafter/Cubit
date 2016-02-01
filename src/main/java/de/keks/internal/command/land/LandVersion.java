package de.keks.internal.command.land;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.keks.iLand.ILandPlugin;
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

public class LandVersion extends MainCore {

	public LandVersion(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, String[] args) {
		if (sender.hasPermission("iLand.land.version")) {
			sender.sendMessage(ChatColor.YELLOW + "§n§6-==================[§2§liLand§r§6]=================-");
			sender.sendMessage(ChatColor.DARK_GREEN + "iLand Version: " + ChatColor.LIGHT_PURPLE
					+ ILandPlugin.inst().pdf.getVersion().toString());
			sender.sendMessage(ChatColor.DARK_GREEN + "By Kekshaus");
			sender.sendMessage(ChatColor.DARK_GREEN + "https://forum.hyve-software.com");

		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
