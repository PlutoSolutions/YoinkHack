package cat.yoink.yoinkhack.api;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.buttons.ButtonManager;
import cat.yoink.yoinkhack.api.command.CommandManager;
import cat.yoink.yoinkhack.api.component.ComponentManager;
import cat.yoink.yoinkhack.api.discord.Discord;
import cat.yoink.yoinkhack.api.discord.RPC;
import cat.yoink.yoinkhack.api.event.EventHandler;
import cat.yoink.yoinkhack.api.friend.FriendManager;
import cat.yoink.yoinkhack.api.gui.button.ButtonGUI;
import cat.yoink.yoinkhack.api.gui.clickgui.ClickGUI;
import cat.yoink.yoinkhack.api.gui.hud.HUDEditor;
import cat.yoink.yoinkhack.api.gui.old.ClickGUINew;
import cat.yoink.yoinkhack.api.gui.old.ClickGUIOld;
import cat.yoink.yoinkhack.api.macro.MacroManager;
import cat.yoink.yoinkhack.api.module.ModuleManager;
import cat.yoink.yoinkhack.api.notification.client.ClientNotificationManager;
import cat.yoink.yoinkhack.api.notification.desktop.DesktopNotificationManager;
import cat.yoink.yoinkhack.api.setting.SettingManager;
import cat.yoink.yoinkhack.api.util.ConfigUtil;
import cat.yoink.yoinkhack.api.util.TPSUtil;
import cat.yoink.yoinkhack.api.util.font.CFontRenderer;
import cat.yoink.yoinkhack.api.waypoint.WaypointManager;
import cat.yoink.yoinkhack.impl.command.Help;

import java.awt.*;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class Loader
{
	public void load()
	{
		Help.load();
		Client.INSTANCE.rpc = new RPC();
		Client.INSTANCE.settingManager = new SettingManager();
		Client.INSTANCE.moduleManager = new ModuleManager();
		Client.INSTANCE.commandManager = new CommandManager();
		Client.INSTANCE.componentManager = new ComponentManager();
		Client.INSTANCE.macroManager = new MacroManager();
		Client.INSTANCE.discord = new Discord();
		Client.INSTANCE.eventHandler = new EventHandler();
		Client.INSTANCE.friendManager = new FriendManager();
		Client.INSTANCE.waypointManager = new WaypointManager();
		Client.INSTANCE.buttonManager = new ButtonManager();
		try
		{
			Client.INSTANCE.desktopNotificationManager = new DesktopNotificationManager();
		}
		catch (Exception ignored)
		{
		}
		Client.INSTANCE.clientNotificationManager = new ClientNotificationManager();
		Client.INSTANCE.clickGUI = new ClickGUI();
		Client.INSTANCE.hudEditor = new HUDEditor();
		Client.INSTANCE.clickGUIOld = new ClickGUIOld();
		Client.INSTANCE.buttonGUI = new ButtonGUI();
		Client.INSTANCE.clickGUINew = new ClickGUINew();
		TPSUtil.INSTANCE = new TPSUtil();
		Client.INSTANCE.discord.start();
		Client.INSTANCE.configUtil = new ConfigUtil();
		Client.INSTANCE.cFontRenderer = new CFontRenderer(new Font("Verdana", Font.PLAIN, 19), true, false);
		Client.INSTANCE.configUtil.loadConfig();
		Runtime.getRuntime().addShutdownHook(Client.INSTANCE.configUtil);
	}
}
