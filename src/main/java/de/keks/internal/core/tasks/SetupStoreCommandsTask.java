package de.keks.internal.core.tasks;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.register.CommandSetupStore;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class SetupStoreCommandsTask implements Runnable {

    private CubitPlugin cubit;

    private CommandSetupStore handler;

    public SetupStoreCommandsTask(CubitPlugin cubit, CommandSetupStore handler) {
        this.cubit = cubit;

        this.handler = handler;
    }

    @Override
    public void run() {
        cubit.getServer().getScheduler().runTaskAsynchronously(cubit, new Runnable() {
            @Override
            public void run() {
                if (!handler.isInitialized()) {
                    handler.initialize();
                }
            }
        });

    }
}
