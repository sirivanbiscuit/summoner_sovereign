package net.ivandev.sovereign.init;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.item.BookOfNationsOne;
import net.ivandev.sovereign.item.BookOfNationsTwo;
import net.ivandev.sovereign.item.TheLeftOpposition;
import net.ivandev.sovereign.item.TheRightOpposition;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SovereignItems {

	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
			SovereignMod.MOD_ID);

	public static final RegistryObject<Item> BOOK_OF_NATIONS_VOLUME_I = ITEMS.register("book_of_nations_volume_i",
			() -> new BookOfNationsOne(new Properties()));
	public static final RegistryObject<Item> BOOK_OF_NATIONS_VOLUME_II = ITEMS.register("book_of_nations_volume_ii",
			() -> new BookOfNationsTwo(new Properties()));
	public static final RegistryObject<Item> THE_LEFT_OPPOSITION = ITEMS.register("the_left_opposition",
			() -> new TheLeftOpposition(new Properties()));
	public static final RegistryObject<Item> THE_RIGHT_OPPOSITION = ITEMS.register("the_right_opposition",
			() -> new TheRightOpposition(new Properties()));

}
