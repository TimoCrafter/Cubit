package de.keks.internal.register;

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
import de.keks.internal.command.admin.CMD_Admin_Delete;
import de.keks.internal.command.admin.CMD_Admin_Help;
import de.keks.internal.command.admin.CMD_Admin_List;
import de.keks.internal.command.admin.CMD_Admin_SetServer;
import de.keks.internal.command.admin.CMD_Admin_Take;
import de.keks.internal.command.admin.CMD_Admin_Tp;
import de.keks.internal.command.admin.CMD_Admin_UnsetServer;
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
		if (CubitCore.isPluginDisabled(sender)) {
			sender.sendMessage(I18n.translate("messages.pluginDisabled"));
			return true;
		}
		if (CubitPlugin.inst().cubitAdminTask.containsKey(sender.getName())) {
			long secondsLeft = ((CubitPlugin.inst().cubitAdminTask.get(sender.getName()) / 1000)
					+ CubitPlugin.inst().cubitTaskTime) - (System.currentTimeMillis() / 1000);

			if (secondsLeft > 0) {
				sender.sendMessage(I18n.translate("messages.noSpam"));
				CubitPlugin.inst().cubitAdminTask.put(sender.getName(), System.currentTimeMillis());
				return true;
			}
		}
		CubitPlugin.inst().cubitAdminTask.put(sender.getName(), System.currentTimeMillis());
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

	private TreeMap<String, CubitCore> commands = Maps.newTreeMap();

	public TreeMap<String, CubitCore> getCommands() {
		return commands;
	}

	private boolean initialized = false;

	public void initialize() {
		try {
			this.commands.put("delete", new CMD_Admin_Delete(this));
			this.commands.put("setserver", new CMD_Admin_SetServer(this));
			this.commands.put("unsetserver", new CMD_Admin_UnsetServer(this));
			this.commands.put("help", new CMD_Admin_Help(this));
			this.commands.put("take", new CMD_Admin_Take(this));
			this.commands.put("list", new CMD_Admin_List(this));
			this.commands.put("tp", new CMD_Admin_Tp(this));
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
