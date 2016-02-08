package de.keks.internal.plugin.hooks;

import java.util.UUID;

import org.bukkit.plugin.Plugin;

import de.keks.internal.core.database.DatabaseFacade;

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
		return DatabaseFacade.getOfferdata(regionName);
	}

	public boolean addOffer(String regionName, double value, UUID uuid) {

		if (isCorrectRegionName(regionName) && value >= 0) {
			if (value <= 0) {
				DatabaseFacade.removeOfferdata(regionName);

			} else {
				DatabaseFacade.addOfferdata(regionName, value, uuid);

			}
		}

		return true;
	}

	public boolean removeOffer(String regionName) {
		if (isCorrectRegionName(regionName)) {
			DatabaseFacade.removeOfferdata(regionName);

		}
		return true;
	}

	public boolean isOffered(String regionName) {
		if (isCorrectRegionName(regionName)) {
			return DatabaseFacade.isOffered(regionName);
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
