package net.ivandev.sovereign.event;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.network.SovereignNetwork;
import net.ivandev.sovereign.network.packet.ExampleC2SPacket;
import net.ivandev.sovereign.util.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

	@Mod.EventBusSubscriber(modid = SovereignMod.MOD_ID, value = Dist.CLIENT)
	public static class ClientForgeEvents {
		@SuppressWarnings("resource")
		@SubscribeEvent
		public static void onKeyInput(InputEvent.KeyInputEvent event) {
			if (KeyBindings.DRINKING_KEY.consumeClick()) {
				Player p = Minecraft.getInstance().player;
				p.sendMessage(new TextComponent("Pressed a key!"), p.getUUID());
				SovereignNetwork.sendToServer(new ExampleC2SPacket());
			}
		}
	}

	@Mod.EventBusSubscriber(modid = SovereignMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents {
		public static void onKeyRegister() {
			ClientRegistry.registerKeyBinding(KeyBindings.DRINKING_KEY);
		}
	}
}
