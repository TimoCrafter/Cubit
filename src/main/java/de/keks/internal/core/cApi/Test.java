package de.keks.internal.core.cApi;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_9_R1.PacketPlayOutWorldParticles;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class Test {
	public void playEffect(Player np, Location location, Effect effect, int id, int data, float offsetX, float offsetY,
			float offsetZ, float speed, int particleCount, int radius) {
		CraftPlayer pl = (CraftPlayer) np;

		Packet packet;
		if (effect.getType() != Effect.Type.PARTICLE) {
			int packetData = effect.getId();
			packet = new PacketPlayOutWorldEvent(packetData,
					new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()), id, false);
		} else {
			net.minecraft.server.v1_9_R1.EnumParticle particle = null;
			int[] extra = null;
			for (net.minecraft.server.v1_9_R1.EnumParticle p : net.minecraft.server.v1_9_R1.EnumParticle.values()) {
				if (effect.getName().startsWith(p.b().replace("_", ""))) {
					particle = p;
					if (effect.getData() != null) {
						if (effect.getData().equals(org.bukkit.Material.class)) {
							extra = new int[] { id };
						} else {
							extra = new int[] { (data << 12) | (id & 0xFFF) };
						}
					}
					break;
				}
			}
			if (extra == null) {
				extra = new int[0];
			}
			packet = new PacketPlayOutWorldParticles(particle, true, (float) location.getX(), (float) location.getY(),
					(float) location.getZ(), offsetX, offsetY, offsetZ, speed, particleCount, extra);
		}
		int distance;
		radius *= radius;
		if (pl.getHandle().playerConnection == null) {
			return;
		}
		if (!location.getWorld().equals(pl.getWorld())) {
			return;
		}

		distance = (int) pl.getLocation().distanceSquared(location);
		if (distance <= radius) {
			pl.getHandle().playerConnection.sendPacket(packet);
		}
	}
}
