package net.ivandev.sovereign.entitydata.model.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CityCornerstoneEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CityCornerstoneModel extends AnimatedGeoModel<CityCornerstoneEntity> {

	@Override
	public ResourceLocation getAnimationFileLocation(CityCornerstoneEntity entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "animations/city_cornerstone.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(CityCornerstoneEntity entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "geo/city_cornerstone.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CityCornerstoneEntity entity) {
		return new ResourceLocation(SovereignMod.MOD_ID, "textures/entity/city_cornerstone.png");
	}
}
