package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.util.LoggerUtil;
import cat.yoink.yoinkhack.api.util.LoginUtil;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Login extends Command
{
	public Login(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		String[] split = arguments.split(" ");
		if (split[0].equals("") || split[1].equals(""))
		{
			printUsage();
			return;
		}

		if (LoginUtil.loginWithOutput(split[0], split[1]))
		{
			LoggerUtil.sendMessage("Logged in");
		}
		else
		{
			LoggerUtil.sendMessage("Failed to log in");
		}
	}
}
