package de.keks.internal.core.database.mysql;

import org.bukkit.entity.Player;

import de.keks.internal.core.database.DataController;

public class SQLConnectionTask implements Runnable {
    private Player player;

    public SQLConnectionTask(Player player) {
        this.player = player;
    }

    public void run() {
        DataController.savePlayer(player);
    }
}
