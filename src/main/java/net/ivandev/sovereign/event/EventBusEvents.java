package net.ivandev.sovereign.event;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.entity.cornerstone.CityCornerstoneEntity;
import net.ivandev.sovereign.entity.cornerstone.TempleCornerstoneEntity;
import net.ivandev.sovereign.init.SovereignEntityTypes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SovereignMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventBusEvents {

	@SubscribeEvent
	public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
		event.put(SovereignEntityTypes.TEMPLE_CORNERSTONE.get(), TempleCornerstoneEntity.setAttributes());
		event.put(SovereignEntityTypes.CITY_CORNERSTONE.get(), CityCornerstoneEntity.setAttributes());
	}
}
