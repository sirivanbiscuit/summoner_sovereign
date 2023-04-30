package net.ivandev.sovereign.savedata;

import java.util.HashMap;
import java.util.Map;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.empiremech.Empire;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.saveddata.SavedData;

public class ChunkDataManager extends SavedData {

	public static class Entry {

		private boolean genDry = false;
		private String owner = null;

		public Entry setGenDry(boolean set) {
			this.genDry = set;
			return this;
		}

		public boolean getGenDry() {
			return this.genDry;
		}

		public void setOwner(Empire empire) {
			this.owner = empire.name;
		}

		public String getOwner() {
			return this.owner;
		}

		private CompoundTag saveEntry() {
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean("genDry", this.genDry);
			nbt.putString("owner", this.owner);
			return nbt;
		}

		private Entry loadEntry(CompoundTag nbt) {
			this.genDry = nbt.getBoolean("genDry");
			this.owner = nbt.getString("owner");
			return this;
		}
	}

	private static final String NAME = SovereignMod.MOD_ID + "_chunkdata";

	private Map<ChunkPos, Entry> chunkData = new HashMap<>();

	public ChunkDataManager() {
	}

	public ChunkDataManager(CompoundTag nbt) {
		ListTag list = nbt.getList("map", Tag.TAG_COMPOUND);
		for (Tag t : list) {
			CompoundTag cTag = (CompoundTag) t;
			this.chunkData.put(new ChunkPos(cTag.getInt("x"), cTag.getInt("z")),
					new Entry().loadEntry(cTag.getCompound("entry")));
		}
	}

	@Override
	public CompoundTag save(CompoundTag nbt) {
		ListTag list = new ListTag();
		this.chunkData.forEach((pos, entry) -> {
			CompoundTag cTag = new CompoundTag();
			cTag.putInt("x", pos.x);
			cTag.putInt("z", pos.z);
			cTag.put("entry", entry.saveEntry());
			list.add(cTag);
		});
		nbt.put("map", list);
		return nbt;
	}

	public static void set(ChunkPos chunkPos, ServerLevel level, Entry entry) {
		ChunkDataManager data = level.getLevel().getDataStorage().get(ChunkDataManager::new, ChunkDataManager.NAME);
		data.chunkData.put(chunkPos, entry);
		data.setDirty();
	}

	public static Entry get(ChunkPos chunkPos, ServerLevel level) {
		return level.getDataStorage().computeIfAbsent(ChunkDataManager::new, ChunkDataManager::new,
				ChunkDataManager.NAME).chunkData.computeIfAbsent(chunkPos, cP -> new Entry());
	}

}
