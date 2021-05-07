package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.util.LoggerUtil;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Macro extends Command
{
	public Macro(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		String[] args = arguments.split(" ");

		StringBuilder message = new StringBuilder();

		int i = 0;
		for (String arg : args)
		{
			if (i >= 2) message.append(arg).append(" ");
			i++;
		}


		if (args[0].equalsIgnoreCase("add"))
		{
			Client.INSTANCE.macroManager.removeMacro(Client.INSTANCE.macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1])));
			Client.INSTANCE.macroManager.addMacro(new cat.yoink.yoinkhack.api.macro.Macro(Keyboard.getKeyIndex(args[1].toUpperCase()), message.toString()));
			LoggerUtil.sendMessage("Added macro");
			return;
		}
		else if (args[0].equalsIgnoreCase("del"))
		{

			if (Client.INSTANCE.macroManager.getMacros().contains(Client.INSTANCE.macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1].toUpperCase()))))
			{
				Client.INSTANCE.macroManager.removeMacro(Client.INSTANCE.macroManager.getMacroByKey(Keyboard.getKeyIndex(args[1].toUpperCase())));
				LoggerUtil.sendMessage("Deleted macro");
			}
			else
			{
				LoggerUtil.sendMessage("Macro does not exist");
			}
			return;
		}
		else if (args[0].equalsIgnoreCase("list"))
		{
			for (cat.yoink.yoinkhack.api.macro.Macro macro : Client.INSTANCE.macroManager.getMacros())
			{
				LoggerUtil.sendMessage(Keyboard.getKeyName(macro.getKey()) + " -  " + macro.getMessage());
			}
			return;
		}

		printUsage();
	}
}
