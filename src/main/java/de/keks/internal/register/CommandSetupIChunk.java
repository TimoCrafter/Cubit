package de.keks.internal.register;

import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.google.common.collect.Maps;

import de.keks.iLand.ILandPlugin;
import de.keks.internal.I18n;
import de.keks.internal.command.tChunk.TChunkDelete;
import de.keks.internal.command.tChunk.TChunkHelp;
import de.keks.internal.command.tChunk.TChunkList;
import de.keks.internal.command.tChunk.TChunkPaste;
import de.keks.internal.command.tChunk.TChunkRegen;
import de.keks.internal.command.tChunk.TChunkSave;
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

	private ILandPlugin iLand;
	private OfferManager offerManager;

	public ThreadPoolExecutor executorServiceCommands;
	public ThreadPoolExecutor executorServiceRegions;

	public CommandSetupIChunk(final ILandPlugin iLand) {
		this.iLand = iLand;
		offerManager = new OfferManager(iLand);
		executorServiceCommands = new ThreadPoolExecutor(1, 1, 250L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		executorServiceRegions = new ThreadPoolExecutor(1, 1, 120L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
	}

	public ILandPlugin getILandInstance() {
		return this.iLand;
	}

	public OfferManager getOfferManager() {
		return offerManager;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (MainCore.isPluginDisabled(sender)) {
			sender.sendMessage(I18n.translate("messages.pluginDisabled"));
			return true;
		}
		if (ILandPlugin.inst().iLandIChunkTask.containsKey(sender.getName())) {
			long secondsLeft = ((ILandPlugin.inst().iLandIChunkTask.get(sender.getName()) / 1000)
					+ ILandPlugin.inst().iLandTaskTime) - (System.currentTimeMillis() / 1000);

			if (secondsLeft > 0) {
				sender.sendMessage(I18n.translate("messages.noSpam"));
				ILandPlugin.inst().iLandAdminTask.put(sender.getName(), System.currentTimeMillis());
				return true;
			}
		}
		ILandPlugin.inst().iLandIChunkTask.put(sender.getName(), System.currentTimeMillis());
		if (args.length == 0) {

			if (sender.hasPermission("iLand.iChunk.help")) {

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

	private TreeMap<String, MainCore> commands = Maps.newTreeMap();

	public TreeMap<String, MainCore> getCommands() {
		return commands;
	}

	private boolean initialized = false;

	public void initialize() {
		try {
			this.commands.put("save", new TChunkSave(this));
			this.commands.put("paste", new TChunkPaste(this));
			this.commands.put("regen", new TChunkRegen(this));
			this.commands.put("delete", new TChunkDelete(this));
			this.commands.put("list", new TChunkList(this));
			this.commands.put("help", new TChunkHelp(this));
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
