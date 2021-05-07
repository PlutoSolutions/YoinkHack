package cat.yoink.yoinkhack.api.notification.desktop;

import net.minecraft.client.Minecraft;

import java.awt.*;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class DesktopNotificationManager
{
	public TrayIcon trayIcon;

	public DesktopNotificationManager()
	{
		Minecraft mc = Minecraft.getMinecraft();
		SystemTray tray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("yoink.png"));
		trayIcon = new TrayIcon(image, "yoinkhack");
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip("yoinkhack");
		PopupMenu pm = new PopupMenu("yoinkhack");

		MenuItem kill = new MenuItem("Kill");
		kill.addActionListener(e ->
		{
			if (mc.world != null && mc.player != null) mc.player.sendChatMessage("/kill");
		});

		pm.add(kill);

		trayIcon.setPopupMenu(pm);
		try
		{
			tray.add(trayIcon);
		}
		catch (Exception ignored)
		{
		}
	}
}
