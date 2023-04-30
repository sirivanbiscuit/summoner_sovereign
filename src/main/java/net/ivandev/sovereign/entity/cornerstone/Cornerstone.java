package net.ivandev.sovereign.entity.cornerstone;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.capability.types.CornerstoneCapability;
import net.ivandev.sovereign.capability.types.CornerstoneCapability.CornerstoneData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityType.EntityFactory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public abstract class Cornerstone extends Mob {

	private static final float STD_W = 1.0f;
	private static final float STD_H = 2.5f;

	protected Cornerstone(EntityType<? extends Mob> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	public boolean isNoAi() {
		return true;
	}

	public static <T extends Cornerstone> RegistryObject<EntityType<T>> register(String type, EntityFactory<T> factory,
			DeferredRegister<EntityType<?>> registry) {
		return registry.register(type + "_cornerstone",
				() -> EntityType.Builder.of(factory, MobCategory.MISC).sized(STD_W, STD_H)
						.build(new ResourceLocation(SovereignMod.MOD_ID, type + "_cornerstone").toString()));
	}

	/**
	 * Returns this cornerstone's capability data. If none is available, then
	 * returns <code>null</code>.
	 */
	public CornerstoneData getDataOrNull() {
		return this.getCapability(CornerstoneCapability.DATA).orElse(null);
	}

	/**
	 * Returns this cornerstone's capability data. If none is available, then
	 * returns a new cornerstone data instance.
	 */
	public CornerstoneData getDataOrInit() {
		return this.getCapability(CornerstoneCapability.DATA).orElse(new CornerstoneData());
	}
}
