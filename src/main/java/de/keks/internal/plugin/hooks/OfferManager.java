package de.keks.internal.plugin.hooks;

import org.bukkit.plugin.Plugin;

import de.keks.internal.core.database.DataController;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class OfferManager {

	public OfferManager(Plugin plugin) {
	}

	public double getOffer(String regionName) {
		return DataController.getOfferdata(regionName);
	}

	public boolean addOffer(String regionName, double value) {

		if (isCorrectRegionName(regionName) && value >= 0) {
			if (value <= 0) {
				DataController.removeOfferdata(regionName);

			} else {
				DataController.addOfferdata(regionName, value);

			}
		}

		return true;
	}

	public boolean removeOffer(String regionName) {
		if (isCorrectRegionName(regionName)) {
			DataController.removeOfferdata(regionName);

		}
		return true;
	}

	public boolean isOffered(String regionName) {
		if (isCorrectRegionName(regionName)) {
			return DataController.isOffered(regionName);
		}
		return false;
	}

	private boolean isCorrectRegionName(String regionName) {
		if (regionName == null || regionName == "") {
			return false;
		}
		return true;
	}
}
