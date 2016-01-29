package de.keks.internal.core.database;

import de.keks.internal.command.config.ConfigValues;
import de.keks.internal.core.database.mysql.SQLSetup;
import de.keks.internal.core.database.yaml.YAMLSetup;

public class DatabaseManager {
	public static boolean setupManager() {
		if (ConfigValues.isSQL) {
			return SQLSetup.start();
		}
		return YAMLSetup.start();

	}

}
