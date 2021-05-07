package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class Discord extends Command
{
	public Discord(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		if (!arguments.equals(""))
		{
			Client.INSTANCE.rpc.setState(arguments);
			LoggerUtil.sendMessage("Set status to " + Client.INSTANCE.rpc.getState());
			return;
		}

		printUsage();
	}
}
