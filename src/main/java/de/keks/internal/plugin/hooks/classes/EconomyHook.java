package de.keks.internal.plugin.hooks.classes;

import org.bukkit.OfflinePlayer;

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

public abstract class EconomyHook implements HookCheck {
	public abstract double getMoney(OfflinePlayer player);

	public abstract void giveMoney(OfflinePlayer player, double amount);

	public abstract String formatMoney(double amount);
}
