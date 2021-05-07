package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.LoggerUtil;
import cat.yoink.yoinkhack.impl.event.PopEvent;
import cat.yoink.yoinkhack.impl.event.ToggleEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Announcer extends Module
{
	private final Setting watermark = new Setting("Watermark", this, false);
	private final Setting clientSide = new Setting("ClientSide", this, false);
	private final Setting tickDelay = new Setting("TickDelay", this, 1, 80, 300);
	private final Setting move = new Setting("Move", this, true);
	private final Setting jump = new Setting("Jump", this, true);
	private final Setting sneak = new Setting("Sneak", this, true);
	private final Setting food = new Setting("Food", this, true);
	private final Setting totemPop = new Setting("TotemPop", this, true);
	private final Setting attack = new Setting("Attack", this, true);
	private final Setting moduleToggle = new Setting("ModuleToggle", this, true);

	private int messageCooldown;

	private BlockPos oldPosition;

	public Announcer(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(watermark);
		addSetting(clientSide);
		addSetting(tickDelay);
		addSetting(move);
		addSetting(jump);
		addSetting(sneak);
		addSetting(food);
		addSetting(attack);
		addSetting(totemPop);
		addSetting(moduleToggle);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (messageCooldown != 0) messageCooldown--;

		if (mc.player.ticksExisted % tickDelay.getIntValue() == 0 && move.getBoolValue())
		{
			if (oldPosition == null) oldPosition = new BlockPos(mc.player.getPositionVector());
			int distance = Math.toIntExact(Math.round(mc.player.getDistance(oldPosition.getX(), oldPosition.getY(), oldPosition.getZ())));

			if (distance > 2)
			{
				sendMessage(String.format("I just moved %s blocks", distance));
				oldPosition = mc.player.getPosition();
			}
		}
	}

	@SubscribeEvent
	public void onPop(PopEvent event)
	{
		if (mc.player == null || mc.world == null || !totemPop.getBoolValue()) return;

		if (mc.player.getDistance(event.getEntity()) < 6 && !Client.INSTANCE.friendManager.isFriend(event.getEntity().getName()) && !mc.player.getName().equals(event.getEntity().getName()))
		{
			sendMessage(String.format("I just popped %s", event.getEntity().getName()));
		}
	}

	@SubscribeEvent
	public void onAttack(LivingAttackEvent event)
	{
		if (mc.player == null || mc.world == null || !attack.getBoolValue()) return;

		if (event.getEntity() instanceof EntityPlayer && mc.player.getDistance(event.getEntity()) < 5 && !Client.INSTANCE.friendManager.isFriend(event.getEntity().getName()) && !mc.player.getName().equals(event.getEntity().getName()))
		{
			sendMessage(String.format("I just attacked %s and dealt %s hearts of damage", event.getEntity().getName(), Math.round(event.getAmount() + 1)));
		}
	}

	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event)
	{
		if (mc.player != null && mc.world != null && jump.getBoolValue() && event.getEntity().equals(mc.player))
		{
			sendMessage("I just jumped");
		}
	}

	@SubscribeEvent
	public void onSneak(InputUpdateEvent event)
	{
		if (mc.player != null && mc.world != null && sneak.getBoolValue() && event.getMovementInput().sneak)
		{
			sendMessage("I just sneaked");
		}
	}

	@SubscribeEvent
	public void onUse(LivingEntityUseItemEvent.Finish event)
	{
		if (mc.player != null && mc.world != null && food.getBoolValue() && event.getEntity() == mc.player && (event.getItem().getItem() instanceof ItemFood || event.getItem().getItem() instanceof ItemAppleGold))
		{
			sendMessage(String.format("I just ate a %s", mc.player.getHeldItemMainhand().getDisplayName()));
		}
	}

	@SubscribeEvent
	public void onToggle(ToggleEvent event)
	{
		if (mc.player != null && mc.world != null && moduleToggle.getBoolValue())
		{
			if (event.getState())
			{
				sendMessage(String.format("I just toggled %s on", event.getModule().getName()));
			}
			else
			{
				sendMessage(String.format("I just toggled %s off", event.getModule().getName()));
			}
		}
	}

	private String addWatermark(String message)
	{
		if (watermark.getBoolValue())
		{
			return message + " thanks to yoinkhack!";
		}
		return message + "!";
	}

	private void sendMessage(String message)
	{
		if (messageCooldown != 0) return;

		if (clientSide.getBoolValue())
		{
			LoggerUtil.sendMessage(addWatermark(message));
		}
		else
		{
			mc.player.sendChatMessage(addWatermark(message));
		}

		messageCooldown = tickDelay.getIntValue();
	}

}
