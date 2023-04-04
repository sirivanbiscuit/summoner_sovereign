package net.ivandev.sovereign.init;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CityCornerstoneEntity;
import net.ivandev.sovereign.entity.cornerstone.TempleCornerstoneEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SovereignEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			SovereignMod.MOD_ID);

	public static final RegistryObject<EntityType<TempleCornerstoneEntity>> TEMPLE_CORNERSTONE = ENTITIES.register("temple_cornerstone",
			() -> EntityType.Builder.of(TempleCornerstoneEntity::new, MobCategory.MISC).sized(1.0f, 2.5f)
					.build(new ResourceLocation(SovereignMod.MOD_ID, "temple_cornerstone").toString()));
	public static final RegistryObject<EntityType<CityCornerstoneEntity>> CITY_CORNERSTONE = ENTITIES.register("city_cornerstone",
			() -> EntityType.Builder.of(CityCornerstoneEntity::new, MobCategory.MISC).sized(1.0f, 2.5f)
					.build(new ResourceLocation(SovereignMod.MOD_ID, "city_cornerstone").toString()));

}
