package net.ivandev.sovereign.network.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class ExampleC2SPacket {

	public ExampleC2SPacket() {

	}

	public ExampleC2SPacket(FriendlyByteBuf buf) {

	}

	public void toBytes(FriendlyByteBuf buf) {

	}

	public boolean handle(Supplier<NetworkEvent.Context> supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() -> {
			// RUNS ON SERVER
			ServerPlayer player = context.getSender();
			ServerLevel level = player.getLevel();
		});
		return true;
	}

}
