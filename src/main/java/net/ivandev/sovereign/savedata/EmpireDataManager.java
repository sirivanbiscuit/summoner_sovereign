package net.ivandev.sovereign.savedata;

import java.util.ArrayList;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.empiremech.Empire;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

public class EmpireDataManager extends SavedData {

	private static final String NAME = SovereignMod.MOD_ID + "_empiredata";

	private ArrayList<Empire> empireData = new ArrayList<>();

	public EmpireDataManager() {
	}

	public EmpireDataManager(CompoundTag nbt) {
		this.empireData.clear();

		for (int i = 0; i < nbt.getCompound("empires").size(); i++) {
			this.empireData.add(Empire.constructEmpire(nbt.getCompound("empires").getCompound("empire" + i)));
		}
	}

	@Override
	public CompoundTag save(CompoundTag nbt) {
		CompoundTag nbtEmpires = new CompoundTag();
		for (int id = 0; id < this.empireData.size(); id++) {
			nbtEmpires.put("empire" + id, this.empireData.get(id).constructCompoundTag());
		}
		nbt.put("empires", nbtEmpires);

		return nbt;
	}

	public static void set(ArrayList<Empire> list, ServerLevel level) {
		EmpireDataManager data = level.getLevel().getServer().getLevel(Level.OVERWORLD).getDataStorage()
				.get(EmpireDataManager::new, EmpireDataManager.NAME);
		data.empireData = list;
		data.setDirty();
	}

	public static ArrayList<Empire> get(ServerLevel level) {
		MinecraftServer server = level.getServer();
		ServerLevel sLevel = server.getLevel(Level.OVERWORLD);
		return sLevel.getDataStorage().computeIfAbsent(EmpireDataManager::new, EmpireDataManager::new,
				EmpireDataManager.NAME).empireData;
	}

}
