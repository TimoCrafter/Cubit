package de.keks.internal.plugin.hooks.classes;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class VaultHook extends EconomyHook {

	private Plugin plugin;

	private Economy vaultEcon;
	private boolean loaded = false;

	public VaultHook(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void load() {
		RegisteredServiceProvider<Economy> service = Bukkit.getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (service != null) {
			vaultEcon = service.getProvider();
			if (vaultEcon == null) {
				plugin.getLogger().severe("Plugin not found: Vault");
				vaultEcon = null;
				return;
			}

			if (!vaultEcon.isEnabled()) {
				plugin.getLogger().severe("Plugin not loaded: Vault");
				vaultEcon = null;
				return;
			}
		}

		loaded = true;
	}

	@Override
	public boolean isLoaded() {
		return loaded;
	}

	public double getMoney(OfflinePlayer player) {
		return vaultEcon.getBalance(player);
	}

	public void giveMoney(OfflinePlayer player, double amount) {
		if (amount > 0) {
			vaultEcon.depositPlayer(player, amount);
		} else {
			vaultEcon.withdrawPlayer(player, Math.abs(amount));
		}
	}

	@Override
	public String formatMoney(double amount) {
		return vaultEcon.format(amount);
	}

}
