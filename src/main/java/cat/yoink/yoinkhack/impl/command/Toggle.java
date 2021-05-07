package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.module.Module;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Toggle extends Command
{
	public Toggle(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}


	@Override
	public void run(String arguments)
	{

		Module m = Client.INSTANCE.moduleManager.getModuleByName(arguments);
		if (m != null)
		{
			m.toggle();
			return;
		}

		printUsage();

	}
}
