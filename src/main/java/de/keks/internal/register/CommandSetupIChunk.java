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
import de.keks.internal.command.iChunk.IChunkDelete;
import de.keks.internal.command.iChunk.IChunkHelp;
import de.keks.internal.command.iChunk.IChunkList;
import de.keks.internal.command.iChunk.IChunkPaste;
import de.keks.internal.command.iChunk.IChunkRegen;
import de.keks.internal.command.iChunk.IChunkSave;
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

public class CommandSetupIChunk implements CommandExecutor {
	public boolean isInitialized() {
		return initialized;
	}

	private CubitPlugin cubit;
	private OfferManager offerManager;

	public ThreadPoolExecutor executorServiceCommands;
	public ThreadPoolExecutor executorServiceRegions;

	public CommandSetupIChunk(final CubitPlugin cubit) {
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
		if (CubitPlugin.inst().cubitIChunkTask.containsKey(sender.getName())) {
			long secondsLeft = ((CubitPlugin.inst().cubitIChunkTask.get(sender.getName()) / 1000)
					+ CubitPlugin.inst().cubitTaskTime) - (System.currentTimeMillis() / 1000);

			if (secondsLeft > 0) {
				sender.sendMessage(I18n.translate("messages.noSpam"));
				CubitPlugin.inst().cubitAdminTask.put(sender.getName(), System.currentTimeMillis());
				return true;
			}
		}
		CubitPlugin.inst().cubitIChunkTask.put(sender.getName(), System.currentTimeMillis());
		if (args.length == 0) {

			if (sender.hasPermission("cubit.iChunk.help")) {

				sender.sendMessage(I18n.translate("iChunkHelpPage1.help1"));
				sender.sendMessage(I18n.translate("iChunkHelpPage1.help2"));
				sender.sendMessage(I18n.translate("iChunkHelpPage1.help3"));
				sender.sendMessage(I18n.translate("iChunkHelpPage1.help4"));
				sender.sendMessage(I18n.translate("iChunkHelpPage1.help5"));
			} else {
				sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
			}
		} else if (getCommands().containsKey(args[0])) {
			String command = args[0];
			if (!getCommands().get(command).execute(sender, args)) {
				sender.sendMessage(I18n.translate("messages.unknownCommand", "/iChunk help"));
			}
		} else {
			sender.sendMessage(I18n.translate("messages.unknownCommand", "/iChunk help"));
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
			this.commands.put("save", new IChunkSave(this));
			this.commands.put("paste", new IChunkPaste(this));
			this.commands.put("regen", new IChunkRegen(this));
			this.commands.put("delete", new IChunkDelete(this));
			this.commands.put("list", new IChunkList(this));
			this.commands.put("help", new IChunkHelp(this));
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
