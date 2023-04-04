package net.ivandev.sovereign;

import net.ivandev.sovereign.entitydata.render.entity.CityCornerstoneRenderer;
import net.ivandev.sovereign.entitydata.render.entity.TempleCornerstoneRenderer;
import net.ivandev.sovereign.gui.BookOfNationsOneGui;
import net.ivandev.sovereign.gui.BookOfNationsTwoGui;
import net.ivandev.sovereign.gui.TheLeftOppositionGui;
import net.ivandev.sovereign.gui.TheRightOppositionGui;
import net.ivandev.sovereign.init.SovereignEntityTypes;
import net.ivandev.sovereign.init.SovereignItems;
import net.ivandev.sovereign.init.SovereignMenuTypes;
import net.ivandev.sovereign.network.SovereignNetwork;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(SovereignMod.MOD_ID)
public class SovereignMod {

	public static final String MOD_ID = "sovereign";

	public SovereignMod() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		SovereignItems.ITEMS.register(bus);
		SovereignMenuTypes.MENUS.register(bus);
		SovereignEntityTypes.ENTITIES.register(bus);

		bus.addListener(this::commonSetup);
		bus.addListener(this::clientSetup);

		MinecraftForge.EVENT_BUS.register(this);

		// start gecko api
		GeckoLib.initialize();
	}

	private void clientSetup(final FMLCommonSetupEvent event) {
		MenuScreens.register(SovereignMenuTypes.BOOK_OF_NATIONS_VOLUME_I.get(), BookOfNationsOneGui::new);
		MenuScreens.register(SovereignMenuTypes.BOOK_OF_NATIONS_VOLUME_II.get(), BookOfNationsTwoGui::new);
		MenuScreens.register(SovereignMenuTypes.THE_LEFT_OPPOSITION.get(), TheLeftOppositionGui::new);
		MenuScreens.register(SovereignMenuTypes.THE_RIGHT_OPPOSITION.get(), TheRightOppositionGui::new);

		EntityRenderers.register(SovereignEntityTypes.TEMPLE_CORNERSTONE.get(), TempleCornerstoneRenderer::new);
		EntityRenderers.register(SovereignEntityTypes.CITY_CORNERSTONE.get(), CityCornerstoneRenderer::new);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		SovereignNetwork.register();
	}
}
