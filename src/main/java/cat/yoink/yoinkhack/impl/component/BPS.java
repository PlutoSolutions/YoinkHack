package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author yoink
 * @since 9/2/2020
 */
public class BPS extends Component
{
	private float bps = 0;

	public BPS(String name)
	{
		super(name);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;
		bps = Math.round(mc.player.getDistance(mc.player.lastTickPosX, mc.player.posY, mc.player.lastTickPosZ) * 200) / 10f;
	}

	@Override
	public void render()
	{
		String text = "\u00A77BPS \u00A7r" + bps;

		setWidth(FontUtil.getStringWidth(text));
		setHeight(FontUtil.getFontHeight());

		FontUtil.drawStringWithShadow(text, getX(), getY(), 0xffffffff);
	}
}
