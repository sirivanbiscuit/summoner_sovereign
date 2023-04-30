package net.ivandev.sovereign.item;

import java.util.List;

import net.ivandev.sovereign.init.SovereignMenuTypes;
import net.ivandev.sovereign.menu.ItemMenu;
import net.ivandev.sovereign.tab.SovereignTab;
import net.ivandev.sovereign.util.FormatStrings;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class BookOfNationsTwo extends Item {

	public BookOfNationsTwo(Properties pProperties) {
		super(pProperties.tab(SovereignTab.SOVEREIGN_TAB));
	}

	@Override
	public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
			TooltipFlag pIsAdvanced) {
		if (Screen.hasShiftDown()) {
			pTooltipComponents.add(new TextComponent(FormatStrings.GRAY + "Identity and Security"));
			pTooltipComponents.add(new TextComponent(""));
			pTooltipComponents.add(new TextComponent(FormatStrings.GRAY + "- Border Settings"));
			pTooltipComponents.add(new TextComponent(FormatStrings.GRAY + "- Name Settings"));
			pTooltipComponents.add(new TextComponent(FormatStrings.GRAY + "- Empire Deletion"));
		} else {
			pTooltipComponents.add(new TextComponent(FormatStrings.GRAY + "Identity and Security"));
			pTooltipComponents.add(new TextComponent(FormatStrings.GRAY + "Hold SHIFT for more information"));
		}
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		if (!pLevel.isClientSide()) {
			NetworkHooks.openGui((ServerPlayer) pPlayer, new MenuProvider() {

				@Override
				public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
					return new ItemMenu(SovereignMenuTypes.BOOK_OF_NATIONS_VOLUME_II.get(), pContainerId);
				}

				@Override
				public Component getDisplayName() {
					return new TextComponent("BookOfNationsTwoGui");
				}
			});
		}
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
	}

}