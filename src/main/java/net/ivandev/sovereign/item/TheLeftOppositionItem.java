package net.ivandev.sovereign.item;

import java.util.ArrayList;
import java.util.List;

import net.ivandev.sovereign.emp.Empire;
import net.ivandev.sovereign.init.SovereignMenuTypes;
import net.ivandev.sovereign.menu.ItemMenu;
import net.ivandev.sovereign.savedata.EmpireDataManager;
import net.ivandev.sovereign.tab.SovereignTab;
import net.ivandev.sovereign.util.FormatStrings;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
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

public class TheLeftOppositionItem extends Item {

	public TheLeftOppositionItem(Properties pProperties) {
		super(pProperties.tab(SovereignTab.SOVEREIGN_TAB));
	}

	@Override
	public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents,
			TooltipFlag pIsAdvanced) {
		pTooltipComponents.add(new TextComponent(FormatStrings.GRAY + "The Left Opposition"));
		super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
		if (!pLevel.isClientSide()) {
			int pId = Empire.getPlayerEmpireId(pPlayer, (ServerLevel) pLevel);
			ArrayList<Empire> empires = EmpireDataManager.get((ServerLevel) pLevel);
			if (pId >= 0 && !empires.isEmpty()) {
				if (!empires.get(pId).ideology.name.equals("Anarchist")) {
					// open gui if ideology is rank -1 -> +2
					NetworkHooks.openGui((ServerPlayer) pPlayer, new MenuProvider() {

						@Override
						public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory,
								Player pPlayer) {
							return new ItemMenu(SovereignMenuTypes.THE_LEFT_OPPOSITION.get(), pContainerId);
						}

						@Override
						public Component getDisplayName() {
							return new TextComponent("TheLeftOppositionGui");
						}
					});
				}
			}
		}
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, pPlayer.getItemInHand(pUsedHand));
	}
}
