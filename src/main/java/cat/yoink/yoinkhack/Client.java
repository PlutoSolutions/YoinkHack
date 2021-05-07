package cat.yoink.yoinkhack;

import cat.yoink.yoinkhack.api.Loader;
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
import cat.yoink.yoinkhack.api.util.font.CFontRenderer;
import cat.yoink.yoinkhack.api.waypoint.WaypointManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * @author yoink
 * @since 8/29/2020
 */
@Mod(modid = "yoinkhack", name = "yoinkhack", version = "3.4-beta2")
public class Client
{
	@Mod.Instance("yoinkhack")
	public static Client INSTANCE;

	public ButtonManager buttonManager;
	public CommandManager commandManager;
	public ComponentManager componentManager;
	public RPC rpc;
	public FriendManager friendManager;
	public ButtonGUI buttonGUI;
	public ClickGUI clickGUI;
	public MacroManager macroManager;
	public ModuleManager moduleManager;
	public DesktopNotificationManager desktopNotificationManager;
	public ClientNotificationManager clientNotificationManager;
	public SettingManager settingManager;
	public WaypointManager waypointManager;
	public HUDEditor hudEditor;
	public ClickGUINew clickGUINew;
	public ClickGUIOld clickGUIOld;
	public Discord discord;
	public EventHandler eventHandler;
	public ConfigUtil configUtil;
	public CFontRenderer cFontRenderer;

	@Mod.EventHandler
	public void initialize(FMLInitializationEvent event)
	{
		(new Loader()).load();
	}
}
