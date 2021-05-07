package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Offhand extends Module
{
	private final Setting itemAuto = new Setting("AutoItem", this, "Crystal", new ArrayList<>(Arrays.asList("Gapple", "Crystal")));
	private final Setting itemManual = new Setting("ManItem", this, "Totem", new ArrayList<>(Arrays.asList("Gapple", "Crystal", "Totem")));
	private final Setting mode = new Setting("Mode", this, "Manual", new ArrayList<>(Arrays.asList("Manual", "Automatic")));
	private final Setting health = new Setting("Health", this, 1, 10, 25);

	private boolean movingAuto = false;
	private boolean returnAuto = false;
	private int timer;

	public Offhand(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(mode);
		addSetting(itemAuto);
		addSetting(itemManual);
		addSetting(health);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		switch (mode.getEnumValue().toLowerCase())
		{
			case "automatic":
				tickAuto();
				break;
			case "manual":
				tickManual();
				break;
			default:
				break;
		}
	}


	private void tickAuto()
	{
		Item autoItem = itemAuto.getEnumValue().equalsIgnoreCase("Gapple") ? Items.GOLDEN_APPLE : Items.END_CRYSTAL;

		if (returnAuto)
		{
			int t = -1;
			for (int i = 0; i < 45; i++)
			{
				if (mc.player.inventory.getStackInSlot(i).isEmpty())
				{
					t = i;
					break;
				}
			}

			if (t == -1) return;
			mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
			returnAuto = false;

		}

		int autoTotems = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
		int autoCrystals = mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == autoItem).mapToInt(ItemStack::getCount).sum();

		if ((!shouldTotem() || mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) && (shouldTotem() || mc.player.getHeldItemOffhand().getItem() != autoItem))
		{
			if (movingAuto)
			{
				mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
				movingAuto = false;
				returnAuto = true;
				return;
			}

			if (mc.player.inventory.getItemStack().isEmpty())
			{
				if (!shouldTotem() && mc.player.getHeldItemOffhand().getItem() == autoItem) return;
				if (shouldTotem() && mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) return;
				if (!shouldTotem())
				{
					if (autoCrystals == 0) return;
					int t = -1;
					for (int i = 0; i < 45; i++)
					{
						if (mc.player.inventory.getStackInSlot(i).getItem() == autoItem)
						{
							t = i;
							break;
						}
					}

					if (t == -1) return;
					mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);
				}
				else
				{

					if (autoTotems == 0) return;
					int t = -1;
					for (int i = 0; i < 45; i++)
					{
						if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING)
						{
							t = i;
							break;
						}
					}

					if (t == -1) return;
					mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);

				}

				movingAuto = true;

			}
			else
			{
				int t = -1;
				for (int i = 0; i < 45; i++)
				{
					if (mc.player.inventory.getStackInSlot(i).isEmpty())
					{
						t = i;
						break;
					}
				}

				if (t == -1) return;
				mc.playerController.windowClick(0, t < 9 ? t + 36 : t, 0, ClickType.PICKUP, mc.player);

			}
		}
	}


	private void tickManual()
	{
		Item manualItem = null;
		switch (itemManual.getEnumValue().toLowerCase())
		{
			case "totem":
				manualItem = Items.TOTEM_OF_UNDYING;
				break;
			case "crystal":
				manualItem = Items.END_CRYSTAL;
				break;
			case "gapple":
				manualItem = Items.GOLDEN_APPLE;
				break;
			default:
				break;
		}

		if (timer > 0)
		{
			timer--;
			return;
		}

		NonNullList<ItemStack> inv;
		ItemStack offhand = mc.player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);

		int inventoryIndex;
		inv = mc.player.inventory.mainInventory;
		for (inventoryIndex = 0; inventoryIndex < inv.size(); inventoryIndex++)
		{
			if (inv.get(inventoryIndex) != ItemStack.EMPTY && offhand.getItem() != manualItem && inv.get(inventoryIndex).getItem() == manualItem)
			{
				replaceOffhand(inventoryIndex);
				break;
			}
			timer = 2;
		}
	}


	private void replaceOffhand(int inventoryIndex)
	{
		if (mc.player.openContainer instanceof ContainerPlayer)
		{
			mc.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, mc.player);
			mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
			mc.playerController.windowClick(0, inventoryIndex < 9 ? inventoryIndex + 36 : inventoryIndex, 0, ClickType.PICKUP, mc.player);
		}
	}

	private boolean shouldTotem()
	{
		boolean hp = (mc.player.getHealth() + mc.player.getAbsorptionAmount()) <= health.getIntValue();
		boolean endCrystal = !isCrystalsAABBEmpty();

		return (hp || endCrystal);
	}

	private boolean isEmpty(BlockPos pos)
	{

		List<Entity> crystalsInAABB = mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)).stream()
				.filter(e -> e instanceof EntityEnderCrystal)
				.collect(Collectors.toList());

		return crystalsInAABB.isEmpty();
	}

	private boolean isCrystalsAABBEmpty()
	{
		return isEmpty(mc.player.getPosition().add(1, 0, 0)) &&
				isEmpty(mc.player.getPosition().add(-1, 0, 0)) &&
				isEmpty(mc.player.getPosition().add(0, 0, 1)) &&
				isEmpty(mc.player.getPosition().add(0, 0, -1)) &&
				isEmpty(mc.player.getPosition());
	}

}

