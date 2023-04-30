package net.ivandev.sovereign.entitydata.model.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CapitalCornerstone;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CapitalCornerstoneModel extends AnimatedGeoModel<CapitalCornerstone> {

	@Override
	public ResourceLocation getAnimationFileLocation(CapitalCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "animations/capital_cornerstone.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(CapitalCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "geo/capital_cornerstone.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CapitalCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, entity.getLevelTexture());
	}
}
