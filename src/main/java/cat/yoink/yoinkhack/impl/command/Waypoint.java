package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.util.LoggerUtil;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Waypoint extends Command
{
	public Waypoint(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	@Override
	public void run(String arguments)
	{
		String[] split = arguments.split(" ");

		if (split[0].equalsIgnoreCase("add"))
		{
			if (split.length == 2)
			{
				if (mc.isIntegratedServerRunning())
				{
					Client.INSTANCE.waypointManager.addWaypoint(new cat.yoink.yoinkhack.api.waypoint.Waypoint((float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ, split[1], mc.player.dimension, "Singleplayer"));
				}
				else if (mc.getCurrentServerData() != null && !mc.isIntegratedServerRunning())
				{
					Client.INSTANCE.waypointManager.addWaypoint(new cat.yoink.yoinkhack.api.waypoint.Waypoint((float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ, split[1], mc.player.dimension, mc.getCurrentServerData().serverIP));
				}
				return;
			}
			else if (split.length == 5)
			{
				try
				{
					if (mc.isIntegratedServerRunning())
					{
						Client.INSTANCE.waypointManager.addWaypoint(new cat.yoink.yoinkhack.api.waypoint.Waypoint((float) Integer.parseInt(split[2]), (float) Integer.parseInt(split[3]), (float) Integer.parseInt(split[4]), split[1], mc.player.dimension, "Singleplayer"));
					}
					else if (mc.getCurrentServerData() != null && !mc.isIntegratedServerRunning())
					{
						Client.INSTANCE.waypointManager.addWaypoint(new cat.yoink.yoinkhack.api.waypoint.Waypoint((float) Integer.parseInt(split[2]), (float) Integer.parseInt(split[3]), (float) Integer.parseInt(split[4]), split[1], mc.player.dimension, mc.getCurrentServerData().serverIP));
					}
					return;
				}
				catch (Exception ignored)
				{
				}
			}
		}
		else if (split[0].equalsIgnoreCase("remove") || split[0].equalsIgnoreCase("del"))
		{
			if (Client.INSTANCE.waypointManager.getWaypointByName(split[1]) != null)
			{
				Client.INSTANCE.waypointManager.removeWaypoint(Client.INSTANCE.waypointManager.getWaypointByName(split[1]));
				return;
			}
		}
		else if (split[0].equalsIgnoreCase("list"))
		{
			if (Client.INSTANCE.waypointManager.getWaypoints().size() == 0)
			{
				LoggerUtil.sendMessage("No waypoints");
				return;
			}
			for (cat.yoink.yoinkhack.api.waypoint.Waypoint waypoint : Client.INSTANCE.waypointManager.getWaypoints())
			{
				LoggerUtil.sendMessage(String.format("%s: %s, %s, %s", waypoint.getName(), Math.round(waypoint.getX()), Math.round(waypoint.getY()), Math.round(waypoint.getZ())));
			}
			return;
		}

		printUsage();
	}
}
