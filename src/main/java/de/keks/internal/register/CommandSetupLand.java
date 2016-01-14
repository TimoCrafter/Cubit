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
import de.keks.internal.command.land.CMD_AddAll_Member;
import de.keks.internal.command.land.CMD_Biome;
import de.keks.internal.command.land.CMD_Biome_Set;
import de.keks.internal.command.land.CMD_CleanAll_Member;
import de.keks.internal.command.land.CMD_Kick;
import de.keks.internal.command.land.CMD_Land_Add_Member;
import de.keks.internal.command.land.CMD_Land_Buy;
import de.keks.internal.command.land.CMD_Land_Buy_Up;
import de.keks.internal.command.land.CMD_Land_Give;
import de.keks.internal.command.land.CMD_Land_Help;
import de.keks.internal.command.land.CMD_Land_Info;
import de.keks.internal.command.land.CMD_Land_List;
import de.keks.internal.command.land.CMD_Land_Offer;
import de.keks.internal.command.land.CMD_Land_Remove_Member;
import de.keks.internal.command.land.CMD_Land_Sell;
import de.keks.internal.command.land.CMD_Land_Take_Offer;
import de.keks.internal.command.land.CMD_RemoveAll_Member;
import de.keks.internal.command.land.CMD_Version;
import de.keks.internal.command.land.addons.CMD_FIRE;
import de.keks.internal.command.land.addons.CMD_LOCK;
import de.keks.internal.command.land.addons.CMD_MOBS;
import de.keks.internal.command.land.addons.CMD_POTION;
import de.keks.internal.command.land.addons.CMD_PVP;
import de.keks.internal.command.land.addons.CMD_TNT;
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
		if (CubitCore.isPluginDisabled(sender)) {
			sender.sendMessage(I18n.translate("messages.pluginDisabled"));
			return true;
		}
		if (CubitPlugin.inst().cubitLandTask.containsKey(sender.getName())) {
			long secondsLeft = ((CubitPlugin.inst().cubitLandTask.get(sender.getName()) / 1000)
					+ CubitPlugin.inst().cubitTaskTime) - (System.currentTimeMillis() / 1000);

			if (secondsLeft > 0) {
				sender.sendMessage(I18n.translate("messages.noSpam"));
				CubitPlugin.inst().cubitLandTask.put(sender.getName(), System.currentTimeMillis());
				return true;
			}
		}
		CubitPlugin.inst().cubitLandTask.put(sender.getName(), System.currentTimeMillis());
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

	private TreeMap<String, CubitCore> commands = Maps.newTreeMap();

	public TreeMap<String, CubitCore> getCommands() {
		return commands;
	}

	private boolean initialized = false;

	public void initialize() {
		try {
			CMD_Land_Buy buyCommand = new CMD_Land_Buy(this);
			this.commands.put("buy", buyCommand);
			this.commands.put("kaufen", buyCommand);
			CMD_Land_Sell sellCommand = new CMD_Land_Sell(this);
			this.commands.put("sell", sellCommand);
			this.commands.put("verkaufen", sellCommand);
			CMD_Land_Offer offerCommand = new CMD_Land_Offer(this);
			this.commands.put("offer", offerCommand);
			this.commands.put("anbieten", offerCommand);
			CMD_Land_Take_Offer takeOfferCommand = new CMD_Land_Take_Offer(this);
			this.commands.put("takeoffer", takeOfferCommand);
			this.commands.put("abkaufen", takeOfferCommand);
			CMD_Land_Give giveCommand = new CMD_Land_Give(this);
			this.commands.put("give", giveCommand);
			this.commands.put("schenken", giveCommand);
			CMD_Land_Buy_Up buyUpCommand = new CMD_Land_Buy_Up(this);
			this.commands.put("buyup", buyUpCommand);
			this.commands.put("aufkaufen", buyUpCommand);
			this.commands.put("kick", new CMD_Kick(this));
			this.commands.put("info", new CMD_Land_Info(this));
			this.commands.put("biome", new CMD_Biome(this));
			this.commands.put("setbiome", new CMD_Biome_Set(this, null));
			this.commands.put("add", new CMD_Land_Add_Member(this));
			this.commands.put("remove", new CMD_Land_Remove_Member(this));
			this.commands.put("help", new CMD_Land_Help(this));
			this.commands.put("version", new CMD_Version(this));
			this.commands.put("list", new CMD_Land_List(this));
			this.commands.put("addall", new CMD_AddAll_Member(this));
			this.commands.put("removeall", new CMD_RemoveAll_Member(this));
			this.commands.put("cleanall", new CMD_CleanAll_Member(this));
			// Landeinstellungen. Später in Konfig anmerken!
			this.commands.put("tnt", new CMD_TNT(this));
			this.commands.put("mobs", new CMD_MOBS(this));
			this.commands.put("potion", new CMD_POTION(this));
			this.commands.put("pvp", new CMD_PVP(this));
			this.commands.put("lock", new CMD_LOCK(this));
			this.commands.put("fire", new CMD_FIRE(this));
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
