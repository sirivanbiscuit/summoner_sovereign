package net.ivandev.sovereign.gui;

import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.ivandev.sovereign.SovereignMod;
import net.ivandev.sovereign.emp.Empire;
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
import net.minecraftforge.server.ServerLifecycleHooks;

public class BookOfNationsTwoGui extends AbstractContainerScreen<ItemMenu> {

	private static final ResourceLocation GUI_LOCATION = new ResourceLocation(SovereignMod.MOD_ID,
			"textures/gui/book_of_nations_volume_ii.png");

	private final int guiX = 200;
	private final int guiY = 216;

	private int cornerX, cornerY;

	private EditBox codeBox, nameBox;
	private Button codeBut, nameBut, deleteBut;

	public BookOfNationsTwoGui(ItemMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
		super(pMenu, pPlayerInventory, pTitle);
	}

	@SuppressWarnings("resource")
	@Override
	protected void init() {
		super.init();

		this.cornerX = (this.width - this.guiX) / 2;
		this.cornerY = (this.height - this.guiY) / 2;

		this.codeBox = new EditBox(this.font, this.cornerX + 17, this.cornerY + 74, 100, 14, new TextComponent(""));
		this.nameBox = new EditBox(this.font, this.cornerX + 17, this.cornerY + 141, 100, 14, new TextComponent(""));
		this.addWidget(this.codeBox);
		this.addWidget(this.nameBox);

		this.codeBut = new Button(this.cornerX + 128, this.cornerY + 69, 47, 20, new TextComponent("Change"), (b) -> {
			//
			// CHANGES THE CURRENT TEAM'S PASSODE TO THE VALUE OF THE INPUT BOX
			// Blank entry removes code
			//

			String passcode = this.codeBox.getValue();
			ServerLevel level = this.getMinecraft().getSingleplayerServer().getLevel(Level.OVERWORLD);
			LocalPlayer player = this.getMinecraft().player;
			ArrayList<Empire> empires = EmpireDataManager.get(level);

			// find player's empire
			boolean inEmpire = false;
			int memId = -1;
			int empireId = -1;
			for (int id = 0; id < empires.size() && !inEmpire; id++) {
				for (int pId = 0; pId < empires.get(id).members.size(); pId++) {
					if (empires.get(id).members.get(pId).equals(player.getUUID())) {
						inEmpire = true;
						memId = pId;
						empireId = id;
						break;
					}
				}
			}

			// change empire passcode
			if (inEmpire && memId >= 0 && empireId >= 0) {
				if (passcode.isBlank()) {
					// remove passcode
					empires.get(empireId).passcode = "";
					empires.get(empireId).bordersOpen = true;
					ServerLifecycleHooks.getCurrentServer().getPlayerList()
							.broadcastMessage(new TextComponent(player.getName().getString()
									+ " has removed the border code of " + empires.get(empireId).name), ChatType.CHAT,
									Util.NIL_UUID);
				} else {
					// update passcode
					empires.get(empireId).passcode = passcode;
					empires.get(empireId).bordersOpen = false;
					ServerLifecycleHooks.getCurrentServer().getPlayerList()
							.broadcastMessage(new TextComponent(player.getName().getString()
									+ " has made a new border code for " + empires.get(empireId).name), ChatType.CHAT,
									Util.NIL_UUID);
				}
				EmpireDataManager.set(empires, level);
			} else {
				// error - not in any empire
				FormatStrings.errorMsg(player, "You are not a member of any empire");
			}
			player.closeContainer();
		});
		this.nameBut = new Button(this.cornerX + 128, this.cornerY + 136, 47, 20, new TextComponent("Change"), (b) -> {
			//
			// CHANGES THE CURRENT TEAM'S NAME TO THE VALUE OF THE INPUT BOX
			// Blank and pre-existing names are invalid
			//

			String name = this.nameBox.getValue();
			ServerLevel level = this.getMinecraft().getSingleplayerServer().getLevel(Level.OVERWORLD);
			LocalPlayer player = this.getMinecraft().player;
			ArrayList<Empire> empires = EmpireDataManager.get(level);
			if (!name.isBlank()) {
				// find player's empire
				boolean inEmpire = false;
				int empireId = -1;
				for (int id = 0; id < empires.size() && !inEmpire; id++) {
					for (int pId = 0; pId < empires.get(id).members.size(); pId++) {
						if (empires.get(id).members.get(pId).equals(player.getUUID())) {
							inEmpire = true;
							empireId = id;
							break;
						}
					}
				}
				if (inEmpire && empireId >= 0) {
					// verify original name
					boolean nameUsed = false;
					for (Empire e : empires) {
						nameUsed = name.equals(e.name);
					}
					if (!nameUsed) {
						// update empire name
						String ogName = empires.get(empireId).name;
						empires.get(empireId).name = name;
						ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastMessage(
								new TextComponent(player.getName().getString() + " has reformed the empire of " + ogName
										+ " as the empire of " + empires.get(empireId).name),
								ChatType.CHAT, Util.NIL_UUID);
						EmpireDataManager.set(empires, level);
					} else {
						// error - different empire already shares name
						FormatStrings.errorMsg(player, "An empire already has that name");
					}
				} else {
					// error - not in any empire
					FormatStrings.errorMsg(player, "You are not a member of any empire");
				}
				player.closeContainer();
			}
		});
		this.deleteBut = new Button(this.cornerX + 16, this.cornerY + 160, 158, 20,
				new TextComponent("(!) Destroy Empire (!)"), (b) -> {
					//
					// PERMANENT DELETION OF PLAYER EMPIRE
					// Requires all other players and all infastructure to be removed prior
					//

					ServerLevel level = this.getMinecraft().getSingleplayerServer().getLevel(Level.OVERWORLD);
					LocalPlayer player = this.getMinecraft().player;
					ArrayList<Empire> empires = EmpireDataManager.get(level);

					// verify that player's empire is emptied
					boolean inEmpire = false;
					boolean empireEmpty = false;
					int empireId = -1;
					for (int id = 0; id < empires.size() && !inEmpire; id++) {
						for (int pId = 0; pId < empires.get(id).members.size(); pId++) {
							if (empires.get(id).members.get(pId).equals(player.getUUID())) {
								inEmpire = true;
								empireId = id;
								empireEmpty = (empires.get(empireId).members.size() == 1);
								break;
							}
						}
					}
					if (inEmpire && empireId >= 0) {
						if (empireEmpty) {
							// remove empire from game
							ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastMessage(
									new TextComponent(FormatStrings.MAGENTA + "The empire of "
											+ empires.get(empireId).name + " has been permanently destroyed"),
									ChatType.CHAT, Util.NIL_UUID);
							empires.remove(empireId);
							EmpireDataManager.set(empires, level);
						} else {
							// error - empire still has contents
							player.sendMessage(new TextComponent(FormatStrings.RED
									+ "Your empire must be cleared of all buildings and must have member count of 1"),
									player.getUUID());
						}
					} else {
						// error - not in any empire
						player.sendMessage(new TextComponent(FormatStrings.RED + "You are not a member of any empire"),
								player.getUUID());
					}
					player.closeContainer();

				});
		this.addWidget(this.codeBut);
		this.addWidget(this.nameBut);
		this.addWidget(this.deleteBut);
	}

	@Override
	public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
		this.renderBackground(pPoseStack);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, GUI_LOCATION);
		this.blit(pPoseStack, this.cornerX, this.cornerY, 0, 0, this.guiX, this.guiY);
		this.nameBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.codeBox.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.nameBut.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.codeBut.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
		this.deleteBut.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
	}

	@Override
	protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public boolean keyPressed(int i, int j, int k) {
		if (this.codeBox.isFocused()) {
			return this.codeBox.keyPressed(i, j, k);
		} else if (this.nameBox.isFocused()) {
			return this.nameBox.keyPressed(i, j, k);
		}
		return super.keyPressed(i, j, k);
	}

	@Override
	public boolean charTyped(char charIn, int i) {
		return this.nameBox.isFocused() ? this.nameBox.charTyped(charIn, i) : this.codeBox.charTyped(charIn, i);
	}
}
