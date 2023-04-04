package net.ivandev.sovereign.network;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.network.packet.ExampleC2SPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class SovereignNetwork {

	private static SimpleChannel INSTANCE;

	private static int packetId = 0;

	private static int id() {
		// bumps it up 1 while returning
		return packetId++;
	}

	public static void register() {
		SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(SovereignMod.MOD_ID, "network"))
				.networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true)
				.simpleChannel();

		INSTANCE = net;

		net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(ExampleC2SPacket::new)
				.encoder(ExampleC2SPacket::toBytes).consumer(ExampleC2SPacket::handle).add();
	}

	public static <SMG> void sendToServer(SMG message) {
		INSTANCE.sendToServer(message);
	}

	public static <SMG> void sendToPlayer(SMG message, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}
}
