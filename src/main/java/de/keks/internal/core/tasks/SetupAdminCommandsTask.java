package de.keks.internal.core.tasks;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.register.CommandSetupAdmin;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class SetupAdminCommandsTask implements Runnable {

	private CubitPlugin cubit;

	private CommandSetupAdmin handler;

	public SetupAdminCommandsTask(CubitPlugin cubit, CommandSetupAdmin handler) {
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
