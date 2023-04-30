package net.ivandev.sovereign.capability.types;

import net.ivandev.sovereign.capability.ISovereignData;
import net.ivandev.sovereign.capability.SovereignCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class HostileCapability extends SovereignCapability<HostileCapability.HostileData> {

	public static final Capability<HostileData> DATA = CapabilityManager.get(new CapabilityToken<HostileData>() {
	});

	@Override
	protected HostileData setInitObject() {
		return new HostileData();
	}

	@Override
	protected Capability<HostileData> capabilityValue() {
		return DATA;
	}

	public class HostileData implements ISovereignData {

		public boolean created = false;

		@Override
		public void saveNBT(CompoundTag nbt) {
			nbt.putBoolean("created", this.created);
		}

		@Override
		public void loadNBT(CompoundTag nbt) {
			this.created = nbt.getBoolean("created");
		}

		public void copy(HostileData level) {
			this.created = level.created;
		}

	}
}