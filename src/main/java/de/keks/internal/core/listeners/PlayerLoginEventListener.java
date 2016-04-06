package de.keks.internal.core.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.core.database.mysql.SQLConnectionTask;

public class PlayerLoginEventListener implements Listener {


	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		SQLConnectionTask task = new SQLConnectionTask(player);
		CubitPlugin.inst().getServer().getScheduler().runTaskLaterAsynchronously(CubitPlugin.inst(), task, 40L);
	}
	
	@EventHandler
	public void onWaterLavaFlow(BlockFromToEvent event) {
		Location from = event.getBlock().getLocation();
		final World fromWorld = from.getWorld();
		final int fromChunkX = from.getChunk().getX();
		final int fromChunkZ = from.getChunk().getZ();
		String fromRegionName = getRegionName(fromChunkX, fromChunkZ, fromWorld);	
		ProtectedRegion fromRegion = getRegion(fromWorld, fromRegionName);
		
		Location to = event.getToBlock().getLocation();
		final World toWorld = to.getWorld();
		final int toChunkX = to.getChunk().getX();
		final int toChunkZ = to.getChunk().getZ();
		String toRegionName = getRegionName(toChunkX, toChunkZ, toWorld);
		ProtectedRegion toRegion = getRegion(toWorld, toRegionName);
		
		if (! sameOwner(fromRegion, toRegion)) {
			event.setCancelled(true);
		}
		
	}
	
	private boolean sameOwner(ProtectedRegion a, ProtectedRegion b) {
		if (a == null || b == null) {
			Bukkit.broadcastMessage("null");
		}
		for (UUID pa : a.getOwners().getUniqueIds()) {
			for (UUID pb : b.getOwners().getUniqueIds()) {
				if (pa.equals(pb)) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected String getRegionName(int x, int z, World world) {
		return world.getName().toLowerCase() + "_" + x + "_" + z;
	}
	
	protected WorldGuardPlugin getWorldGuard() {
		try {
			return CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected ProtectedRegion getRegion(World world, String regionName) {
		return getWorldGuard().getRegionManager(world).getRegion(regionName);
	}

}
