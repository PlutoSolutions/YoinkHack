package cat.yoink.yoinkhack.api.waypoint;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class WaypointManager
{
	private final ArrayList<Waypoint> waypoints = new ArrayList<>();

	public void addWaypoint(Waypoint waypoint)
	{
		waypoints.add(waypoint);
	}

	public void removeWaypoint(Waypoint waypoint)
	{
		waypoints.remove(waypoint);
	}

	public void renderWaypoints()
	{
		for (Waypoint waypoint : waypoints)
		{
			waypoint.renderBox();
		}
		for (Waypoint waypoint : waypoints)
		{
			waypoint.renderTag();
		}
	}

	public ArrayList<Waypoint> getWaypoints()
	{
		return waypoints;
	}

	public Waypoint getWaypointByName(String name)
	{
		for (Waypoint waypoint : waypoints)
		{
			if (waypoint.getName().equalsIgnoreCase(name)) return waypoint;
		}
		return null;
	}

	public ArrayList<String> getWaypointForConfig()
	{
		ArrayList<String> arrayList = new ArrayList<>();

		for (Waypoint waypoint : getWaypoints())
		{
			arrayList.add(String.format("%s:%s:%s:%s:%s:%s", waypoint.getName(), waypoint.getX(), waypoint.getY(), waypoint.getZ(), waypoint.getDimension(), waypoint.getIp()));
		}
		return arrayList;
	}
}
