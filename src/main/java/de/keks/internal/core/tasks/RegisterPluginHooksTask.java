package de.keks.internal.core.tasks;

import de.keks.internal.plugin.hooks.HookCheck;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class RegisterPluginHooksTask implements Runnable {

	private HookCheck toLoad;

	public RegisterPluginHooksTask(HookCheck toLoad) {
		this.toLoad = toLoad;
	}

	@Override
	public void run() {
		if (toLoad != null) {
			toLoad.load();
		}
	}

}
