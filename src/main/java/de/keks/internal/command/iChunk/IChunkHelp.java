package de.keks.internal.command.iChunk;

import org.bukkit.command.CommandSender;

import de.keks.internal.I18n;
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

public class IChunkHelp extends CubitCore {

	public IChunkHelp(CommandSetupIChunk handler) {
		super(true);
		this.setupIChunk = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, String[] args) {
		if (sender.hasPermission("cubit.iChunk.help")) {

			if (args.length < 2) {
			} else if (args[1].toString().equalsIgnoreCase("2")) {
				sender.sendMessage(I18n.translate("iChunkHelpPage2.help1"));
				sender.sendMessage(I18n.translate("iChunkHelpPage2.help2"));
				sender.sendMessage(I18n.translate("iChunkHelpPage2.help3"));
				sender.sendMessage(I18n.translate("iChunkHelpPage2.help4"));
				sender.sendMessage(I18n.translate("iChunkHelpPage2.help5"));
				return true;
			}

			sender.sendMessage(I18n.translate("iChunkHelpPage1.help1"));
			sender.sendMessage(I18n.translate("iChunkHelpPage1.help2"));
			sender.sendMessage(I18n.translate("iChunkHelpPage1.help3"));
			sender.sendMessage(I18n.translate("iChunkHelpPage1.help4"));
			sender.sendMessage(I18n.translate("iChunkHelpPage1.help5"));

		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
