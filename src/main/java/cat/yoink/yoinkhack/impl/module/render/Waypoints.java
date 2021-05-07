package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.waypoint.Waypoint;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import com.google.common.base.Strings;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Waypoints extends Module
{
	private final Setting death = new Setting("Death", this, false);
	private final Setting logout = new Setting("Logout", this, true);

	public Waypoints(String name, Category category, String description)
	{
		super(name, category, description);

		Setting coords = new Setting("Coords", this, true);
		Setting color = new Setting("Color", this, "Static", new ArrayList<>(Arrays.asList("Static", "Rainbow")));
		Setting red = new Setting("Red", this, 0, 255, 255);
		Setting green = new Setting("Green", this, 0, 20, 255);
		Setting blue = new Setting("Blue", this, 0, 20, 255);
		Setting alpha = new Setting("Alpha", this, 0, 100, 255);
		Setting rainbowSpeed = new Setting("RainbowSpeed", this, 0, 5, 10);

		addSetting(death);
		addSetting(logout);
		addSetting(coords);
		addSetting(color);
		addSetting(red);
		addSetting(green);
		addSetting(blue);
		addSetting(alpha);
		addSetting(rainbowSpeed);
	}

	@SubscribeEvent
	public void onDeath(GuiScreenEvent.InitGuiEvent event)
	{
		if (event.getGui() instanceof GuiGameOver && death.getBoolValue())
		{
			Client.INSTANCE.waypointManager.removeWaypoint(Client.INSTANCE.waypointManager.getWaypointByName("Death"));
			if (mc.isIntegratedServerRunning())
			{
				Client.INSTANCE.waypointManager.addWaypoint(new Waypoint((float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ, "Death", mc.player.dimension, "SinglePlayer"));
			}
			else if (mc.getCurrentServerData() != null && !mc.isIntegratedServerRunning())
			{
				Client.INSTANCE.waypointManager.addWaypoint(new Waypoint((float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ, "Death", mc.player.dimension, mc.getCurrentServerData().serverIP));
			}
		}
	}


	private void playerLogout(EntityPlayer player)
	{
		if (logout.getBoolValue())
		{
			Client.INSTANCE.waypointManager.removeWaypoint(Client.INSTANCE.waypointManager.getWaypointByName(player.getName() + "_logout"));
			if (mc.isIntegratedServerRunning())
			{
				Client.INSTANCE.waypointManager.addWaypoint(new Waypoint((float) player.posX, (float) player.posY, (float) player.posZ, player.getName() + "_logout", mc.player.dimension, "SinglePlayer"));
			}
			else if (mc.getCurrentServerData() != null && !mc.isIntegratedServerRunning())
			{
				Client.INSTANCE.waypointManager.addWaypoint(new Waypoint((float) player.posX, (float) player.posY, (float) player.posZ, player.getName() + "_logout", mc.player.dimension, mc.getCurrentServerData().serverIP));
			}
		}
	}

	private void playerLogin(String name)
	{
		if (logout.getBoolValue())
		{
			Client.INSTANCE.waypointManager.removeWaypoint(Client.INSTANCE.waypointManager.getWaypointByName(name + "_logout"));
		}
	}

	@SubscribeEvent
	public void onPacketReceive(PacketEvent.Receive event)
	{
		if (mc.player == null || mc.world == null) return;
        if (event.getPacket() instanceof SPacketPlayerListItem)
		{
			SPacketPlayerListItem packet2 = (SPacketPlayerListItem) event.getPacket();
			if (!SPacketPlayerListItem.Action.ADD_PLAYER.equals(packet2.getAction()) && !SPacketPlayerListItem.Action.REMOVE_PLAYER.equals(packet2.getAction()))
			{
				return;
			}
			UUID[] id = new UUID[1];
			String[] name = new String[1];
			EntityPlayer[] entity = new EntityPlayer[1];
			packet2.getEntries().stream().filter(Objects::nonNull).filter(data -> !Strings.isNullOrEmpty(data.getProfile().getName()) || data.getProfile().getId() != null).forEach(data ->
			{
				id[0] = data.getProfile().getId();

				switch (packet2.getAction())
				{
					case ADD_PLAYER:
					{
						name[0] = data.getProfile().getName();
						playerLogin(name[0]);
						break;
					}
					case REMOVE_PLAYER:
					{
						entity[0] = mc.world.getPlayerEntityByUUID(id[0]);
						if (entity[0] != null)
						{
							playerLogout(entity[0]);
						}
						break;
					}
					default:
						break;
				}
			});
		}
	}

	@Override
	public void onDisable()
	{
		ArrayList<Waypoint> waypoints = new ArrayList<>();
		for (Waypoint waypoint : Client.INSTANCE.waypointManager.getWaypoints())
		{
			if (waypoint.getName().endsWith("logout"))
			{
				waypoints.add(waypoint);
			}
		}
		for (Waypoint waypoint : waypoints)
		{
			Client.INSTANCE.waypointManager.removeWaypoint(waypoint);
		}
		waypoints.clear();
	}

	@SubscribeEvent
	public void onRender(RenderWorldLastEvent event)
	{
		if (mc.player == null || mc.world == null || mc.getRenderManager().options == null)
		{
			return;
		}
		Client.INSTANCE.waypointManager.renderWaypoints();
	}
}
