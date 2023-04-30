package net.ivandev.sovereign.empiremech;

import java.util.ArrayList;
import java.util.UUID;

import net.ivandev.sovereign.savedata.EmpireDataManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public class Empire {

	public String name, passcode;
	public EmpireIdeology ideology;
	public boolean bordersOpen;
	public ArrayList<UUID> members;

	public Empire(String name, String passcode, EmpireIdeology ideology, boolean bordersOpen, ArrayList<UUID> members) {
		this.name = name;
		this.passcode = passcode;
		this.ideology = ideology;
		this.bordersOpen = bordersOpen;
		this.members = members;
	}

	public String getString(ServerLevel level) {
		String string = this.ideology.prefix + this.name + " (" + this.ideology.name + ", "
				+ (this.bordersOpen ? "open" : "closed") + " borders):";
		int count = 0;
		for (UUID uuid : this.members) {
			string += (count > 0 ? ", " : " ") + level.getPlayerByUUID(uuid).getName().getString();
			count++;
		}
		return string;
	}

	public boolean hasPlayer(Player player) {
		return members.contains(player.getUUID());
	}

	public CompoundTag constructCompoundTag() {
		CompoundTag nbt = new CompoundTag();
		nbt.putString("name", this.name);
		nbt.putString("passcode", this.passcode);
		CompoundTag nbtIdeos = new CompoundTag();
		nbtIdeos.putInt("id", this.ideology.id);
		nbt.put("ideology", nbtIdeos);
		nbt.putBoolean("bordersOpen", this.bordersOpen);
		CompoundTag nbtMems = new CompoundTag();
		for (int id = 0; id < this.members.size(); id++) {
			nbtMems.putUUID("member" + id, this.members.get(id));
		}
		nbt.put("members", nbtMems);
		return nbt;
	}

	public static Empire constructEmpire(CompoundTag nbt) {
		ArrayList<UUID> memUuids = new ArrayList<>();
		for (int iter = 0; iter < nbt.getCompound("members").size(); iter++) {
			memUuids.add(nbt.getCompound("members").getUUID("member" + iter));
		}
		Empire empire = new Empire(nbt.getString("name"), nbt.getString("passcode"),
				EmpireIdeology.SPECTRUM[nbt.getCompound("ideology").getInt("id")], nbt.getBoolean("bordersOpen"),
				memUuids);
		return empire;
	}

	public static int getPlayerEmpireId(Player player, ServerLevel level) {
		ArrayList<Empire> empires = EmpireDataManager.get(level);
		for (int id = 0; id < empires.size(); id++) {
			Empire e = empires.get(id);
			for (int pId = 0; pId < e.members.size(); pId++) {
				if (e.members.get(pId).equals(player.getUUID())) {
					return id;
				}
			}
		}
		return -1;
	}
}
