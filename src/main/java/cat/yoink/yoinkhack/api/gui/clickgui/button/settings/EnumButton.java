package cat.yoink.yoinkhack.api.gui.clickgui.button.settings;

import cat.yoink.yoinkhack.api.gui.clickgui.button.SettingButton;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.font.FontUtil;

import java.awt.*;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class EnumButton extends SettingButton
{
	private final Setting setting;

	public EnumButton(Module module, Setting setting, int X, int Y, int W, int H)
	{
		super(module, X, Y, W, H);
		this.setting = setting;
	}


	@Override
	public void render(int mX, int mY)
	{
		drawButton();

		drawButton(mX, mY);

		FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
		FontUtil.drawStringWithShadow(setting.getEnumValue(), (float) ((getX() + getW() - 6) - FontUtil.getStringWidth(setting.getEnumValue())), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());

	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			if (mB == 0)
			{
				int i = 0;
				int enumIndex = 0;
				for (String enumName : setting.getOptions())
				{
					if (enumName.equals(setting.getEnumValue())) enumIndex = i;
					i++;
				}
				if (enumIndex == setting.getOptions().size() - 1)
				{
					setting.setEnumValue(setting.getOptions().get(0));
				}
				else
				{
					enumIndex++;
					i = 0;
					for (String enumName : setting.getOptions())
					{
						if (i == enumIndex) setting.setEnumValue(enumName);
						i++;
					}
				}
			}
			else if (mB == 1)
			{
				int i = 0;
				int enumIndex = 0;
				for (String enumName : setting.getOptions())
				{
					if (enumName.equals(setting.getEnumValue())) enumIndex = i;
					i++;
				}
				if (enumIndex == 0)
				{
					setting.setEnumValue(setting.getOptions().get(setting.getOptions().size() - 1));
				}
				else
				{
					enumIndex--;
					i = 0;
					for (String enumName : setting.getOptions())
					{
						if (i == enumIndex) setting.setEnumValue(enumName);
						i++;
					}
				}
			}
		}
	}
}
