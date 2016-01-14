package de.keks.internal.core.cubli;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_8_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R2.PacketPlayOutMapChunk;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class Version1_8_R2 {
    private final net.minecraft.server.v1_8_R2.Chunk chunk;

    /**
     * Creates a PacketMapChunk.
     *
     * @param world
     *            The chunk's world.
     * @param x
     *            The chunk's X.
     * @param z
     *            The chunk's Z.
     */

    public Version1_8_R2(final World world, final int x, final int z) {
        this(world.getChunkAt(x, z));
    }

    /**
     * Creates a PacketMapChunk.
     *
     * @param chunk
     *            The chunk.
     */

    public Version1_8_R2(final org.bukkit.Chunk chunk) {
        this.chunk = ((CraftChunk) chunk).getHandle();
    }

    /**
     * Sends this packet to a player. <br>
     * You still need to refresh it manually with
     * <code>world.refreshChunk(...)</code>.
     *
     * @param player
     *            The player.
     */
    public final void send(final Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, true, 20));
    }

    /**
     * Refresh a chunk.
     *
     * @param chunk
     *            The chunk.
     */

    public static final void refreshChunk(final Chunk chunk) {
        refreshChunk(chunk.getWorld(), chunk.getX(), chunk.getZ());
    }

    /**
     * Wrapper for <code>world.refreshChunk(...)</code>
     *
     * @param world
     *            The world.
     * @param x
     *            The chunk's X.
     * @param z
     *            The chunk's Z.
     */

    public static final void refreshChunk(final World world, final int x, final int z) {
        final Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        refreshChunk(world, x, z, players.toArray(new Player[players.size()]));
    }

    /**
     * Refresh a chunk for the selected players.
     *
     * @param world
     *            The chunk's world.
     * @param x
     *            The chunk's X.
     * @param z
     *            The chunk's Z.
     * @param players
     *            The players.
     */

    @SuppressWarnings("deprecation")
    public static final void refreshChunk(final World world, final int x, final int z, final Player... players) {
        final Version1_8_R2 packet = new Version1_8_R2(world.getChunkAt(x, z));
        for (final Player player : players) {
            packet.send(player);
        }
        world.refreshChunk(x, z);
    }
}
