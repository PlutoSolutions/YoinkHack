package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Friend extends Command
{
	public Friend(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		String[] split = arguments.split(" ");


		if (split[0].equalsIgnoreCase("add"))
		{
			if (Client.INSTANCE.friendManager.isFriend(split[1]))
			{
				LoggerUtil.sendMessage("User is already a friend");
			}
			else
			{
				Client.INSTANCE.friendManager.addFriend(split[1]);
				LoggerUtil.sendMessage("Added " + split[1]);
			}
			return;
		}
		if (split[0].equalsIgnoreCase("remove") || split[0].equalsIgnoreCase("del"))
		{
			if (Client.INSTANCE.friendManager.isFriend(split[1]))
			{
				Client.INSTANCE.friendManager.delFriend(split[1]);
				LoggerUtil.sendMessage("Removed " + split[1]);
			}
			else
			{
				LoggerUtil.sendMessage("User is not your friend");
			}
			return;
		}
		if (split[0].equalsIgnoreCase("list"))
		{
			if (Client.INSTANCE.friendManager.getFriends().size() == 0)
			{
				LoggerUtil.sendMessage("No friends");
				return;
			}
			for (cat.yoink.yoinkhack.api.friend.Friend friend : Client.INSTANCE.friendManager.getFriends())
			{
				LoggerUtil.sendMessage(friend.getName());
			}
			return;
		}

		printUsage();
	}
}
