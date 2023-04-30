package net.ivandev.sovereign.capability;

import net.minecraft.nbt.CompoundTag;

public interface ISovereignData {

	public void saveNBT(CompoundTag nbt);

	public void loadNBT(CompoundTag nbt);

}
