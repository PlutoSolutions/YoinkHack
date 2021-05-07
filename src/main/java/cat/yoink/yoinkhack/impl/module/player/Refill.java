package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.PairUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Refill extends Module
{
	private final Setting threshold = new Setting("Threshold", this, 1, 20, 63);
	private final Setting tickDelay = new Setting("TickDelay", this, 0, 4, 20);
	private int delayStep = 0;

	public Refill(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(tickDelay);
		addSetting(threshold);
	}


	private Map<Integer, ItemStack> getInventory()
	{
		return getInventorySlots(9, 35);
	}

	private Map<Integer, ItemStack> getHotbar()
	{
		return getInventorySlots(36, 44);
	}

	private Map<Integer, ItemStack> getInventorySlots(int current, int last)
	{
		int c = current;
		Map<Integer, ItemStack> fullInventorySlots = new HashMap<>();
		while (c <= last)
		{
			fullInventorySlots.put(c, mc.player.inventoryContainer.getInventory().get(c));
			c++;
		}
		return fullInventorySlots;
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.world == null || mc.player == null || mc.currentScreen instanceof GuiContainer)
		{
			return;
		}

		if (delayStep < tickDelay.getIntValue())
		{
			delayStep++;
			return;
		}
		else
		{
			delayStep = 0;
		}

		PairUtil<Integer, Integer> slots = findReplenishableHotbarSlot();

		if (slots == null)
		{
			return;
		}

		int inventorySlot = slots.getKey();
		int hotbarSlot = slots.getValue();

		mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, mc.player);
		mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, mc.player);
		mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, mc.player);

	}

	private PairUtil<Integer, Integer> findReplenishableHotbarSlot()
	{
		PairUtil<Integer, Integer> returnPair = null;
		for (Map.Entry<Integer, ItemStack> hotbarSlot : getHotbar().entrySet())
		{
			ItemStack stack = hotbarSlot.getValue();
			int inventorySlot = findCompatibleInventorySlot(stack);
			if (stack.isEmpty() || stack.getItem() == Items.AIR || !stack.isStackable() || stack.getCount() >= stack.getMaxStackSize() || stack.getCount() > threshold.getIntValue() || inventorySlot == -1)
			{
				continue;
			}
			returnPair = new PairUtil<>(inventorySlot, hotbarSlot.getKey());

		}
		return returnPair;
	}

	private int findCompatibleInventorySlot(ItemStack hotbarStack)
	{
		int inventorySlot = -1;
		int smallestStackSize = 999;
		for (Map.Entry<Integer, ItemStack> entry : getInventory().entrySet())
		{
			ItemStack inventoryStack = entry.getValue();
			if (inventoryStack.isEmpty() || inventoryStack.getItem() == Items.AIR || !isCompatibleStacks(hotbarStack, inventoryStack))
			{
				continue;
			}
			int currentStackSize = mc.player.inventoryContainer.getInventory().get(entry.getKey()).getCount();
			if (smallestStackSize > currentStackSize)
			{
				smallestStackSize = currentStackSize;
				inventorySlot = entry.getKey();
			}

		}
		return inventorySlot;
	}

	private boolean isCompatibleStacks(ItemStack stack1, ItemStack stack2)
	{
		if (!stack1.getItem().equals(stack2.getItem()) || !stack1.getDisplayName().equals(stack2.getDisplayName()))
		{
			return false;
		}
		return stack1.getItemDamage() == stack2.getItemDamage();
	}
}