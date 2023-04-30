package net.ivandev.sovereign.capability.types;

import net.ivandev.sovereign.capability.ISovereignData;
import net.ivandev.sovereign.capability.SovereignCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CornerstoneCapability extends SovereignCapability<CornerstoneCapability.CornerstoneData> {

	public static final Capability<CornerstoneData> DATA = CapabilityManager
			.get(new CapabilityToken<CornerstoneData>() {
			});

	@Override
	protected CornerstoneData setInitObject() {
		return new CornerstoneData();
	}

	@Override
	protected Capability<CornerstoneData> capabilityValue() {
		return DATA;
	}

	public static class CornerstoneData implements ISovereignData {

		private int level = 1;

		private static final int MAX_LVL = 4;
		private static final int MIN_LVL = 1;

		public void upgrade() {
			this.level += this.level < MAX_LVL ? 1 : 0;
		}

		public void downgrade() {
			this.level -= this.level > MIN_LVL ? 1 : 0;
		}

		@Override
		public void saveNBT(CompoundTag nbt) {
			nbt.putInt("level", this.level);
		}

		@Override
		public void loadNBT(CompoundTag nbt) {
			this.level = nbt.getInt("level");
		}

		public CompoundTag writeNbt() {
			CompoundTag nbt = new CompoundTag();
			this.saveNBT(nbt);
			return nbt;
		}

		public void copy(CornerstoneData data) {
			this.level = data.level;
		}

		public int getLevel() {
			return this.level;
		}

		public void setLevel(int lvl) {
			this.level = lvl;
		}
	}

}
