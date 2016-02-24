package de.keks.internal.command.land.addons;

import java.util.HashSet;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.I18n;
import de.keks.internal.core.cApi.ChunkApi;
import de.keks.internal.core.tasks.RegionSaveTask;
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

public class LandAddonMobs extends MainCore {

	public LandAddonMobs(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("cubit.flag.mobs")) {

			Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			final String statuson = I18n.translate("messages.optionson");
			final String statusoff = I18n.translate("messages.optionsoff");
			final String flag = I18n.translate("optionName.monster");
			final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager()
					.getWorldGuardPlugin().wrapPlayer(player);

			this.setupLand.executorServiceCommands.submit(new Runnable() {
				@SuppressWarnings({ "serial" })
				public void run() {
					Player player = (Player) sender;
					String regionName = getRegionName(chunkX, chunkZ, world);
					if (!ProtectedRegion.isValidId(regionName)) {
						player.sendMessage(I18n.translate("messages.noRegionHere", new Object[0]));
						return;
					}
					ProtectedRegion region = getRegion(world, regionName);
					if (region == null) {
						player.sendMessage(I18n.translate("messages.noRegionHere", new Object[0]));
						return;
					}
					if (!region.isOwner(localplayer)) {
						player.sendMessage(I18n.translate("messages.noPermissionForRegion", new Object[0]));
						return;
					}
					//////////////////////////////////////////////////////////////////////////////
					if (args.length < 2) {
					} else if (args[1].toString().equalsIgnoreCase("on")) {
						if (region.getFlag(DefaultFlag.MOB_DAMAGE) == StateFlag.State.ALLOW) {
							player.sendMessage(I18n.translate("messages.optionsAlready", flag, statuson));
							return;
						}
						region.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.ALLOW);
						region.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.ALLOW);
						region.getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
							{

							}
						});

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.FLAME);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statuson));
						return;

					} else if (args[1].toString().equalsIgnoreCase("off")) {
						if (region.getFlag(DefaultFlag.MOB_DAMAGE) == StateFlag.State.DENY) {
							player.sendMessage(I18n.translate("messages.optionsAlready", flag, statusoff));
							return;
						}
						region.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
						region.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.ALLOW);
						region.getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
							{
								add(EntityType.CREEPER);
								add(EntityType.ZOMBIE);
								add(EntityType.SKELETON);
								add(EntityType.SILVERFISH);
								add(EntityType.ENDER_DRAGON);
								add(EntityType.WITHER);
								add(EntityType.WITHER_SKULL);
								add(EntityType.GIANT);
								add(EntityType.PIG_ZOMBIE);
								add(EntityType.CAVE_SPIDER);
								add(EntityType.SPIDER);
								add(EntityType.WITCH);
								add(EntityType.ENDERMITE);
								add(EntityType.GUARDIAN);

							}
						});

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.HAPPY_VILLAGER);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statusoff));
						return;
					}
					if (region.getFlag(DefaultFlag.MOB_DAMAGE) == StateFlag.State.DENY) {
						region.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.ALLOW);
						region.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.ALLOW);
						region.getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
							{

							}
						});

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.FLAME);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statuson));
						return;
					} else {
						region.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
						region.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.ALLOW);
						region.getFlags().put(DefaultFlag.DENY_SPAWN, new HashSet<EntityType>() {
							{
								add(EntityType.CREEPER);
								add(EntityType.ZOMBIE);
								add(EntityType.SKELETON);
								add(EntityType.SILVERFISH);
								add(EntityType.ENDER_DRAGON);
								add(EntityType.WITHER);
								add(EntityType.WITHER_SKULL);
								add(EntityType.GIANT);
								add(EntityType.PIG_ZOMBIE);
								add(EntityType.CAVE_SPIDER);
								add(EntityType.SPIDER);
								add(EntityType.WITCH);
								add(EntityType.ENDERMITE);
								add(EntityType.GUARDIAN);

							}
						});

						ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
								Effect.HAPPY_VILLAGER);

						setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
						player.sendMessage(I18n.translate("messages.options", flag, statusoff));
						return;
					}
					//////////////////////////////////////////////////////////////////////////////
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
