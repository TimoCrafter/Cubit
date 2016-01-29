package de.keks.internal.core.database.yaml;

import de.keks.iLand.ILandPlugin;

public class YAMLSetup {
	private static YAMLConnectionHandler yamlProvider;

	public static boolean start() {
		yamlProvider = new YAMLConnectionHandler(ILandPlugin.inst());
		yamlProvider.setup();
		return true;

	}

	public static YAMLConnectionHandler getYAMLConnection() {
		return yamlProvider;
	}

}
