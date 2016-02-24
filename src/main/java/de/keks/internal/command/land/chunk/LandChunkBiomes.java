package de.keks.internal.command.land.chunk;

import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;

import de.keks.internal.I18n;
import de.keks.internal.register.CommandSetupLand;
import de.keks.internal.register.MainCore;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class LandChunkBiomes extends MainCore {

	public LandChunkBiomes(CommandSetupLand handler) {
		super(true);
		this.setupLand = handler;
	}

	@Override
	public boolean execute(final CommandSender sender, String[] args) {
		if (sender.hasPermission("cubit.landEdit.biomes")) {

			String biomes = null;
			for (Biome biome : Biome.values()) {
				if (biomes == null)
					biomes = biome.toString();
				else
					biomes += "§6, " + ChatColor.LIGHT_PURPLE + biome.toString();
			}
			sender.sendMessage(ChatColor.GREEN + "Available biomes: ");
			sender.sendMessage(ChatColor.LIGHT_PURPLE + biomes);

		} else {
			sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
		}
		return true;
	}

}
