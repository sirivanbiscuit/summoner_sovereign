package net.ivandev.sovereign.init;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.item.BookOfNationsOneItem;
import net.ivandev.sovereign.item.BookOfNationsTwoItem;
import net.ivandev.sovereign.item.TheLeftOppositionItem;
import net.ivandev.sovereign.item.TheRightOppositionItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SovereignItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			SovereignMod.MOD_ID);

	public static final RegistryObject<Item> BOOK_OF_NATIONS_VOLUME_I = ITEMS.register("book_of_nations_volume_i",
			() -> new BookOfNationsOneItem(new Properties()));
	public static final RegistryObject<Item> BOOK_OF_NATIONS_VOLUME_II = ITEMS.register("book_of_nations_volume_ii",
			() -> new BookOfNationsTwoItem(new Properties()));
	public static final RegistryObject<Item> THE_LEFT_OPPOSITION = ITEMS.register("the_left_opposition",
			() -> new TheLeftOppositionItem(new Properties()));
	public static final RegistryObject<Item> THE_RIGHT_OPPOSITION = ITEMS.register("the_right_opposition",
			() -> new TheRightOppositionItem(new Properties()));

}
