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
import de.keks.internal.command.land.LandMemberAddAll;
import de.keks.internal.command.land.LandMemberClearAll;
import de.keks.internal.command.land.LandKick;
import de.keks.internal.command.land.LandMemberAdd;
import de.keks.internal.command.land.LandBuy;
import de.keks.internal.command.land.LandBuyup;
import de.keks.internal.command.land.LandGive;
import de.keks.internal.command.land.LandHelp;
import de.keks.internal.command.land.LandInfo;
import de.keks.internal.command.land.LandList;
import de.keks.internal.command.land.LandOffer;
import de.keks.internal.command.land.LandMemberRemove;
import de.keks.internal.command.land.LandSell;
import de.keks.internal.command.land.LandTakeOffer;
import de.keks.internal.command.land.LandMemberRemoveAll;
import de.keks.internal.command.land.LandVersion;
import de.keks.internal.command.land.addons.LandAddonFire;
import de.keks.internal.command.land.addons.LandAddonLock;
import de.keks.internal.command.land.addons.LandAddonMobs;
import de.keks.internal.command.land.addons.LandAddonPotion;
import de.keks.internal.command.land.addons.LandAddonPvP;
import de.keks.internal.command.land.addons.LandAddonTnt;
import de.keks.internal.command.land.chunk.LandChunkBiomes;
import de.keks.internal.command.land.chunk.LandChunkDelete;
import de.keks.internal.command.land.chunk.LandChunkPaste;
import de.keks.internal.command.land.chunk.LandChunkRegen;
import de.keks.internal.command.land.chunk.LandChunkSave;
import de.keks.internal.command.land.chunk.LandChunkSaves;
import de.keks.internal.command.land.chunk.LandChunkSetbiome;
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

public class CommandSetupLand implements CommandExecutor {
	public boolean isInitialized() {
		return initialized;
	}

	private HashMap<String, Long> commandTaskSpam = new HashMap<String, Long>();

	private OfferManager offerManager;
	private CubitPlugin cubit;
	public ThreadPoolExecutor executorServiceCommands;
	public ThreadPoolExecutor executorServiceRegions;

	public CommandSetupLand(final CubitPlugin cubit) {
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

			if (sender.hasPermission("cubit.land.help")) {

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
		} else if (getCommands().containsKey(args[0])) {
			String command = args[0];
			if (!getCommands().get(command).execute(sender, args)) {
				sender.sendMessage(I18n.translate("messages.unknownCommand", "/land help"));
			}
		} else {
			sender.sendMessage(I18n.translate("messages.unknownCommand", "/land help"));
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
			LandBuy buyCommand = new LandBuy(this);
			this.commands.put("buy", buyCommand);
			this.commands.put("kaufen", buyCommand);
			LandSell sellCommand = new LandSell(this);
			this.commands.put("sell", sellCommand);
			this.commands.put("verkaufen", sellCommand);
			LandOffer offerCommand = new LandOffer(this);
			this.commands.put("offer", offerCommand);
			this.commands.put("anbieten", offerCommand);
			LandTakeOffer takeOfferCommand = new LandTakeOffer(this);
			this.commands.put("takeoffer", takeOfferCommand);
			this.commands.put("abkaufen", takeOfferCommand);
			LandGive giveCommand = new LandGive(this);
			this.commands.put("give", giveCommand);
			this.commands.put("schenken", giveCommand);
			LandBuyup buyUpCommand = new LandBuyup(this);
			this.commands.put("buyup", buyUpCommand);
			this.commands.put("aufkaufen", buyUpCommand);
			this.commands.put("kick", new LandKick(this));
			this.commands.put("info", new LandInfo(this));
			this.commands.put("add", new LandMemberAdd(this));
			this.commands.put("remove", new LandMemberRemove(this));
			this.commands.put("help", new LandHelp(this));
			this.commands.put("version", new LandVersion(this));
			this.commands.put("list", new LandList(this));
			this.commands.put("addall", new LandMemberAddAll(this));
			this.commands.put("removeall", new LandMemberRemoveAll(this));
			this.commands.put("cleanall", new LandMemberClearAll(this));
			// Land Options
			this.commands.put("tnt", new LandAddonTnt(this));
			this.commands.put("mobs", new LandAddonMobs(this));
			this.commands.put("potion", new LandAddonPotion(this));
			this.commands.put("pvp", new LandAddonPvP(this));
			this.commands.put("lock", new LandAddonLock(this));
			this.commands.put("fire", new LandAddonFire(this));
			// Chunk Tasks
			this.commands.put("testbiomes", new LandChunkBiomes(this));
			this.commands.put("testsetbiome", new LandChunkSetbiome(this, null));
			this.commands.put("testsave", new LandChunkSave(this));
			this.commands.put("testpaste", new LandChunkPaste(this));
			this.commands.put("testregen", new LandChunkRegen(this));
			this.commands.put("testsaves", new LandChunkSaves(this));
			this.commands.put("testdelete", new LandChunkDelete(this));
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
