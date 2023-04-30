package net.ivandev.sovereign.gui;

import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.empiremech.Empire;
import net.ivandev.sovereign.empiremech.EmpireIdeology;
import net.ivandev.sovereign.menu.ItemMenu;
import net.ivandev.sovereign.savedata.EmpireDataManager;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.server.ServerLifecycleHooks;

@OnlyIn(Dist.CLIENT)
public class TheRightOppositionGui extends AbstractContainerScreen<ItemMenu> {

	private final int guiX = 146;
	private final int guiY = 180;

	private int cornerX, cornerY;
	private int id;

	private String ideologyName;

	private Button commitBut;

	private ServerLevel level;

	private LocalPlayer local;

	private ArrayList<Empire> empires;

	public TheRightOppositionGui(ItemMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@SuppressWarnings("resource")
	@Override
	protected void init() {
		super.init();

		this.cornerX = (this.width - this.guiX) / 2;
		this.cornerY = (this.height - this.guiY) / 2;

		this.level = this.getMinecraft().getSingleplayerServer().getLevel(Level.OVERWORLD);
		this.local = this.getMinecraft().player;
		this.empires = EmpireDataManager.get(this.level);
		this.id = Empire.getPlayerEmpireId(this.local, this.level);
		this.ideologyName = this.empires.get(this.id).ideology.driftRight().name.toLowerCase();

		this.commitBut = new Button(this.cornerX + 28, this.cornerY + 142, 90, 20, new TextComponent("Commit Ideology"),
				(b) -> {
					//
					// DRIFT PLAYER EMPIRE IDEOLOGY ONE TO THE RIGHT
					//

					EmpireIdeology iIdeo = this.empires.get(this.id).ideology;
					this.empires.get(this.id).ideology = iIdeo.driftRight();
					ServerLifecycleHooks.getCurrentServer().getPlayerList()
							.broadcastMessage(
									new TextComponent(local.getName().getString() + " has started a "
											+ this.ideologyName + " uprising in " + this.empires.get(id).name),
									ChatType.CHAT, Util.NIL_UUID);
					EmpireDataManager.set(this.empires, this.level);
					this.local.closeContainer();
				});
		this.addWidget(this.commitBut);
	}

	@Override
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0,
				new ResourceLocation(SovereignMod.MOD_ID, "textures/gui/" + ideologyName + "_select.png"));
		this.blit(pPoseStack, this.cornerX, this.cornerY, 0, 0, this.guiX, this.guiY);
		this.commitBut.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

}