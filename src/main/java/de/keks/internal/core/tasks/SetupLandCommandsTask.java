package de.keks.internal.core.tasks;

import de.keks.iLand.ILandPlugin;
import de.keks.internal.register.CommandSetupLand;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class SetupLandCommandsTask implements Runnable {

	private ILandPlugin iLand;

	private CommandSetupLand handler;

	public SetupLandCommandsTask(ILandPlugin iLand, CommandSetupLand handler) {
		this.iLand = iLand;

		this.handler = handler;
	}

	@Override
	public void run() {
		iLand.getServer().getScheduler().runTaskAsynchronously(iLand, new Runnable() {
			@Override
			public void run() {
				if (!handler.isInitialized()) {
					handler.initialize();
				}
			}
		});

	}
}
