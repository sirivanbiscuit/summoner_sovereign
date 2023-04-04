package net.ivandev.sovereign.tab;

import net.ivandev.sovereign.init.SovereignItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class SovereignTab extends CreativeModeTab {

	public static final SovereignTab SOVEREIGN_TAB = new SovereignTab(CreativeModeTab.TABS.length, "sovereign_tab");

	public SovereignTab(int pId, String pLangId) {
		super(pId, pLangId);
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(SovereignItems.BOOK_OF_NATIONS_VOLUME_I.get());
	}

}
