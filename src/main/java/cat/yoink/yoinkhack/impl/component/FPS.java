package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.Minecraft;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class FPS extends Component
{
	public FPS(String name)
	{
		super(name);
	}

	@Override
	public void render()
	{
		String text = "\u00A77FPS \u00A7r" + Minecraft.getDebugFPS();

		setWidth(FontUtil.getStringWidth(text));
		setHeight(FontUtil.getFontHeight());

		FontUtil.drawStringWithShadow("\u00A77FPS \u00A7r" + Minecraft.getDebugFPS(), getX(), getY(), 0xffffffff);
	}
}
