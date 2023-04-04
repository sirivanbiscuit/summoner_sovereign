package net.ivandev.sovereign.menu;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ItemMenu extends AbstractContainerMenu {

	private static MenuType<?> MENU;

	public ItemMenu(MenuType<?> pMenuType, int pContainerId) {
		super(pMenuType, pContainerId);
		ItemMenu.MENU = pMenuType;
	}

	public ItemMenu(int id, Inventory inv, FriendlyByteBuf buffer) {
		this(ItemMenu.MENU, id);
	}

	@Override
	public boolean stillValid(Player pPlayer) {
		return true;
	}

}
