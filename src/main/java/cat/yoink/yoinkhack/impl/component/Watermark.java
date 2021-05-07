package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.util.font.FontUtil;

import java.awt.*;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class Watermark extends Component
{
	private float hue = 0f;

	public Watermark(String name)
	{
		super(name);

		setWidth(FontUtil.getStringWidth("yoinkhack"));
		setHeight(FontUtil.getFontHeight());
	}

	@Override
	public void render()
	{

		boolean rainbow = Client.INSTANCE.settingManager.getSettingEasy(getName(), 3).getBoolValue();

		if (rainbow)
		{
			int rgb = Color.HSBtoRGB(hue, 1, 1);

			hue += 0.001f;

			FontUtil.drawStringWithShadow("yoinkhack", getX(), getY(), rgb);
		}
		else
		{
			int red = Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getIntValue();
			int green = Client.INSTANCE.settingManager.getSettingEasy(getName(), 1).getIntValue();
			int blue = Client.INSTANCE.settingManager.getSettingEasy(getName(), 2).getIntValue();

			FontUtil.drawStringWithShadow("yoinkhack", getX(), getY(), new Color(red, green, blue, 255).getRGB());
		}
	}
}
