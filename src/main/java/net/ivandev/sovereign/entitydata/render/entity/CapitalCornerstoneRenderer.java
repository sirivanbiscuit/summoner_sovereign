package net.ivandev.sovereign.entitydata.render.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CapitalCornerstone;
import net.ivandev.sovereign.entitydata.model.entity.CapitalCornerstoneModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class CapitalCornerstoneRenderer extends GeoEntityRenderer<CapitalCornerstone> {

	public CapitalCornerstoneRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new CapitalCornerstoneModel());
		this.shadowRadius = 0.3f;
	}

	@Override
	public ResourceLocation getTextureLocation(CapitalCornerstone object) {
		return new ResourceLocation(SovereignMod.MOD_ID, object.getLevelTexture());
	}

}