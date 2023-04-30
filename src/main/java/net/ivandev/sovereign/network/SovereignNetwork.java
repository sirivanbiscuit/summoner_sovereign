package net.ivandev.sovereign.network;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.network.packet.ExampleS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class SovereignNetwork {

	private SovereignNetwork() {
	}

	private static final String PROTOCOL_VERSION = "1.0";

	private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(SovereignMod.MOD_ID, "network")) //
			.networkProtocolVersion(() -> PROTOCOL_VERSION) //
			.clientAcceptedVersions(PROTOCOL_VERSION::equals) //
			.serverAcceptedVersions(PROTOCOL_VERSION::equals) //
			.simpleChannel();

	private static int packetId = 0;

	public static void registerNetwork() {
		INSTANCE.registerMessage(packetId++, //
				ExampleS2CPacket.class, //
				ExampleS2CPacket::encode, //
				ExampleS2CPacket::decode, //
				ExampleS2CPacket::handle);
	}

	public static SimpleChannel getNetwork() {
		return INSTANCE;
	}
}
