package net.ivandev.sovereign.util;

import net.ivandev.sovereign.SovereignMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class SovereignTags {

	public static final TagKey<Block> EMPTY = block("empty");
	public static final TagKey<Block> FLUID = block("fluid");

	public static final TagKey<Item> BUCKETS = item("buckets");

	private static TagKey<Block> block(String name) {
		return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(SovereignMod.MOD_ID, name));
	}

	private static TagKey<Item> item(String name) {
		return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(SovereignMod.MOD_ID, name));
	}

}
