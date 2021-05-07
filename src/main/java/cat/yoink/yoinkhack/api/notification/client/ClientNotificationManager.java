package cat.yoink.yoinkhack.api.notification.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ClientNotificationManager
{
	private final ArrayList<Notification> notifications = new ArrayList<>();

	public ArrayList<Notification> getNotifications()
	{
		return notifications;
	}

	public void addNotification(String text, long duration)
	{
		notifications.add(new Notification(text, duration));
	}

	public void renderNotifications()
	{
		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		float neededY = (float) (scaledResolution.getScaledHeight() - 12);

		for (int i = 0; i < notifications.size(); ++i)
		{
			notifications.get(i).renderNotification(neededY -= 29f);
		}
	}
}
