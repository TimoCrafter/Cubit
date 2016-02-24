package de.keks.internal.core.database.yaml;

import de.keks.cubit.CubitPlugin;

public class YAMLSetup {
	private static YAMLConnectionHandler yamlProvider;

	public static boolean start() {
		yamlProvider = new YAMLConnectionHandler(CubitPlugin.inst());
		yamlProvider.setup();
		return true;

	}

	public static YAMLConnectionHandler getYAMLConnection() {
		return yamlProvider;
	}

}
