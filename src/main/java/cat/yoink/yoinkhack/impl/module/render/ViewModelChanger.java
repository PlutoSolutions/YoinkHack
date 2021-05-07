package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class ViewModelChanger extends Module
{
	private final Setting noHands = new Setting("NoHands", this, false);
	private final Setting swingProgress = new Setting("SwingProgress", this, 0, 68, 100);
	private final Setting FOV = new Setting("FOV", this, 0, 120, 180);

	public ViewModelChanger(String name, Category category, String description)
	{
		super(name, category, description);

		Setting noEatAnimation = new Setting("NoEatAnim", this, false);

		addSetting(noHands);
		addSetting(noEatAnimation);
		addSetting(swingProgress);
		addSetting(FOV);
		Setting xr = new Setting("XRight", this, -20, 0, 20);
		addSetting(xr);
		Setting yr = new Setting("YRight", this, -20, 2, 20);
		addSetting(yr);
		Setting zr = new Setting("ZRight", this, -20, -12, 20);
		addSetting(zr);
		Setting xl = new Setting("XLeft", this, -20, 0, 20);
		addSetting(xl);
		Setting yl = new Setting("YLeft", this, -20, 2, 20);
		addSetting(yl);
		Setting zl = new Setting("ZLeft", this, -20, -12, 20);
		addSetting(zl);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		mc.gameSettings.fovSetting = FOV.getIntValue();
	}

	@SubscribeEvent
	public void onRenderHand(RenderHandEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (noHands.getBoolValue()) event.setCanceled(true);

		if (swingProgress.getIntValue() != 0 || swingProgress.getIntValue() != 100)
		{
			mc.player.swingProgress = swingProgress.getIntValue() / 100f;
		}
	}

}
