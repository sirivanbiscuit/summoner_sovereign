package net.ivandev.sovereign.entitydata.model.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.FarmCornerstone;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FarmCornerstoneModel extends AnimatedGeoModel<FarmCornerstone> {

	@Override
	public ResourceLocation getAnimationFileLocation(FarmCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "animations/farm_cornerstone.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(FarmCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "geo/farm_cornerstone.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(FarmCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "textures/entity/farm_cornerstone/farm_cornerstone.png");
	}
}
