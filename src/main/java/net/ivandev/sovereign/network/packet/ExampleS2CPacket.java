package net.ivandev.sovereign.network.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

/**
 * This was originally made for sending the cornerstone capabilities to the
 * client-side but that task has since become unneeded.
 */
public class ExampleS2CPacket {

	private int id;
	private int level;

	public ExampleS2CPacket(int id, int level) {
		this.id = id;
		this.level = level;
	}

	public static ExampleS2CPacket decode(FriendlyByteBuf buf) {
		return new ExampleS2CPacket(buf.getInt(0), buf.getInt(1));
	}

	public static void encode(ExampleS2CPacket pkt, FriendlyByteBuf buf) {
		buf.setInt(0, pkt.id);
		buf.setInt(1, pkt.level);
	}

	public static void handle(ExampleS2CPacket msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleLevelPacket(msg, ctx));
		});
		ctx.get().setPacketHandled(true);
	}

	public static void handleLevelPacket(ExampleS2CPacket msg, Supplier<NetworkEvent.Context> ctx) {
		/*
		 * Everything below (within this method) runs on the client (as opposed to the
		 * server, from which this method should have been called).
		 */
	}

}
