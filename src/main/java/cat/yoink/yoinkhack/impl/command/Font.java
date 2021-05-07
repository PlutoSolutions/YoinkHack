package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.util.LoggerUtil;
import cat.yoink.yoinkhack.api.util.font.CFontRenderer;
import cat.yoink.yoinkhack.api.util.font.FontUtil;

/**
 * @author yoink
 * @since 9/1/2020
 */
public class Font extends Command
{
	public Font(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		if (arguments.equals(""))
		{
			printUsage();
			return;
		}

		if (FontUtil.validateFont(arguments))
		{
			try
			{
				Client.INSTANCE.cFontRenderer = new CFontRenderer(new java.awt.Font(arguments, java.awt.Font.PLAIN, 19), true, false);
				LoggerUtil.sendMessage("New font set");
			}
			catch (Exception e)
			{
				LoggerUtil.sendMessage("Failed to set font");
			}
		}
		else
		{
			LoggerUtil.sendMessage("Invalid font");
		}
	}


}
