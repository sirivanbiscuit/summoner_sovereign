package net.ivandev.sovereign.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class HostileProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

	public static class HostileData {

		public boolean created;

		public void saveNBT(CompoundTag nbt) {
			nbt.putBoolean("created", this.created);
		}

		public void loadNBT(CompoundTag nbt) {
			this.created = nbt.getBoolean("created");
		}

		public void copy(HostileData level) {
			this.created = level.created;
		}
	}

	public static final Capability<HostileData> DATA = CapabilityManager.get(new CapabilityToken<HostileData>() {
	});

	private final LazyOptional<HostileData> optional = LazyOptional.of(this::initLevel);

	private HostileData data = null;

	private HostileData initLevel() {
		this.data = this.data == null ? new HostileData() : this.data;
		return this.data;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		initLevel().saveNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		initLevel().loadNBT(nbt);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap) {
		return cap == DATA ? this.optional.cast() : LazyOptional.empty();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return getCapability(cap);
	}

}
