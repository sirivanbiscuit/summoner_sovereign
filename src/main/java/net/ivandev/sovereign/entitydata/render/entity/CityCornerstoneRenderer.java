package net.ivandev.sovereign.entitydata.render.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CityCornerstoneEntity;
import net.ivandev.sovereign.entitydata.model.entity.CityCornerstoneModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CityCornerstoneRenderer extends GeoEntityRenderer<CityCornerstoneEntity> {

	public CityCornerstoneRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new CityCornerstoneModel());
		this.shadowRadius = 0.3f;
	}

	@Override
	public ResourceLocation getTextureLocation(CityCornerstoneEntity object) {
		return new ResourceLocation(SovereignMod.MOD_ID, "textures/entity/city_cornerstone/city_cornerstone.png");
	}
}
