package cat.yoink.yoinkhack.impl.module.misc;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class DesktopNotify extends Module
{
	private final Setting chatMention = new Setting("ChatMention", this, true);
	private final Setting visualRange = new Setting("VisualRange", this, true);
	private final Setting disconnect = new Setting("Disconnect", this, true);
	private final Setting damage = new Setting("Damage", this, true);

	private final ArrayList<EntityPlayer> players = new ArrayList<>();

	public DesktopNotify(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(chatMention);
		addSetting(visualRange);
		addSetting(disconnect);
		addSetting(damage);
	}


	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null || !visualRange.getBoolValue() || Display.isActive()) return;

		for (Entity entity : mc.world.loadedEntityList)
		{
			if (entity instanceof EntityPlayer && !entity.getName().equals(mc.player.getName()) && !players.contains(entity))
			{
				players.add((EntityPlayer) entity);
				Client.INSTANCE.desktopNotificationManager.trayIcon.displayMessage("Visual Range", entity.getName() + " has entered visual range", TrayIcon.MessageType.INFO);
			}
		}

		players.removeIf(player -> !mc.world.getLoadedEntityList().contains(player));
	}

	@SubscribeEvent
	public void onDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		if (mc.player == null || mc.world == null || !disconnect.getBoolValue() || Display.isActive()) return;

		Client.INSTANCE.desktopNotificationManager.trayIcon.displayMessage("Disconnected", "You have been disconnected from the server", TrayIcon.MessageType.INFO);
	}


	@SubscribeEvent
	public void hurtEvent(LivingHurtEvent event)
	{
		if (mc.player == null || mc.world == null || !damage.getBoolValue() || Display.isActive()) return;

		if (event.getEntity() instanceof EntityPlayer && event.getEntity().getName().equals(mc.player.getName()))
		{
			Client.INSTANCE.desktopNotificationManager.trayIcon.displayMessage("Taking damage", "You have just taken damage", TrayIcon.MessageType.WARNING);
		}
	}

	@SubscribeEvent
	public void onMessageReceived(ClientChatReceivedEvent event)
	{
		if (mc.player == null || mc.world == null || !chatMention.getBoolValue() || Display.isActive()) return;

		if (!Display.isActive() && event.getMessage().getUnformattedText().contains(mc.player.getName()))
		{
			Client.INSTANCE.desktopNotificationManager.trayIcon.displayMessage("Chat Mention", event.getMessage().getUnformattedText(), TrayIcon.MessageType.NONE);
		}
	}
}
