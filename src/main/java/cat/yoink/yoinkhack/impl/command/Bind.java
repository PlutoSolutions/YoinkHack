package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.module.Module;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Bind extends Command
{
	public Bind(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}


	@Override
	public void run(String arguments)
	{

		String[] split = arguments.split(" ");

		Module module = Client.INSTANCE.moduleManager.getModuleByName(split[0].toUpperCase());
		if (module != null)
		{
			try
			{
				module.setBind(Keyboard.getKeyIndex(split[1].toUpperCase()));
				return;
			}
			catch (Exception ignored)
			{
			}
		}

		printUsage();

	}
}
