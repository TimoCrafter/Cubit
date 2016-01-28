package de.keks.internal.command.land;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

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

public class CMD_Land_Info extends MainCore {

	private static final SimpleDateFormat LAST_SEEN = new SimpleDateFormat("dd.MM.yyyy");

	public CMD_Land_Info(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, String[] args) {
		if (sender.hasPermission("iLand.land.info")) {
			Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();

			this.setupLand.executorServiceCommands.submit(new Runnable() {
				public void run() {
					Player player = (Player) sender;
					if (isSpigot()) {
						playEffect(player, null, 1);
					}
					if (isServerRegion(chunkX, chunkZ, world)) {
						sender.sendMessage(I18n.translate("messages.isServerregion"));
						return;
					}
					String regionName = CMD_Land_Info.this.getRegionName(chunkX, chunkZ, world);
					Biome biome = player.getWorld().getBiome(player.getLocation().getBlockX(),
							player.getLocation().getBlockZ());
					if (!ProtectedRegion.isValidId(regionName)) {
						sender.sendMessage(
								I18n.translate("messages.regionBuyInfo",
										CMD_Land_Info.this.setupLand.getILandInstance().getHookManager()
												.getEconomyManager()
												.formatMoney(CMD_Land_Info.this.calculateCosts(player, world, true))));
						return;
					}
					ProtectedRegion region = CMD_Land_Info.this.getRegion(world, regionName);
					if (region == null) {
						sender.sendMessage(
								I18n.translate("messages.regionBuyInfo",
										CMD_Land_Info.this.setupLand.getILandInstance().getHookManager()
												.getEconomyManager()
												.formatMoney(CMD_Land_Info.this.calculateCosts(player, world, true))));
						return;
					}
					for (UUID regionplayer : region.getOwners().getUniqueIds()) {
						OfflinePlayer name = Bukkit.getOfflinePlayer(regionplayer);
						Set<String> member = new HashSet<String>();
						LocalPlayer lplayer = ILandPlugin.inst().getHookManager().getWorldGuardManager()
								.getWorldGuardPlugin().wrapOfflinePlayer(name);
						for (UUID memberuuid : region.getMembers().getUniqueIds()) {
							OfflinePlayer omember = Bukkit.getOfflinePlayer(memberuuid);
							LocalPlayer lmember = ILandPlugin.inst().getHookManager().getWorldGuardManager()
									.getWorldGuardPlugin().wrapOfflinePlayer(omember);
							member.add(lmember.getName());
						}

						String locked = region
								.getFlag(DefaultFlag.USE) != com.sk89q.worldguard.protection.flags.StateFlag.State.ALLOW
										? I18n.translate("landInfo.locked") : I18n.translate("landInfo.unlocked");
						String pvp = region
								.getFlag(DefaultFlag.PVP) != com.sk89q.worldguard.protection.flags.StateFlag.State.ALLOW
										? I18n.translate("landInfo.pvpoff") : I18n.translate("landInfo.pvpon");
						String tnt = region
								.getFlag(DefaultFlag.TNT) != com.sk89q.worldguard.protection.flags.StateFlag.State.ALLOW
										? I18n.translate("landInfo.tntoff") : I18n.translate("landInfo.tnton");
						String fire = region.getFlag(
								DefaultFlag.FIRE_SPREAD) != com.sk89q.worldguard.protection.flags.StateFlag.State.ALLOW
										? I18n.translate("landInfo.fireoff") : I18n.translate("landInfo.fireon");
						String mob = region.getFlag(
								DefaultFlag.MOB_DAMAGE) != com.sk89q.worldguard.protection.flags.StateFlag.State.ALLOW
										? I18n.translate("landInfo.mobsoff") : I18n.translate("landInfo.mobson");
						String potion = region.getFlag(
								DefaultFlag.POTION_SPLASH) != com.sk89q.worldguard.protection.flags.StateFlag.State.ALLOW
										? I18n.translate("landInfo.potionsoff") : I18n.translate("landInfo.potionson");

						String min = "(" + region.getMinimumPoint().getBlockX() + ", "
								+ region.getMinimumPoint().getBlockZ() + ")";
						String max = "(" + region.getMaximumPoint().getBlockX() + ", "
								+ region.getMaximumPoint().getBlockZ() + ")";

						sender.sendMessage(I18n.translate("landInfo.line1"));
						sender.sendMessage(I18n.translate("landInfo.line2", region.getId(), lplayer.getName()));
						if (!region.getMembers().getUniqueIds().isEmpty()) {
							sender.sendMessage(I18n.translate("landInfo.line3", member));
						}
						sender.sendMessage(I18n.translate("landInfo.line4", min, max, biome.toString()));
						sender.sendMessage(I18n.translate("landInfo.line5", CMD_Land_Info.LAST_SEEN
								.format(new Date(CMD_Land_Info.this.lastSeen(lplayer.getUniqueId())))));
						sender.sendMessage(I18n.translate("landInfo.line6",
								(new StringBuilder()).append(locked).append(I18n.translate("landInfo.split"))
										.append(tnt).append(I18n.translate("landInfo.split")).append(pvp)
										.append(I18n.translate("landInfo.split")).append(fire)
										.append(I18n.translate("landInfo.split")).append(mob)
										.append(I18n.translate("landInfo.split")).append(potion).toString()));
					}

					if (setupLand.getOfferManager().isOffered(regionName)) {
						String formattedMoney = setupLand.getILandInstance().getHookManager().getEconomyManager()
								.formatMoney(setupLand.getOfferManager().getOffer(regionName));
						sender.sendMessage(I18n.translate("messages.regionOffered", formattedMoney));
					}
					if (CMD_Land_Info.this.wasPlayerTooLongOff(CMD_Land_Info.this.getRegion(world, regionName),
							player)) {
						sender.sendMessage(I18n.translate("messages.regionBuyupInfo"));
					} else {
						if (CMD_Land_Info.this.timeForBuyupInfo(CMD_Land_Info.this.getRegion(world, regionName),
								player)) {
							long buyDate = CMD_Land_Info.this.getBuyupInfoDate(region, player);
							sender.sendMessage(I18n.translate("messages.regionBuyupInfoDate",
									CMD_Land_Info.LAST_SEEN.format(new Date(buyDate))));
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
