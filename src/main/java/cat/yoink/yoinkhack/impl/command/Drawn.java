package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Drawn extends Command
{
	public Drawn(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		Module module = Client.INSTANCE.moduleManager.getModuleByName(arguments);

		if (module != null)
		{
			module.setDrawn(!module.isDrawn());

			if (module.isDrawn())
			{
				LoggerUtil.sendMessage("Drawing " + module.getName().toLowerCase());
			}
			else
			{
				LoggerUtil.sendMessage("Stopped drawing " + module.getName().toLowerCase());
			}
			return;
		}

		printUsage();
	}
}
