package net.ivandev.sovereign.empiremech.raid;

import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public final class RaidMonsterConfig {

	private RaidMonsterConfig() {
	}

	private static final ItemStack[][] EQUIPMENT_POOL = {
			{ Items.IRON_HELMET.getDefaultInstance(), Items.CHAINMAIL_HELMET.getDefaultInstance() },
			{ Items.IRON_CHESTPLATE.getDefaultInstance(), Items.CHAINMAIL_CHESTPLATE.getDefaultInstance() },
			{ Items.IRON_LEGGINGS.getDefaultInstance(), Items.CHAINMAIL_LEGGINGS.getDefaultInstance() },
			{ Items.IRON_BOOTS.getDefaultInstance(), Items.CHAINMAIL_BOOTS.getDefaultInstance() },
			{ Items.IRON_AXE.getDefaultInstance(), Items.IRON_SWORD.getDefaultInstance() },
			{ Items.SHIELD.getDefaultInstance(), Items.IRON_AXE.getDefaultInstance(), ItemStack.EMPTY } };

	private static final Enchantment[][] ENCHANTMENT_POOL = {
			{ Enchantments.ALL_DAMAGE_PROTECTION, Enchantments.UNBREAKING },
			{ Enchantments.SHARPNESS, Enchantments.UNBREAKING, Enchantments.FIRE_ASPECT, Enchantments.KNOCKBACK } };

	private static final String[] SLOTS = { "head", "chest", "legs", "feet", "mainhand", "offhand" };

	private static final int POOL_SIZE = 6;

	public static void raidSoldierEquip(Monster entity) {
		ItemStack[] equipment = rndEquipPool();

		for (int i = 0; i < POOL_SIZE; i++) {
			ItemStack stack = equipment[i].copy();
			EquipmentSlot slot = EquipmentSlot.byName(SLOTS[i]);
			if (Math.random() < 0.2) {
				int type = slot.getType() == EquipmentSlot.Type.ARMOR ? 0 : 1;
				Enchantment[] enmtPool = ENCHANTMENT_POOL[type];
				stack.enchant(enmtPool[(int) (Math.random() * enmtPool.length)], (int) (Math.random() * 2) + 1);
			}
			if (!(entity instanceof AbstractSkeleton && i == 4) || Math.random() < 0.2) {
				if (entity.getItemBySlot(slot).getItem() == Items.AIR) {
					entity.setItemSlot(slot, stack);
				}
			}
		}

		// invisible status effects for added strength (last one day)
		entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1, true, false));
		entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1, true, false));

		// chance to create a backup soldier
		if (Math.random() < 0.3) {
			Level level = entity.getLevel();
			if (!level.isClientSide()) {
				int horde = Math.random() < 0.05 ? 10 : 1;
				for (int i = 0; i < horde; i++) {
					Vec3i rndVec = new Vec3i(Math.random() * 4 - 2, 0, Math.random() * 4 - 2);
					entity.getType().spawn((ServerLevel) level, null, null, entity.getOnPos().offset(rndVec),
							MobSpawnType.NATURAL, true, false);
				}
			}
		}
	}

	private static ItemStack[] rndEquipPool() {
		ItemStack[] pool = new ItemStack[POOL_SIZE];
		for (int i = 0; i < POOL_SIZE; i++) {
			int rnd = (int) (Math.random() * EQUIPMENT_POOL[i].length);
			pool[i] = EQUIPMENT_POOL[i][rnd].copy();
		}
		return pool;
	}

}
