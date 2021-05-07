package cat.yoink.yoinkhack.api.command;

import cat.yoink.yoinkhack.api.util.LoggerUtil;
import cat.yoink.yoinkhack.impl.command.*;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class CommandManager
{
	private final ArrayList<Command> commands = new ArrayList<>();
	private String prefix = ".";

	public CommandManager()
	{
		commands.add(new Help("Help", new String[]{"h", "help"}, "help"));
		commands.add(new Macro("Macro", new String[]{"m", "macro"}, "macro <add|del|list> <key> <message>"));
		commands.add(new Toggle("Toggle", new String[]{"t", "toggle"}, "toggle <module>"));
		commands.add(new Bind("Bind", new String[]{"b", "bind"}, "bind <module> <key>"));
		commands.add(new Friend("Friend", new String[]{"f", "friend"}, "friend <add|del|list> <name>"));
		commands.add(new Prefix("Prefix", new String[]{"prefix"}, "prefix <prefix>"));
		commands.add(new Login("Login", new String[]{"login"}, "login <email> <password>"));
		commands.add(new Drawn("Drawn", new String[]{"drawn"}, "drawn <module>"));
		commands.add(new Set("Set", new String[]{"set"}, "set <module> <setting> <value>"));
		commands.add(new Waypoint("Waypoint", new String[]{"waypoint", "waypoints"}, "waypoint <add|del|list> <name> <coords> <x> <y> <z>"));
		commands.add(new Discord("Discord", new String[]{"d", "discord"}, "discord <text>"));
		commands.add(new Font("Font", new String[]{"font"}, "font <name>"));
	}


	public void runCommand(String arguments)
	{
		boolean found = false;
		String[] split = arguments.split(" ");
		String startCommand = split[0];
		String args = arguments.substring(startCommand.length()).trim();

		for (Command command : getCommands())
		{
			for (String alias : command.getAlias())
			{
				if (startCommand.equals(getPrefix() + alias))
				{
					command.run(args);
					found = true;
				}
			}
		}

		if (!found)
		{
			LoggerUtil.sendMessage("Unknown command");
		}
	}


	public String getPrefix()
	{
		return prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public ArrayList<Command> getCommands()
	{
		return commands;
	}
}
