package net.ivandev.sovereign.init;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.menu.ItemMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SovereignMenuTypes {

	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
			SovereignMod.MOD_ID);

	public static final RegistryObject<MenuType<ItemMenu>> BOOK_OF_NATIONS_VOLUME_I = initItemMenu(
			"book_of_nations_volume_i");
	public static final RegistryObject<MenuType<ItemMenu>> BOOK_OF_NATIONS_VOLUME_II = initItemMenu(
			"book_of_nations_volume_ii");
	public static final RegistryObject<MenuType<ItemMenu>> THE_LEFT_OPPOSITION = initItemMenu("the_left_opposition");
	public static final RegistryObject<MenuType<ItemMenu>> THE_RIGHT_OPPOSITION = initItemMenu("the_right_opposition");

	private static RegistryObject<MenuType<ItemMenu>> initItemMenu(String id) {
		return MENUS.register(id, () -> IForgeMenuType.create(ItemMenu::new));
	}

}