package de.keks.internal.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.core.database.mysql.SQLConnectionTask;

public class CubitListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		SQLConnectionTask task = new SQLConnectionTask(player);
		CubitPlugin.inst().getServer().getScheduler().runTaskLaterAsynchronously(CubitPlugin.inst(), task, 40L);
	}

}
