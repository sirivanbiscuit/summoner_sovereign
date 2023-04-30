package net.ivandev.sovereign.entity.cornerstone;

import net.ivandev.sovereign.entity.IUpgradable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class CapitalCornerstone extends Cornerstone implements IAnimatable, IUpgradable {

	private static final EntityDataAccessor<Integer> LEVEL_DATA = SynchedEntityData.defineId(CapitalCornerstone.class,
			EntityDataSerializers.INT);

	private final AnimationFactory factory = new AnimationFactory(this);

	public CapitalCornerstone(EntityType<? extends Cornerstone> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	public static AttributeSupplier setAttributes() {
		return Cornerstone.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0f).build();
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<CapitalCornerstone>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController()
				.setAnimation(new AnimationBuilder().addAnimation("animation.capital_cornerstone.idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(LEVEL_DATA, 0);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) {
		super.addAdditionalSaveData(pCompound);
		pCompound.putInt("level", this.getUpgradeLevel());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag pCompound) {
		super.readAdditionalSaveData(pCompound);
		setLevelData(pCompound.getInt("level"));
	}

	private void setLevelData(int level) {
		entityData.set(LEVEL_DATA, level);
	}

	@Override
	public String getLevelTexture() {
		return "textures/entity/capital_cornerstone/capital_cornerstone_" + this.getUpgradeLevel() + ".png";
	}

	@Override
	public int minLevel() {
		return 0;
	}

	@Override
	public int maxLevel() {
		return 3;
	}

	@Override
	public int getUpgradeLevel() {
		return this.entityData.get(LEVEL_DATA);
	}

	@Override
	public void changeLevelByFactor(int factor) {
		this.setLevelData(this.getUpgradeLevel() + factor);
	}

	@Override
	protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
		if (!pPlayer.getLevel().isClientSide() && pHand == InteractionHand.MAIN_HAND) {
			System.out.println(this.getUpgradeLevel());
			if (pPlayer.isCrouching()) {
				this.downgrade();
				return InteractionResult.SUCCESS;
			} else {
				this.upgrade();
				return InteractionResult.SUCCESS;
			}
		} else {
			return super.mobInteract(pPlayer, pHand);
		}
	}

}
