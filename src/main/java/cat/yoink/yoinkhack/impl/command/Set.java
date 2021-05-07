package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Set extends Command
{
	public Set(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		String[] args = arguments.split(" ");

		for (Module module : Client.INSTANCE.moduleManager.getModules())
		{
			if (module.getName().equalsIgnoreCase(args[0]))
			{

				for (Setting setting : Client.INSTANCE.settingManager.getSettings())
				{
					if (setting.getParent().equals(module) && args[1].equalsIgnoreCase(setting.getName()))
					{

						if (setting.isInteger() && (setting.getIntMinValue() - 1) < Integer.parseInt(args[2].toLowerCase()) && (setting.getIntMaxValue() + 1) > Integer.parseInt(args[2].toLowerCase()))
						{
							setting.setIntValue(Integer.parseInt(args[2].toLowerCase()));
							LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getIntValue());
							return;
						}
						else if (setting.isBoolean())
						{
							setting.setBoolValue(args[2].equalsIgnoreCase("on") || args[2].equalsIgnoreCase("true"));
							LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getBoolValue());
							return;
						}
						else if (setting.isEnum())
						{
							for (String string : setting.getOptions())
							{
								if (args[2].equalsIgnoreCase(string)) setting.setEnumValue(string);
							}
							LoggerUtil.sendMessage("Set " + setting.getName() + " to " + setting.getEnumValue());
							return;
						}
					}
				}
			}
		}

		printUsage();
	}
}
