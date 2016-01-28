package de.keks.internal.core.tasks;

import de.keks.iLand.ILandPlugin;
import de.keks.internal.I18n;
import de.keks.internal.command.config.ConfigValues;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class SetupLanguageTask implements Runnable {

	private ILandPlugin iLand;

	public SetupLanguageTask(ILandPlugin iLand) {
		this.iLand = iLand;
	}

	@Override
	public void run() {
		iLand.getServer().getScheduler().runTaskAsynchronously(iLand, new Runnable() {
			@Override
			public void run() {
				new I18n(iLand, ConfigValues.pluginLocale);

			}
		});

	}

}
