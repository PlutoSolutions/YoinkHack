package cat.yoink.yoinkhack.impl.module.misc;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.notification.client.Notification;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.PopEvent;
import cat.yoink.yoinkhack.impl.event.ToggleEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class ClientNotify extends Module
{
	private final Setting time = new Setting("Time", this, 1, 7, 20);
	private final Setting totemPop = new Setting("TotemPop", this, true);
	private final Setting strength = new Setting("Strength", this, true);
	private final Setting visualRange = new Setting("VisualRange", this, true);
	private final Setting moduleToggle = new Setting("ModuleToggle", this, true);
	private final Setting reset = new Setting("Reset", this, false);

	private final HashMap<String, Integer> popList = new HashMap<>();
	private final ArrayList<EntityPlayer> rangePlayers = new ArrayList<>();
	private final Set<EntityPlayer> strengthPlayers = Collections.newSetFromMap(new WeakHashMap<>());

	// TODO: 8/30/2020 make component
	
	public ClientNotify(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(time);
		addSetting(totemPop);
		addSetting(strength);
		addSetting(visualRange);
		addSetting(moduleToggle);
		addSetting(reset);
	}

	@SubscribeEvent
	public void onPop(PopEvent event)
	{
		if (mc.player == null || mc.world == null || !totemPop.getBoolValue()) return;

		if (popList.get(event.getEntity().getName()) == null)
		{
			popList.put(event.getEntity().getName(), 1);
			showNotification(event.getEntity().getName() + " popped 1 totem!");
		}
		else if (popList.get(event.getEntity().getName()) != null)
		{
			int popCounter = popList.get(event.getEntity().getName());
			int newPopCounter = popCounter + 1;
			popList.put(event.getEntity().getName(), newPopCounter);
			showNotification(event.getEntity().getName() + " popped " + newPopCounter + " totems!");
		}
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (reset.getBoolValue())
		{
			reset.setBoolValue(false);
			reset();
		}

		if (strength.getBoolValue())
		{
			for (EntityPlayer player : mc.world.playerEntities)
			{
				if (player.isPotionActive(MobEffects.STRENGTH) && !this.strengthPlayers.contains(player))
				{
					showNotification(String.format("%s drank strength.", player.getDisplayNameString()));
					strengthPlayers.add(player);
				}
				if (!this.strengthPlayers.contains(player) || player.isPotionActive(MobEffects.STRENGTH)) continue;
				showNotification(String.format("%s ran out of strength.", player.getDisplayNameString()));
				strengthPlayers.remove(player);
			}
		}

		if (totemPop.getBoolValue())
		{
			for (EntityPlayer player : mc.world.playerEntities)
			{
				if (player.getHealth() <= 0 && popList.containsKey(player.getName()))
				{
					showNotification(player.getName() + " died after popping " + popList.get(player.getName()) + " totems!");
					popList.remove(player.getName(), popList.get(player.getName()));
				}
			}
		}


		if (!visualRange.getBoolValue()) return;

		List<EntityPlayer> tickPlayerList = new ArrayList<>(mc.world.playerEntities);

		for (EntityPlayer player : tickPlayerList)
		{
			if (!player.getName().equals(mc.player.getName()) && !rangePlayers.contains(player))
			{
				rangePlayers.add(player);
				showNotification(String.format("%s has entered visual range at %s%d %d %d", player.getName(), ChatFormatting.GRAY, Math.round(player.posX), Math.round(player.posY), Math.round(player.posZ)));
			}
		}

		ArrayList<EntityPlayer> p = new ArrayList<>();


		for (EntityPlayer player : rangePlayers)
		{
			if (!tickPlayerList.contains(player))
			{
				p.add(player);
				showNotification(String.format("%s has left visual range at %s%d %d %d", player.getName(), ChatFormatting.GRAY, Math.round(player.posX), Math.round(player.posY), Math.round(player.posZ)));
			}
		}

		for (EntityPlayer player : p)
		{
			rangePlayers.remove(player);
		}

		p.clear();
	}

	private void reset()
	{
		for (Notification notification : Client.INSTANCE.clientNotificationManager.getNotifications())
		{
			notification.delete();
		}
	}

	@SubscribeEvent
	public void onToggle(ToggleEvent event)
	{
		if (mc.player == null || mc.world == null || !moduleToggle.getBoolValue()) return;
		showNotification(event.getState() ? String.format("%s %sON", event.getModule().getName(), ChatFormatting.GREEN) : String.format("%s %sOFF", event.getModule().getName(), ChatFormatting.RED));
	}

	private void showNotification(String message)
	{
		Client.INSTANCE.clientNotificationManager.addNotification(message, time.getIntValue() * 100);
	}

	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent event)
	{
		if (!event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT) || mc.player == null || mc.world == null)
		{
			return;
		}

		Client.INSTANCE.clientNotificationManager.renderNotifications();
	}
}
