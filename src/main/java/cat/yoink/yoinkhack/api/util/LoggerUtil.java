package cat.yoink.yoinkhack.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class LoggerUtil
{
	public static void sendMessage(String message)
	{
		sendMessage(message, true);
	}

	public static void sendMessage(String message, boolean waterMark)
	{
		StringBuilder messageBuilder = new StringBuilder();
		if (waterMark) messageBuilder.append("&7[&cyoinkhack&7] &r");
		messageBuilder.append("&7").append(message);
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(messageBuilder.toString().replace("&", "\u00A7")));
	}
}
