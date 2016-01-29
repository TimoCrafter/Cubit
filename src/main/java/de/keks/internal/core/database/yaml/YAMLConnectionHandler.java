package de.keks.internal.core.database.yaml;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class YAMLConnectionHandler {
	public FileConfiguration cubliConfig;
	public FileConfiguration offerConfig;
	public File cubliYML;
	public File offerYML;
	public Plugin plugin;

	public YAMLConnectionHandler(Plugin plugin) {
		this.plugin = plugin;

	}

	public void setup() {
		cubliYML = new File(plugin.getDataFolder() + "/cubliData.yml");
		offerYML = new File(plugin.getDataFolder() + "/offerData.yml");

		if (!cubliYML.exists()) {
			try {
				cubliYML.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!offerYML.exists()) {
			try {
				offerYML.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		cubliConfig = YamlConfiguration.loadConfiguration(cubliYML);
		offerConfig = YamlConfiguration.loadConfiguration(offerYML);
	}

	public void saveCubliData() {
		try {
			cubliConfig.save(cubliYML);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveOfferData() {
		try {
			offerConfig.save(offerYML);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getCubliConfig() {
		return this.cubliConfig;

	}

	public FileConfiguration getOfferConfig() {
		return this.offerConfig;

	}

}
