package de.keks.internal.command.land;

import static de.keks.internal.I18n.translate;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.ConfigValues;
import de.keks.internal.I18n;
import de.keks.internal.SkyConfig;
import de.keks.internal.core.cubli.InternalBlockHighlight;
import de.keks.internal.core.tasks.RegionSaveTask;
import de.keks.internal.register.CommandSetupLand;
import de.keks.internal.register.CubitCore;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CMD_Land_Sell extends CubitCore {
    public CMD_Land_Sell(CommandSetupLand handler) {
        super(true);
        this.setupLand = handler;
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (sender.hasPermission("cubit.land.sell")) {

            final Player player = (Player) sender;
            final Location playerLocation = player.getLocation();
            final int chunkX = player.getLocation().getChunk().getX();
            final int chunkZ = player.getLocation().getChunk().getZ();
            final World world = player.getWorld();
            final LocalPlayer localplayer = CubitPlugin.inst().getHookManager().getWorldGuardManager().getWorldGuardPlugin().wrapPlayer(player);
            setupLand.executorServiceCommands.submit(new Runnable() {
                public void run() {
                    if (isServerRegion(chunkX, chunkZ, world)) {
                        sender.sendMessage(I18n.translate("messages.isServerregion"));
                        return;
                    }
                    RegionManager manager = getWorldGuard().getRegionManager(world);

                    String regionName = getRegionName(chunkX, chunkZ, world);

                    if (!manager.hasRegion(regionName)) {
                        player.sendMessage(translate("messages.noRegionHere"));
                        return;
                    }

                    ProtectedRegion region = getRegion(world, regionName);
                    if (region == null) {
                        player.sendMessage(translate("messages.noRegionHere"));
                        return;
                    }

                    if (!region.isOwner(localplayer)) {
                        player.sendMessage(translate("messages.noPermissionForRegion"));
                        return;
                    }

                    manager.removeRegion(regionName);
                    setupLand.getOfferManager().removeOffer(regionName);
                    moneyTransfer(null, player, calculateCosts(player, world, false));
                    if (isSpigot()) {
                        playEffect(player, Effect.INSTANT_SPELL, 1);
                    }
                    player.sendMessage(translate("messages.sellRegion", regionName, CMD_Land_Sell.this.calculateCosts(player, world, false)));

                    if (args.length < 2) {
                        scheduleSyncTask(setupLand,
                                new InternalBlockHighlight(setupLand.getCubitInstance(), playerLocation.getChunk(), (Material) SkyConfig.getObject(ConfigValues.landSellChunkBorders)));
                    } else if (args.length > 2 && !args[1].equalsIgnoreCase("empty")) {
                        scheduleSyncTask(setupLand,
                                new InternalBlockHighlight(setupLand.getCubitInstance(), playerLocation.getChunk(), (Material) SkyConfig.getObject(ConfigValues.landSellChunkBorders)));
                    }
                    setupLand.executorServiceRegions.submit(new RegionSaveTask(getWorldGuard(), null, world));
                }
            });
        } else {
            sender.sendMessage(I18n.translate("messages.noPermission", new Object[0]));
        }
        return true;
    }
}
