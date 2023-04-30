package net.ivandev.sovereign.event;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.capability.types.CornerstoneCapability;
import net.ivandev.sovereign.capability.types.HostileCapability;
import net.ivandev.sovereign.empiremech.raid.RaidMonsterConfig;
import net.ivandev.sovereign.entity.cornerstone.Cornerstone;
import net.ivandev.sovereign.savedata.ChunkDataManager;
import net.ivandev.sovereign.util.SovereignTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Procedures in this event bus:
 * <ul>
 * <li>Entity Capability Attachments</li>
 * <li>Capability Register Event</li>
 * <li>Bucket Disabling</li>
 * <li>Monster armouring</li>
 * <li>Chunk Drying</li>
 * </ul>
 */
@Mod.EventBusSubscriber(modid = SovereignMod.MOD_ID)
public class SovereignEvents {

	@SubscribeEvent
	public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
		Entity entity = event.getObject();
		if (entity instanceof Cornerstone) {
			if (!event.getObject().getCapability(CornerstoneCapability.DATA).isPresent()) {
				event.addCapability(new ResourceLocation(SovereignMod.MOD_ID, "properties"),
						new CornerstoneCapability());
			}
		}
		if (entity instanceof Monster) {
			if (!event.getObject().getCapability(HostileCapability.DATA).isPresent()) {
				event.addCapability(new ResourceLocation(SovereignMod.MOD_ID, "properties"), new HostileCapability());
			}
		}
	}

	/*
	 * @SubscribeEvent public static void onPlayerCloned(PlayerEvent.Clone event) {
	 * if (event.isWasDeath()) {
	 * event.getOriginal().getCapability(null).ifPresent(oldStore -> {
	 * event.getPlayer().getCapability(null).ifPresent(newStore -> { //
	 * newStore.copy(oldStore); }); }); } }
	 */

	@SubscribeEvent
	public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
		event.register(CornerstoneCapability.class);
		event.register(HostileCapability.class);
	}

	@SubscribeEvent
	public static void onBlockRightClicked(PlayerInteractEvent.RightClickBlock event) {
		ItemStack stack = event.getItemStack();

		/*
		 * Removes the functionality of water/lava buckets as fluid placers. Cauldrons
		 * still are fillable and buckets can still be used in their respective recipes.
		 */
		if (stack.is(SovereignTags.BUCKETS) && event.getWorld().isClientSide() && !event.getPlayer().isCreative()) {
			if (!(event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.CAULDRON
					&& !event.getPlayer().isCrouching()
					&& (stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.LAVA_BUCKET))) {
				event.setCancellationResult(InteractionResult.FAIL);
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoin(EntityJoinWorldEvent event) {
		Entity entity = event.getEntity();

		/*
		 * Equips all bipedal hostile mobs with powerful equipment to make nights/raids
		 * more dangerous/challenging.
		 */
		if (!event.getWorld().isClientSide() && (entity instanceof Zombie || entity instanceof AbstractSkeleton)) {
			entity.getCapability(HostileCapability.DATA).ifPresent(data -> {
				if (!data.created) {
					data.created = true;
					RaidMonsterConfig.raidSoldierEquip((Monster) entity);
				}
			});
		}
	}

	@SubscribeEvent
	public static void onEntityNaturalSpawn(LivingSpawnEvent.CheckSpawn event) {
		Entity e = event.getEntity();

		/*
		 * Uses "chunk drying" to increase the rarity of basic livestock/passive mobs.
		 * Chunks are marked as dry whenever a livestock mob attempts to spawn on it.
		 * Dry chunks will not be able to spawn any additional livestock mobs. Livestock
		 * mobs will have a 10% chance to sucessfully spawn, and a sucessful spawn will
		 * generate a herd of the respective mob. In addition all animal spawns will
		 * have a 75% chance to fail. These cuts cause livestock groups to be less
		 * randomized and more rare. The player will treat finding livestock in a
		 * similiar fashion to finding natural structures.
		 */
		if (!event.getWorld().isClientSide() && event.getSpawnReason() == MobSpawnType.CHUNK_GENERATION
				&& e instanceof Animal) {
			if (e instanceof Cow || e instanceof Pig || e instanceof Chicken || e instanceof Sheep) {
				ChunkPos cPos = new ChunkPos((int) e.getX(), (int) e.getZ());
				ServerLevel sL = (ServerLevel) e.getLevel();
				ChunkDataManager.Entry chunkEnt = ChunkDataManager.get(cPos, sL);
				if (Math.random() < 0.9 || chunkEnt.getGenDry()) {
					ChunkDataManager.set(cPos, sL, chunkEnt.setGenDry(true));
					e.discard();
				}
			} else if (Math.random() < 0.75) {
				e.discard();
			}
		}
	}

}
