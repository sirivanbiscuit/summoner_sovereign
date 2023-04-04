package net.ivandev.sovereign.entitydata.model.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.TempleCornerstoneEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TempleCornerstoneModel extends AnimatedGeoModel<TempleCornerstoneEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(TempleCornerstoneEntity entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "animations/temple_cornerstone.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(TempleCornerstoneEntity entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "geo/temple_cornerstone.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TempleCornerstoneEntity entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "textures/entity/temple_cornerstone.png");
	}
}
