package net.ivandev.sovereign.util;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TextComponent;

public class FormatStrings {

	public static final String BLACK = "\u00A70";
	public static final String DARK_BLUE = "\u00A71";
	public static final String DARK_GREEN = "\u00A72";
	public static final String DARK_CYAN = "\u00A73";
	public static final String DARK_RED = "\u00A74";
	public static final String DARK_PURPLE = "\u00A75";
	public static final String DARK_YELLOW = "\u00A76";
	public static final String GRAY = "\u00A77";
	public static final String DARK_GRAY = "\u00A78";
	public static final String BLUE = "\u00A79";

	public static final String GREEN = "\u00A7a";
	public static final String CYAN = "\u00A7b";
	public static final String RED = "\u00A7c";
	public static final String MAGENTA = "\u00A7d";
	public static final String YELLOW = "\u00A7e";
	public static final String WHITE = "\u00A7f";

	public static final String OBFUSCATED = "\u00A7k";
	public static final String BOLD = "\u00A7l";
	public static final String STRIKETHROUGH = "\u00A7m";
	public static final String UNDERLINED = "\u00A7n";
	public static final String ITALIC = "\u00A7o";

	public static final String RESET = "\u00A7r";

	public static void errorMsg(LocalPlayer player, String msg) {
		player.sendMessage(new TextComponent(FormatStrings.RED + msg), player.getUUID());
	}

}
