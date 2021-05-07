package cat.yoink.yoinkhack.api.gui.clickgui.button.settings;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.gui.clickgui.button.SettingButton;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.font.FontUtil;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class SliderButton extends SettingButton
{
	private final Setting setting;
	protected boolean dragging;
	protected int sliderWidth;

	SliderButton(Module module, Setting setting, int X, int Y, int W, int H)
	{
		super(module, X, Y, W, H);
		this.dragging = false;
		this.sliderWidth = 0;
		this.setting = setting;
	}

	protected void updateSlider(int mouseX)
	{
	}

	@Override
	public void render(int mX, int mY)
	{
		updateSlider(mX);

		drawButton();

		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			Client.INSTANCE.clickGUI.drawGradient(getX() + 3 + (sliderWidth), getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(25, 25, 25, 200).getRGB(), new Color(15, 15, 15, 200).getRGB());
			switch (Client.INSTANCE.settingManager.getSettingEasy("ClickGUI", 1).getEnumValue())
			{
				case "Red":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() - 2 + (sliderWidth) + 5, getY() + getH(), new Color(133, 20, 20, 232).getRGB(), new Color(112, 20, 20, 232).getRGB());
					break;
				case "Green":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() - 2 + (sliderWidth) + 5, getY() + getH(), new Color(20, 133, 20, 232).getRGB(), new Color(20, 112, 20, 232).getRGB());
					break;
				case "Blue":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() - 2 + (sliderWidth) + 5, getY() + getH(), new Color(20, 20, 133, 232).getRGB(), new Color(20, 20, 112, 232).getRGB());
					break;
				default:
					break;
			}
		}
		else
		{
			Client.INSTANCE.clickGUI.drawGradient(getX() + 3 + (sliderWidth), getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(35, 35, 35, 200).getRGB(), new Color(25, 25, 25, 200).getRGB());
			switch (Client.INSTANCE.settingManager.getSettingEasy("ClickGUI", 1).getEnumValue())
			{
				case "Red":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() - 2 + (sliderWidth) + 5, getY() + getH(), new Color(155, 20, 20, 232).getRGB(), new Color(134, 20, 20, 232).getRGB());
					break;
				case "Green":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() - 2 + (sliderWidth) + 5, getY() + getH(), new Color(20, 155, 20, 232).getRGB(), new Color(20, 134, 20, 232).getRGB());
					break;
				case "Blue":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() - 2 + (sliderWidth) + 5, getY() + getH(), new Color(20, 20, 155, 232).getRGB(), new Color(20, 20, 134, 232).getRGB());
					break;
				default:
					break;
			}
		}


		FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
		FontUtil.drawStringWithShadow(String.valueOf(setting.getIntValue()), (float) ((getX() + getW() - 6) - FontUtil.getStringWidth(String.valueOf(setting.getIntValue()))), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());

	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			dragging = true;
		}
	}

	@Override
	public void mouseUp(int mouseX, int mouseY)
	{
		dragging = false;
	}

	@Override
	public void close()
	{
		dragging = false;
	}

	public static class IntSlider extends SliderButton
	{
		private final Setting intSetting;

		public IntSlider(Module module, Setting setting, int X, int Y, int W, int H)
		{
			super(module, setting, X, Y, W, H);
			intSetting = setting;
		}

		@Override
		protected void updateSlider(final int mouseX)
		{
			final double diff = Math.min(getW(), Math.max(0, mouseX - getX()));
			final double min = intSetting.getIntMinValue();
			final double max = intSetting.getIntMaxValue();
			sliderWidth = (int) ((getW() - 6) * (intSetting.getIntValue() - min) / (max - min));
			if (dragging)
			{
				if (diff == 0.0)
				{
					intSetting.setIntValue(intSetting.getIntMinValue());
				}
				else
				{
					final DecimalFormat format = new DecimalFormat("##");
					final String newValue = format.format(diff / getW() * (max - min) + min);
					intSetting.setIntValue(Integer.parseInt(newValue));
				}
			}
		}
	}
}
