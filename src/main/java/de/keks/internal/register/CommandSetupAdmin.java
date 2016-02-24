package de.keks.internal.register;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.command.admin.LandAdminDelete;
import de.keks.internal.command.admin.LandAdminHelp;
import de.keks.internal.command.admin.LandAdminList;
import de.keks.internal.command.admin.LandAdminSetServerRegion;
import de.keks.internal.command.admin.LandAdminTake;
import de.keks.internal.command.admin.LandAdminTp;
import de.keks.internal.command.admin.LandAdminUnsetServerRegion;
import de.keks.internal.plugin.hooks.OfferManager;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CommandSetupAdmin implements CommandExecutor {
	public boolean isInitialized() {
		return initialized;
	}

	private HashMap<String, Long> commandTaskSpam = new HashMap<String, Long>();
	private CubitPlugin cubit;
	private OfferManager offerManager;

	public ThreadPoolExecutor executorServiceCommands;
	public ThreadPoolExecutor executorServiceRegions;

	public CommandSetupAdmin(final CubitPlugin cubit) {
		this.cubit = cubit;
		offerManager = new OfferManager(cubit);
		executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		executorServiceRegions = new ThreadPoolExecutor(1, 1, 120L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public CubitPlugin getCubitInstance() {
		return this.cubit;
	}

	public OfferManager getOfferManager() {
		return offerManager;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (MainCore.isPluginDisabled(sender)) {
			sender.sendMessage(I18n.translate("messages.pluginDisabled"));
			return true;
		}
		if (commandTaskSpam.containsKey(sender.getName())) {
			long secondsLeft = ((commandTaskSpam.get(sender.getName()) / 1000) + CubitPlugin.inst().cubitTaskTime)
					- (System.currentTimeMillis() / 1000);

			if (secondsLeft > 0) {
				sender.sendMessage(I18n.translate("messages.noSpam"));
				commandTaskSpam.put(sender.getName(), System.currentTimeMillis());
				return true;
			}
		}
		commandTaskSpam.put(sender.getName(), System.currentTimeMillis());
		if (args.length == 0) {

			if (sender.hasPermission("cubit.admin.help")) {

				sender.sendMessage(I18n.translate("ladminHelpPage1.help1"));
				sender.sendMessage(I18n.translate("ladminHelpPage1.help2"));
				sender.sendMessage(I18n.translate("ladminHelpPage1.help3"));
				sender.sendMessage(I18n.translate("ladminHelpPage1.help4"));
				sender.sendMessage(I18n.translate("ladminHelpPage1.help5"));
			} else {
				sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
			}
		} else if (getCommands().containsKey(args[0])) {
			String command = args[0];
			if (!getCommands().get(command).execute(sender, args)) {
				sender.sendMessage(I18n.translate("messages.unknownCommand", "/ladmin help"));
			}
		} else {
			sender.sendMessage(I18n.translate("messages.unknownCommand", "/ladmin help"));
		}
		return true;
	}

	private TreeMap<String, MainCore> commands = Maps.newTreeMap();

	public TreeMap<String, MainCore> getCommands() {
		return commands;
	}

	private boolean initialized = false;

	public void initialize() {
		try {
			this.commands.put("delete", new LandAdminDelete(this));
			this.commands.put("setserver", new LandAdminSetServerRegion(this));
			this.commands.put("unsetserver", new LandAdminUnsetServerRegion(this));
			this.commands.put("help", new LandAdminHelp(this));
			this.commands.put("take", new LandAdminTake(this));
			this.commands.put("list", new LandAdminList(this));
			this.commands.put("tp", new LandAdminTp(this));
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
