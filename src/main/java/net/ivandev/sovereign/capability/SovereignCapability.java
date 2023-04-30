package net.ivandev.sovereign.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public abstract class SovereignCapability<T extends ISovereignData>
		implements ICapabilityProvider, INBTSerializable<CompoundTag> {

	/**
	 * A new object to set the capability holder as if an entity's capabilities are
	 * null (for example, they just spawned in). This object will ONLY be useful for
	 * this case, and this method will for the most part be ignored for pre-exising
	 * entities which already have an assigned capabilities instance.
	 */
	protected abstract T setInitObject();

	/**
	 * A capability token to be included in a capability data/provider class.
	 * <p>
	 * For example this could be of the form:
	 * <p>
	 * <code>public static final Capability CAPABILITY = ...</code>
	 */
	protected abstract Capability<T> capabilityValue();

	private final LazyOptional<T> opt = LazyOptional.of(this::verify);
	protected T value = null;

	private T verify() {
		if (this.value == null) {
			this.value = this.setInitObject();
		}
		return this.value;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		verify().saveNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		verify().loadNBT(nbt);
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap) {
		return cap == this.capabilityValue() ? this.opt.cast() : LazyOptional.empty();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return getCapability(cap);
	}

}
