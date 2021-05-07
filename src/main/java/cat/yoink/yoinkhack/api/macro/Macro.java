package cat.yoink.yoinkhack.api.macro;

import cat.yoink.yoinkhack.Client;
import net.minecraft.client.Minecraft;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Macro
{
	private int key;
	private String message;

	public Macro(int key, String message)
	{
		this.key = key;
		this.message = message;
	}

	public void run()
	{
		if (getMessage().startsWith(Client.INSTANCE.commandManager.getPrefix()))
			Client.INSTANCE.commandManager.runCommand(getMessage());
		else Minecraft.getMinecraft().player.sendChatMessage(getMessage());
	}

	public int getKey()
	{
		return key;
	}

	public void setKey(int key)
	{
		this.key = key;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
}
