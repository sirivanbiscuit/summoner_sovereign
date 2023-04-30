package net.ivandev.sovereign.init;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CapitalCornerstone;
import net.ivandev.sovereign.entity.cornerstone.Cornerstone;
import net.ivandev.sovereign.entity.cornerstone.FarmCornerstone;
import net.ivandev.sovereign.entity.cornerstone.TempleCornerstone;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SovereignEntityTypes {

	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES,
			SovereignMod.MOD_ID);

	public static final RegistryObject<EntityType<TempleCornerstone>> TEMPLE_CORNERSTONE = Cornerstone
			.register("temple", TempleCornerstone::new, ENTITIES);
	public static final RegistryObject<EntityType<CapitalCornerstone>> CAPITAL_CORNERSTONE = Cornerstone
			.register("capital", CapitalCornerstone::new, ENTITIES);
	public static final RegistryObject<EntityType<FarmCornerstone>> FARM_CORNERSTONE = Cornerstone
			.register("capital", FarmCornerstone::new, ENTITIES);

}
