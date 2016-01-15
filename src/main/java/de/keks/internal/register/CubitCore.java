package de.keks.internal.register;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.core.cubli.InternalHighlight;
import de.keks.internal.core.database.DataController;
import de.keks.internal.plugin.hooks.classes.EconomyHook;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public abstract class CubitCore {

	protected CommandSetupLand setupLand;
	protected CommandSetupStore setupStore;
	protected CommandSetupAdmin setupAdmin;
	public InternalHighlight effects;

	public CubitCore(boolean isOnlyPlayerCommand) {

	}

	public abstract boolean execute(CommandSender sender, String[] args);

	public void playEffect(Player p, Effect e, int i) {
		InternalHighlight.startChunkEffect(p, p.getLocation(), p.getLocation().getChunk(), e);
	}

	protected String getRegionName(int x, int z, World world) {
		return world.getName().toLowerCase() + "_" + x + "_" + z;
	}

	protected String getServerRegionName(int x, int z, World world) {
		return "server_" + x + "_" + z;
	}

	protected boolean isServerRegion(int x, int z, World world) {
		if (getRegion(world, "server_" + x + "_" + z) != null) {
			return true;
		}
		return false;
	}

	protected ProtectedRegion getRegion(World world, String regionName) {
		return getWorldGuard().getRegionManager(world).getRegion(regionName);
	}

	protected void scheduleSyncTask(CommandSetupLand handler, Runnable run) {
		handler.getCubitInstance().getServer().getScheduler().scheduleSyncDelayedTask(handler.getCubitInstance(), run);
	}

	protected void scheduleSyncTask(CommandSetupLand handler, Runnable run, long delay) {
		handler.getCubitInstance().getServer().getScheduler().scheduleSyncDelayedTask(handler.getCubitInstance(), run,
				delay);
	}

	protected void scheduleSyncTaskAdmin(CommandSetupAdmin handler, Runnable run) {
		handler.getCubitInstance().getServer().getScheduler().scheduleSyncDelayedTask(handler.getCubitInstance(), run);
	}

	protected void scheduleSyncTaskAdmin(CommandSetupAdmin handler, Runnable run, long delay) {
		handler.getCubitInstance().getServer().getScheduler().scheduleSyncDelayedTask(handler.getCubitInstance(), run,
				delay);
	}

	protected void scheduleSyncTaskStore(CommandSetupStore handler, Runnable run) {
		handler.getCubitInstance().getServer().getScheduler().scheduleSyncDelayedTask(handler.getCubitInstance(), run);
	}

	protected void scheduleSyncTaskStore(CommandSetupStore handler, Runnable run, long delay) {
		handler.getCubitInstance().getServer().getScheduler().scheduleSyncDelayedTask(handler.getCubitInstance(), run,
				delay);
	}

	protected WorldGuardPlugin getWorldGuard() {
		try {
			return CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected WorldEditPlugin getWorldEdit() {
		try {
			return CubitPlugin.inst().getHookManager().getWorldEditManager().getWorldEditPlugin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected double calculateCosts(Player player, World world, boolean buy) {
		double first_region_costs = ConfigValues.firstRegionCosts;
		double tax_increase = ConfigValues.taxIncreasePerRegion;
		double max_region_costs = ConfigValues.maxTaxAmount;
		double percentage_at_sell = ConfigValues.percentageAtRegionSell;
		WorldGuardPlugin worldGuardPlugin = getWorldGuard();
		int regions = worldGuardPlugin.getRegionManager(world)
				.getRegionCountOfPlayer(worldGuardPlugin.wrapPlayer(player));
		double costs = first_region_costs + tax_increase * (regions);

		costs = costs >= max_region_costs ? max_region_costs : costs;

		if (!buy) {
			return costs * percentage_at_sell;
		} else {
			return costs;
		}
	}

	protected void moneyTransfer(Player from, OfflinePlayer to, double amount) {
		if (from == null && to == null) {
			return;
		}

		EconomyHook economyManager = CubitPlugin.inst().getHookManager().getEconomyManager();
		if (from == null) {
			economyManager.giveMoney(to, amount);
		} else if (to == null) {
			economyManager.giveMoney(from, -amount);
		} else {
			economyManager.giveMoney(from, -amount);
			economyManager.giveMoney(to, amount);
		}
	}

	protected long lastSeen(UUID uuid) {
		return DataController.getLastLogin(uuid);
	}

	protected boolean wasPlayerTooLongOff(ProtectedRegion region, Player questionerName) {
		LocalPlayer olocalplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin()
				.wrapOfflinePlayer(questionerName);

		long timeNow = System.currentTimeMillis();
		for (UUID splayer : region.getOwners().getUniqueIds()) {
			OfflinePlayer oplayer = Bukkit.getOfflinePlayer(splayer);
			LocalPlayer lplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin()
					.wrapOfflinePlayer(oplayer);
			if (region.isMember(olocalplayer) || region.isMember(olocalplayer)) {
				if (timeNow - lastSeen(lplayer.getUniqueId()) < ConfigValues.buyupMembers) {
					return false;
				}
			} else {
				if (timeNow - lastSeen(lplayer.getUniqueId()) < ConfigValues.buyupNoMembers) {
					return false;
				}
			}
		}
		return true;
	}

	protected boolean timeForBuyupInfo(ProtectedRegion region, Player questionerName) {
		LocalPlayer olocalplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin()
				.wrapOfflinePlayer(questionerName);

		long timeNow = System.currentTimeMillis();
		for (UUID splayer : region.getOwners().getUniqueIds()) {
			OfflinePlayer oplayer = Bukkit.getOfflinePlayer(splayer);
			LocalPlayer lplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin()
					.wrapOfflinePlayer(oplayer);
			if (region.isMember(olocalplayer) || region.isMember(olocalplayer)) {
				if (timeNow - lastSeen(lplayer.getUniqueId()) < ConfigValues.buyupMembers
						- ConfigValues.buyupInfo * 24 * 60 * 60 * 1000) {
					return false;
				}
			} else {
				if (timeNow - lastSeen(lplayer.getUniqueId()) < ConfigValues.buyupNoMembers
						- ConfigValues.buyupInfo * 24 * 60 * 60 * 1000) {
					return false;
				}
			}
		}
		return true;
	}

	protected long getBuyupInfoDate(ProtectedRegion region, Player questionerName) {
		LocalPlayer olocalplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin()
				.wrapOfflinePlayer(questionerName);

		long time = 0;
		for (UUID splayer : region.getOwners().getUniqueIds()) {
			OfflinePlayer oplayer = Bukkit.getOfflinePlayer(splayer);
			LocalPlayer lplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin()
					.wrapOfflinePlayer(oplayer);
			if (region.isMember(olocalplayer) || region.isMember(olocalplayer)) {
				time = lastSeen(lplayer.getUniqueId()) + (long) ConfigValues.buyupMembers;
			} else {
				time = lastSeen(lplayer.getUniqueId()) + (long) ConfigValues.buyupNoMembers;
			}
		}
		return time;
	}

	public static boolean isSpigot() {
		try {
			Class.forName("org.spigotmc.SpigotConfig");
			return true;
		} catch (final ClassNotFoundException e) {
			return false;
		}
	}

	public static boolean isPluginDisabled(CommandSender sender) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (ConfigValues.disabledWorlds.contains(p.getLocation().getWorld().getName())) {
				return true;
			}
		}
		return false;
	}
}
