package cat.yoink.yoinkhack.api.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.util.LoggerUtil;
import net.minecraft.client.Minecraft;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Command
{
	public final Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private String[] alias;
	private String usage;

	public Command(String name, String[] alias, String usage)
	{
		this.name = name;
		this.alias = alias;
		this.usage = usage;
	}

	public void run(String arguments)
	{
	}

	public void printUsage()
	{
		LoggerUtil.sendMessage(getPrefix() + usage);
	}

	public String getPrefix()
	{
		return Client.INSTANCE.commandManager.getPrefix();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String[] getAlias()
	{
		return alias;
	}

	public void setAlias(String[] alias)
	{
		this.alias = alias;
	}

	public String getUsage()
	{
		return usage;
	}

	public void setUsage(String usage)
	{
		this.usage = usage;
	}
}
