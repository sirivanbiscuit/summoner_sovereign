package net.ivandev.sovereign.gui;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.emp.Empire;
import net.ivandev.sovereign.emp.EmpireIdeology;
import net.ivandev.sovereign.menu.ItemMenu;
import net.ivandev.sovereign.savedata.EmpireDataManager;
import net.ivandev.sovereign.util.FormatStrings;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
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
public class BookOfNationsOneGui extends AbstractContainerScreen<ItemMenu> {

	private static final ResourceLocation GUI_LOCATION = new ResourceLocation(SovereignMod.MOD_ID,
			"textures/gui/book_of_nations_volume_i.png");

	private final int guiX = 200;
	private final int guiY = 180;

	private int cornerX, cornerY;

	private EditBox nameBox, codeBox;
	private Button createBut, joinBut;

	public BookOfNationsOneGui(ItemMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@SuppressWarnings("resource")
	@Override
	protected void init() {
		super.init();

		this.cornerX = (this.width - this.guiX) / 2;
		this.cornerY = (this.height - this.guiY) / 2;

		this.nameBox = new EditBox(this.font, this.cornerX + 17, this.cornerY + 112, 100, 14, new TextComponent(""));
		this.codeBox = new EditBox(this.font, this.cornerX + 17, this.cornerY + 141, 100, 14, new TextComponent(""));
		this.addWidget(this.nameBox);
		this.addWidget(this.codeBox);

		this.createBut = new Button(this.cornerX + 128, this.cornerY + 111, 47, 20, new TextComponent("Create"),
				(b) -> {
					//
					// CREATES NEW EMPIRE IN THE CURRENT MAP
					// Blank or pre-existing names invalid
					//

					String empireName = this.nameBox.getValue();
					ServerLevel level = this.getMinecraft().getSingleplayerServer().getLevel(Level.OVERWORLD);
					LocalPlayer player = this.getMinecraft().player;
					ArrayList<Empire> empires = EmpireDataManager.get(level);
					if (!empireName.isBlank()) {
						boolean empireAvailable = true;
						for (Empire e : empires) {
							if (e.name.equals(empireName)) {
								empireAvailable = false;
								break;
							}
						}
						if (empireAvailable) {
							// leave current empire
							for (int id = 0; id < empires.size(); id++) {
								for (int pId = 0; pId < empires.get(id).members.size(); pId++) {
									if (empires.get(id).members.get(pId).equals(player.getUUID())) {
										empires.get(id).members.remove(pId);
										break;
									}
								}
							}

							// create and join new empire
							ArrayList<UUID> playerId = new ArrayList<>();
							playerId.add(this.getMinecraft().player.getUUID());
							empires.add(new Empire(empireName, "", EmpireIdeology.DEMOCRATIC, true, playerId));
							EmpireDataManager.set(empires, level);
							ServerLifecycleHooks.getCurrentServer().getPlayerList()
									.broadcastMessage(
											new TextComponent(FormatStrings.YELLOW + player.getName().getString()
													+ " has formed the empire of " + empireName),
											ChatType.CHAT, Util.NIL_UUID);
						} else {
							// error - preexisting name
							player.sendMessage(
									new TextComponent(FormatStrings.RED + "An empire with that name already exists"),
									player.getUUID());
						}
						player.closeContainer();
					}
				});
		this.joinBut = new Button(this.cornerX + 128, this.cornerY + 136, 47, 20, new TextComponent("Join"), (b) -> {
			//
			// JOINS EMPIRE GIVEN IN INPUT BOX
			// Input empire name must exist and be open
			//

			String empireName = this.nameBox.getValue();
			ServerLevel level = this.getMinecraft().getSingleplayerServer().getLevel(Level.OVERWORLD);
			LocalPlayer player = this.getMinecraft().player;
			ArrayList<Empire> empires = EmpireDataManager.get(level);
			boolean empireAvailable = false;
			int empireId = -1;
			Empire empire = null;
			if (!empireName.isBlank()) {
				for (int id = 0; id < empires.size(); id++) {
					if (empires.get(id).name.equals(empireName)) {
						empireAvailable = true;
						empireId = id;
						empire = empires.get(empireId);
						break;
					}
				}
				if (empireAvailable && empireId >= 0) {
					if (this.codeBox.getValue().equals(empire.passcode) || empire.passcode.isBlank()) {
						if (!empire.members.contains(player.getUUID())) {
							// leave current empire
							for (int id = 0; id < empires.size(); id++) {
								for (int pId = 0; pId < empires.get(id).members.size(); pId++) {
									if (empires.get(id).members.get(pId).equals(player.getUUID())) {
										empires.get(id).members.remove(pId);
										break;
									}
								}
							}

							// join given empire
							empires.get(empireId).members.add(player.getUUID());
							EmpireDataManager.set(empires, level);
							ServerLifecycleHooks.getCurrentServer().getPlayerList()
									.broadcastMessage(
											new TextComponent(FormatStrings.YELLOW + player.getName().getString()
													+ " has joined the empire of " + empireName),
											ChatType.CHAT, Util.NIL_UUID);
						} else {
							// error - already in empire
							player.sendMessage(
									new TextComponent(FormatStrings.RED + "You have already joined this empire"),
									player.getUUID());
						}
					} else {
						// error - insufficient passcode
						player.sendMessage(
								new TextComponent(FormatStrings.RED
										+ "That empire has a passcode and the input passcode is incorrect"),
								player.getUUID());
					}
				} else {
					// error - invalid empire input
					player.sendMessage(new TextComponent(FormatStrings.RED + "An empire of that name does not exist"),
							player.getUUID());
				}
				player.closeContainer();
			}
		});
		this.addWidget(this.createBut);
		this.addWidget(this.joinBut);
	}

	@Override
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GUI_LOCATION);
		this.blit(pPoseStack, this.cornerX, this.cornerY, 0, 0, this.guiX, this.guiY);
		this.nameBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.codeBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.createBut.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.joinBut.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public boolean keyPressed(int i, int j, int k) {
		if (this.nameBox.isFocused()) {
			return this.nameBox.keyPressed(i, j, k);
		} else if (this.codeBox.isFocused()) {
			return this.codeBox.keyPressed(i, j, k);
		}
		return super.keyPressed(i, j, k);
	}

	@Override
	public boolean charTyped(char charIn, int i) {
		return this.codeBox.isFocused() ? this.codeBox.charTyped(charIn, i) : this.nameBox.charTyped(charIn, i);
	}
}