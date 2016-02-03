package de.keks.internal.command.admin;

import java.util.HashSet;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.internal.I18n;
import de.keks.internal.core.cApi.ChunkApi;
import de.keks.internal.core.tasks.RegionSaveTask;
import de.keks.internal.register.CommandSetupAdmin;
import de.keks.internal.register.MainCore;

public class LandAdminSetServerRegion extends MainCore {

	/**
	 * Copyright:
	 * <ul>
	 * <li>Autor: Kekshaus</li>
	 * <li>2016</li>
	 * <li>www.minegaming.de</li>
	 * </ul>
	 * 
	 */

	public LandAdminSetServerRegion(CommandSetupAdmin handler) {
		super(true);
		this.setupAdmin = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, final String[] args) {
		if (sender.hasPermission("iLand.admin.setserverregion")) {

			final Player player = (Player) sender;
			final int chunkX = player.getLocation().getChunk().getX();
			final int chunkZ = player.getLocation().getChunk().getZ();
			final World world = player.getWorld();
			setupAdmin.executorServiceCommands.submit(new Runnable() {
				@Override
				public void run() {
					RegionManager manager = getWorldGuard().getRegionManager(world);
					ProtectedRegion region = null;

					String regionName = getRegionName(chunkX, chunkZ, world);
					String serverRegionName = getServerRegionName(chunkX, chunkZ, world);

					if (manager.hasRegion(regionName)) {
						sender.sendMessage(I18n.translate("messages.isPlayerregion"));
						return;
					}
					if (!manager.hasRegion(serverRegionName)) {
						region = createRegion(chunkX, chunkZ, world, serverRegionName);
						if (isSpigot()) {
							ChunkApi.chunkHighligh(player, player.getLocation(), player.getLocation().getChunk(),
									Effect.LARGE_SMOKE);
						}
						sender.sendMessage(I18n.translate("messages.setServerregion", serverRegionName));
					} else {
						sender.sendMessage(I18n.translate("messages.isAlreadyServerregion"));
					}
					setupAdmin.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), region, world));
				}
			});
		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

	@SuppressWarnings("serial")
	private ProtectedRegion createRegion(int chunkX, int chunkZ, World world, String regionName) {
		final Vector min;
		final Vector max;
		final Vector2D min2D;

		min2D = new Vector2D(chunkX * 16, chunkZ * 16);
		min = new Vector(min2D.getBlockX(), 0, min2D.getBlockZ());
		max = min.add(15, world.getMaxHeight(), 15);

		ProtectedRegion region = new ProtectedCuboidRegion(regionName, min.toBlockVector(), max.toBlockVector());
		region.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
		RegionGroupFlag groupFlag = DefaultFlag.USE.getRegionGroupFlag();
		region.setFlag(groupFlag, RegionGroup.NON_MEMBERS);
		region.setFlag(DefaultFlag.BUILD, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.USE, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.MOB_DAMAGE, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.CREEPER_EXPLOSION, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.TNT, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.POTION_SPLASH, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.FIRE_SPREAD, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.LAVA_FIRE, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.LIGHTNING, StateFlag.State.DENY);
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
		return region;
	}
}
