package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Prefix extends Command
{
	public Prefix(String name, String[] alias, String usage)
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

		Client.INSTANCE.commandManager.setPrefix(arguments);
		LoggerUtil.sendMessage(String.format("Set prefix to %s", arguments));
	}
}
