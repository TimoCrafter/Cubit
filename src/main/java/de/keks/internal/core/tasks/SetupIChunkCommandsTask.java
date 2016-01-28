package de.keks.internal.core.tasks;

import de.keks.iLand.ILandPlugin;
import de.keks.internal.register.CommandSetupIChunk;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class SetupIChunkCommandsTask implements Runnable {

	private ILandPlugin iLand;

	private CommandSetupIChunk handler;

	public SetupIChunkCommandsTask(ILandPlugin iLand, CommandSetupIChunk handler) {
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
