package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Fullbright extends Module
{
	private final Setting mode = new Setting("Mode", this, "Gamma", new ArrayList<>(Arrays.asList("Gamma", "Potion")));

	public Fullbright(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(mode);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mode.getEnumValue().equals("Gamma"))
		{
			mc.gameSettings.gammaSetting = 100f;
		}
		else
		{
			mc.gameSettings.gammaSetting = 1f;
			mc.player.addPotionEffect(new PotionEffect(Objects.requireNonNull(Potion.getPotionById(16)), 1, 0));
		}
	}

	@Override
	public void onDisable()
	{
		mc.gameSettings.gammaSetting = 1f;
	}
}
