package net.ivandev.sovereign.event;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CapitalCornerstone;
import net.ivandev.sovereign.entity.cornerstone.FarmCornerstone;
import net.ivandev.sovereign.entity.cornerstone.TempleCornerstone;
import net.ivandev.sovereign.init.SovereignEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SovereignMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventBusEvents {

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
		event.put(SovereignEntityTypes.TEMPLE_CORNERSTONE.get(), TempleCornerstone.setAttributes());
		event.put(SovereignEntityTypes.CAPITAL_CORNERSTONE.get(), CapitalCornerstone.setAttributes());
		event.put(SovereignEntityTypes.FARM_CORNERSTONE.get(), FarmCornerstone.setAttributes());
	}
}
