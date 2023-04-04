package net.ivandev.sovereign.entity.cornerstone;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TempleCornerstoneEntity extends Cornerstone implements IAnimatable {

	private final AnimationFactory factory = new AnimationFactory(this);

	public TempleCornerstoneEntity(EntityType<? extends Cornerstone> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}
	
	public static AttributeSupplier setAttributes() {
		return Cornerstone.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0f).build();
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(
				new AnimationController<TempleCornerstoneEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.temple_cornerstone.idle", true));
		return PlayState.CONTINUE;
	}

}
