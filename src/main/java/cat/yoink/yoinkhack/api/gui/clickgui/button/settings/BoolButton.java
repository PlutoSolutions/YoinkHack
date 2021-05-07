package cat.yoink.yoinkhack.api.gui.clickgui.button.settings;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.gui.clickgui.button.SettingButton;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.font.FontUtil;

import java.awt.*;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class BoolButton extends SettingButton
{
	private final Setting setting;

	public BoolButton(Module module, Setting setting, int X, int Y, int W, int H)
	{
		super(module, X, Y, W, H);
		this.setting = setting;
	}

	@Override
	public void render(int mX, int mY)
	{
		drawButton();

		if (setting.getBoolValue())
		{
			drawButton(mX, mY);
			FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), -1);
		}
		else
		{
			if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
			{
				Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(25, 25, 25, 200).getRGB(), new Color(15, 15, 15, 200).getRGB());
			}
			else
			{
				Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(35, 35, 35, 200).getRGB(), new Color(25, 25, 25, 200).getRGB());
			}

			FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(127, 127, 127, 255).getRGB());
		}
	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY) && (mB == 0 || mB == 1))
		{
			setting.setBoolValue(!setting.getBoolValue());
		}
	}
}
