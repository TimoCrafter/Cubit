package de.keks.internal.command.land.chunk;

import static de.keks.internal.I18n.translate;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.keks.internal.I18n;
import de.keks.internal.core.database.DatabaseFacade;
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

public class LandChunkSaves extends MainCore {

	public LandChunkSaves(CommandSetupLand handler) {

		super(true);
		this.setupLand = handler;
	}

	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.landEdit.list")) {
			setupLand.executorServiceCommands.submit(new Runnable() {
				public void run() {
					if (args.length <= 2) {
						int pageNumb = 0;
						Player p = (Player) sender;
						try {
							if (args.length == 2) {
								int number = Integer.valueOf(args[1]);
								if (number < 1) {
									pageNumb = 0;
								} else {
									pageNumb = Integer.valueOf(args[1]) - 1;
								}

							} else {
								pageNumb = 0;
							}
						} catch (Exception e) {
							p.sendMessage(translate("messages.notANumber", args[1]));
							return;
						}
						List<String> list = DatabaseFacade.getSavelist(p);
						int rgCount = list.size();
						List<String> toplist = list.subList(pageNumb * 10,
								pageNumb * 10 + 10 > rgCount ? rgCount : pageNumb * 10 + 10);
						sender.sendMessage(
								translate("messages.storePage", rgCount, (pageNumb * 10 + 1), (pageNumb * 10 + 10)));
						int counter = pageNumb * 10 + 1;

						for (String name : toplist) {

							sender.sendMessage("§a" + counter + ". §6" + name);
							counter++;
						}
					}
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
