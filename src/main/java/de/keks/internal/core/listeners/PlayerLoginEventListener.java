package de.keks.internal.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.keks.iLand.ILandPlugin;
import de.keks.internal.core.database.mysql.SQLConnectionTask;

public class PlayerLoginEventListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		SQLConnectionTask task = new SQLConnectionTask(player);
		ILandPlugin.inst().getServer().getScheduler().runTaskLaterAsynchronously(ILandPlugin.inst(), task, 40L);
	}

}
