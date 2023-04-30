package net.ivandev.sovereign.entitydata.render.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.FarmCornerstone;
import net.ivandev.sovereign.entitydata.model.entity.FarmCornerstoneModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FarmCornerstoneRenderer extends GeoEntityRenderer<FarmCornerstone> {

	public FarmCornerstoneRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new FarmCornerstoneModel());
		this.shadowRadius = 0.3f;
	}

	@Override
	public ResourceLocation getTextureLocation(FarmCornerstone object) {
		return new ResourceLocation(SovereignMod.MOD_ID, "textures/entity/farm_cornerstone/farm_cornerstone.png");
	}
}
