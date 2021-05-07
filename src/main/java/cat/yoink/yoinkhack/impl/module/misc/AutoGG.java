package cat.yoink.yoinkhack.impl.module.misc;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class AutoGG extends Module
{
	private final Setting timeoutTicks = new Setting("Delay", this, 0, 30, 200);
	private final Setting message = new Setting("Message", this, "EZ", new ArrayList<>(Arrays.asList("GG", "EZ")));
	private final Setting zCounter = new Setting("zCount", this, 1, 3, 100);
	private final Setting watermark = new Setting("Watermark", this, true);
	private final Setting naked = new Setting("Naked", this, false);

	private ConcurrentHashMap<String, Integer> targetedPlayers = new ConcurrentHashMap<>();

	public AutoGG(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(timeoutTicks);
		addSetting(message);
		addSetting(zCounter);
		addSetting(watermark);
		addSetting(naked);
	}

	@Override
	public void onEnable()
	{
		targetedPlayers = new ConcurrentHashMap<>();
	}

	@Override
	public void onDisable()
	{
		targetedPlayers = null;
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (targetedPlayers == null)
		{
			targetedPlayers = new ConcurrentHashMap<>();
		}

		for (EntityPlayer player : mc.world.playerEntities)
		{
			if (player.getHealth() > 0)
			{
				continue;
			}

			if (!isNaked(mc.player) || naked.getBoolValue())
			{
				String name = player.getName();

				if (targetedPlayers.containsKey(name))
				{
					sendMessage(name);
					break;
				}
			}
		}

		targetedPlayers.forEach((name, timeout) ->
		{
			if (timeout <= 0)
			{
				targetedPlayers.remove(name);
			}
			else
			{
				targetedPlayers.put(name, timeout - 1);
			}
		});

	}

	private boolean isNaked(EntityPlayerSP player)
	{
		return isEmpty(0, player) && isEmpty(1, player) && isEmpty(2, player) && isEmpty(3, player);
	}

	public boolean isEmpty(int slotNumber, EntityPlayer player)
	{
		return player.inventory.armorInventory.get(slotNumber).isEmpty();
	}

	@SubscribeEvent
	public void onPacket(PacketEvent.Send event)
	{

		if (mc.player == null || mc.world == null || !(event.getPacket() instanceof CPacketUseEntity)) return;

		CPacketUseEntity cPacketUseEntity = ((CPacketUseEntity) event.getPacket());

		if (!(cPacketUseEntity.getAction().equals(CPacketUseEntity.Action.ATTACK))) return;

		Entity targetEntity = cPacketUseEntity.getEntityFromWorld(mc.world);

		if (targetEntity instanceof EntityPlayer) addTargetedPlayer(targetEntity.getName());

	}

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		EntityLivingBase entity = event.getEntityLiving();

		if (!(entity instanceof EntityPlayer)) return;

		EntityPlayer player = (EntityPlayer) entity;

		if (player.getHealth() > 0) return;

		String name = player.getName();
		if (targetedPlayers.containsKey(name)) sendMessage(name);
	}

	private void sendMessage(String name)
	{
		targetedPlayers.remove(name);

		StringBuilder text = new StringBuilder();

		if (message.getEnumValue().equals("GG"))
		{
			text.append("GG ").append(name);
		}
		else
		{
			text.append("E");

			for (int i = 0; i < zCounter.getIntValue(); i++)
			{
				text.append("Z");
			}

			text.append(" ").append(name);
		}

		if (watermark.getBoolValue()) text.append("! yoinkhack on top");
		text.append("!");

		mc.player.connection.sendPacket(new CPacketChatMessage(text.toString()));
	}

	public void addTargetedPlayer(String name)
	{
		if (name.equals(mc.player.getName())) return;
		targetedPlayers.put(name, timeoutTicks.getIntValue());
	}
}