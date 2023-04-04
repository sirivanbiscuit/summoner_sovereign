package net.ivandev.sovereign.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class CornerstoneProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

	public static class Level {

		private int val;

		private static final int MAX = 4;
		private static final int MIN = 0;

		public void upgrade() {
			this.val += this.val < MAX ? 1 : 0;
		}

		public void downgrade() {
			this.val -= this.val > MIN ? 1 : 0;
		}

		public void saveNBT(CompoundTag nbt) {
			nbt.putInt("level", this.val);
		}

		public void loadNBT(CompoundTag nbt) {
			this.val = nbt.getInt("level");
		}

		public void copy(Level level) {
			this.val = level.val;
		}

		public int get() {
			return this.val;
		}
	}

	public static final Capability<Level> LEVEL = CapabilityManager.get(new CapabilityToken<Level>() {
	});

	private final LazyOptional<Level> optional = LazyOptional.of(this::initLevel);

	private Level level = null;

	private Level initLevel() {
		this.level = this.level == null ? new Level() : this.level;
		return this.level;
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
		return cap == LEVEL ? this.optional.cast() : LazyOptional.empty();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return getCapability(cap);
	}

}
