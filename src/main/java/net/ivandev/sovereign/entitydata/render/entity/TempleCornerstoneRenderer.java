package net.ivandev.sovereign.entitydata.render.entity;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.TempleCornerstone;
import net.ivandev.sovereign.entitydata.model.entity.TempleCornerstoneModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TempleCornerstoneRenderer extends GeoEntityRenderer<TempleCornerstone> {

	public TempleCornerstoneRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new TempleCornerstoneModel());
		this.shadowRadius = 0.3f;
	}

	@Override
	public ResourceLocation getTextureLocation(TempleCornerstone object) {
		return new ResourceLocation(SovereignMod.MOD_ID, "textures/entity/temple_cornerstone/temple_cornerstone.png");
	}
}
