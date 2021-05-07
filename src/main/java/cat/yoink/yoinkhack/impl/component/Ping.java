package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.network.NetworkPlayerInfo;

import java.util.Objects;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Ping extends Component
{
	public Ping(String name)
	{
		super(name);
	}

	@SuppressWarnings("ALL")
	@Override
	public void render()
	{
		if (mc.world == null || mc.player == null || mc.player.getUniqueID() == null) return;

		NetworkPlayerInfo playerInfo = Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID());

		if (playerInfo == null) return;

		String text = "\u00A77Ping \u00A7r" + playerInfo.getResponseTime();

		setWidth(FontUtil.getStringWidth(text));
		setHeight(FontUtil.getFontHeight());

		FontUtil.drawStringWithShadow(text, getX(), getY(), 0xffffffff);
	}
}
