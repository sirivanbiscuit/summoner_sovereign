package net.ivandev.sovereign.entity.cornerstone;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public abstract class Cornerstone extends Mob {

	protected Cornerstone(EntityType<? extends Mob> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Override
	public boolean isNoAi() {
		return true;
	}
}
