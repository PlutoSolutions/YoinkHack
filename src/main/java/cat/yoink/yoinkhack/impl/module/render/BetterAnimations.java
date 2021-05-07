package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.mixin.mixins.accessor.IItemRenderer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class BetterAnimations extends Module
{

	private final Setting mode = new Setting("Mode", this, "Normal", new ArrayList<>(Arrays.asList("Normal", "Hard")));

	private IAttributeInstance speed;
	private AttributeModifier attribute;

	public BetterAnimations(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(mode);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mode.getEnumValue().equals("Hard"))
		{
			if (!(mc.player.inventory.getCurrentItem().getItem() instanceof ItemSword))
			{
				speed = mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
				attribute = new AttributeModifier("attack speed", 21767.0D, 0);
				speed.applyModifier(attribute);
			}
		}
		else
		{
			if (((IItemRenderer) mc.entityRenderer.itemRenderer).getPrevEquippedProgressMainHand() >= 0.9)
			{
				((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressMainHand(1.0f);
				((IItemRenderer) mc.entityRenderer.itemRenderer).setItemStackMainHand(mc.player.getHeldItem(EnumHand.MAIN_HAND));
			}
			if (((IItemRenderer) mc.entityRenderer.itemRenderer).getPrevEquippedProgressOffHand() >= 0.9)
			{
				((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressOffHand(1.0f);
				((IItemRenderer) mc.entityRenderer.itemRenderer).setItemStackOffHand(mc.player.getHeldItem(EnumHand.OFF_HAND));
			}
		}
	}

	@Override
	public void onDisable()
	{
		try
		{
			speed.removeModifier(attribute);
		}
		catch (Exception ignored)
		{
		}
	}

}
