package cat.yoink.yoinkhack.impl.module.player;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class FastUse extends Module
{
	public FastUse(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;
// TODO: 8/31/2020 make this using mixin
		ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 0, "rightClickDelayTimer", "field_71467_ac");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDisable()
	{
		ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 4, "rightClickDelayTimer", "field_71467_ac");
	}
}
