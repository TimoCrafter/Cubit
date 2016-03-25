package de.keks.internal.core.entitylimit;

import org.bukkit.entity.Ambient;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Monster;
import org.bukkit.entity.NPC;
import org.bukkit.entity.WaterMob;

public class MobGroupCompare implements EntityCompare {

	private String mobGroup;

	public MobGroupCompare(String mobGroup) {
		this.mobGroup = mobGroup;
	}

	@Override
	public boolean isSimilar(Entity entity) {
		return (getMobGroup(entity) == this.mobGroup);
	}

	public static String getMobGroup(Entity entity) {
		if (entity instanceof Horse) {
			// Chicken, Cow, MushroomCow, Ocelot, Pig, Sheep, Wolf
			return "Other";
		}
		// Determine the general group this mob belongs to.
		if (entity instanceof Animals) {
			// Chicken, Cow, MushroomCow, Ocelot, Pig, Sheep, Wolf
			return "Animal";
		}

		if (entity instanceof Monster) {
			// Blaze, CaveSpider, Creeper, Enderman, Giant, PigZombie,
			// Silverfish, Skeleton, Spider, Witch, Wither, Zombie
			return "Monster";
		}

		if (entity instanceof Ambient) {
			// Bat
			return "Ambient";
		}

		if (entity instanceof WaterMob) {
			// Squid
			return "Water_Mob";
		}

		if (entity instanceof NPC) {
			// Villager
			return "Npc";
		}

		// Anything else.
		return "Other";
	}
}
