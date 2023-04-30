package net.ivandev.sovereign.entitydata.model.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.TempleCornerstone;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TempleCornerstoneModel extends AnimatedGeoModel<TempleCornerstone> {

	@Override
	public ResourceLocation getAnimationFileLocation(TempleCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "animations/temple_cornerstone.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(TempleCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "geo/temple_cornerstone.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TempleCornerstone entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "textures/entity/temple_cornerstone/temple_cornerstone.png");
	}
}
