package cat.yoink.yoinkhack.api.gui.clickgui.button;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.gui.clickgui.IComponent;
import cat.yoink.yoinkhack.api.gui.clickgui.button.settings.BindButton;
import cat.yoink.yoinkhack.api.gui.clickgui.button.settings.BoolButton;
import cat.yoink.yoinkhack.api.gui.clickgui.button.settings.EnumButton;
import cat.yoink.yoinkhack.api.gui.clickgui.button.settings.SliderButton;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ModuleButton implements IComponent
{
	private final Minecraft mc = Minecraft.getMinecraft();
	private final Module module;
	private final ArrayList<SettingButton> buttons = new ArrayList<>();
	private final int W;
	private final int H;
	private int X;
	private int Y;
	private boolean open;
	private int showingModuleCount;
	private boolean opening;
	private boolean closing;

	public ModuleButton(Module module, int x, int y, int w, int h)
	{
		this.module = module;
		X = x;
		Y = y;
		W = w;
		H = h;

		int n = 0;
		for (Setting setting : Client.INSTANCE.settingManager.getSettingsByModule(module))
		{
			SettingButton settingButton = null;

			if (setting.isBoolean())
			{
				settingButton = new BoolButton(module, setting, X, Y + H + n, W, H);
			}
			else if (setting.isInteger())
			{
				settingButton = new SliderButton.IntSlider(module, setting, X, Y + H + n, W, H);
			}
			else if (setting.isEnum()) settingButton = new EnumButton(module, setting, X, Y + H + n, W, H);

			if (settingButton != null)
			{
				buttons.add(settingButton);

				n += H;
			}

		}

		buttons.add(new BindButton(module, X, Y + H + n, W, H));
	}

	@Override
	public void render(int mX, int mY)
	{
		Color darkOutlineColor = new Color(7, 7, 7, 255);

		Gui.drawRect(X, Y, X + 2, Y + H + 2, darkOutlineColor.getRGB());
		Gui.drawRect(X, Y, X + W, Y + 1, darkOutlineColor.getRGB());
		Gui.drawRect(X + W, Y, X + W - 2, Y + H + 2, darkOutlineColor.getRGB());
		Gui.drawRect(X, Y + H, X + W, Y + H + 1, darkOutlineColor.getRGB());

		if (module.isEnabled())
		{
			if (isHover(X, Y, W, H - 1, mX, mY))
			{
				switch (Client.INSTANCE.settingManager.getSettingEasy("ClickGUI", 1).getEnumValue())
				{
					case "Red":
						Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(133, 20, 20, 232).getRGB(), new Color(112, 20, 20, 232).getRGB());
						break;
					case "Green":
						Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(20, 133, 20, 232).getRGB(), new Color(20, 112, 20, 232).getRGB());
						break;
					case "Blue":
						Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(20, 20, 133, 232).getRGB(), new Color(20, 20, 112, 232).getRGB());
						break;
					default:
						break;
				}
			}
			else
			{
				switch (Client.INSTANCE.settingManager.getSettingEasy("ClickGUI", 1).getEnumValue())
				{
					case "Red":
						Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(155, 20, 20, 232).getRGB(), new Color(134, 20, 20, 232).getRGB());
						break;
					case "Green":
						Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(20, 155, 20, 232).getRGB(), new Color(20, 134, 20, 232).getRGB());
						break;
					case "Blue":
						Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(20, 20, 155, 232).getRGB(), new Color(20, 20, 155, 232).getRGB());
						break;
					default:
						break;
				}
			}
			FontUtil.drawStringWithShadow(module.getName(), (float) (X + 5), (float) (Y + 4), -1);
		}
		else
		{
			if (isHover(X, Y, W, H - 1, mX, mY))
			{
				Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(25, 25, 25, 200).getRGB(), new Color(15, 15, 15, 200).getRGB());
			}
			else
			{
				Client.INSTANCE.clickGUI.drawGradient(X + 2, Y + 1, X + W - 2, Y + H, new Color(35, 35, 35, 200).getRGB(), new Color(25, 25, 25, 200).getRGB());
			}

			FontUtil.drawStringWithShadow(module.getName(), (float) (X + 5), (float) (Y + 4), new Color(127, 127, 127, 255).getRGB());
		}

		if (Client.INSTANCE.moduleManager.moduleHasSettings(module))
		{
			if (module.isEnabled())
			{
				FontUtil.drawStringWithShadow("...", (float) (X + W - FontUtil.getStringWidth("...") - 4), (float) (Y + 4), new Color(255, 255, 255, 255).getRGB());
			}
			else
			{
				FontUtil.drawStringWithShadow("...", (float) (X + W - FontUtil.getStringWidth("...") - 4), (float) (Y + 4), new Color(127, 127, 127, 255).getRGB());
			}
		}

		if (opening)
		{
			showingModuleCount++;
			if (showingModuleCount == buttons.size())
			{
				opening = false;
				open = true;
			}
		}

		if (closing)
		{
			showingModuleCount--;
			if (showingModuleCount == 0)
			{
				closing = false;
				open = false;
			}
		}


		if (Client.INSTANCE.settingManager.getSettingEasy("ClickGUI", 2).getBoolValue() && isHover(X, Y, W, H - 1, mX, mY) && module.getDescription() != null && !module.getDescription().equals(""))
		{
			FontUtil.drawStringWithShadow(module.getDescription(), 2, (new ScaledResolution(mc).getScaledHeight() - FontUtil.getFontHeight() - 2), new Color(0xF2C4C4C4, true).getRGB());
		}
	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
		if (isHover(X, Y, W, H - 1, mX, mY))
		{
			if (mB == 0)
			{
				module.toggle();
				if (module.getName().equals("ClickGUI"))
				{
					mc.displayGuiScreen(null);
				}
			}
			else if (mB == 1)
			{
				processRightClick();
			}
		}

		if (open)
		{
			for (SettingButton settingButton : buttons)
			{
				settingButton.mouseDown(mX, mY, mB);
			}
		}
	}

	@Override
	public void mouseUp(int mX, int mY)
	{
		for (SettingButton settingButton : buttons)
		{
			settingButton.mouseUp(mX, mY);
		}
	}

	@Override
	public void keyPress(int key)
	{
		for (SettingButton settingButton : buttons)
		{
			settingButton.keyPress(key);
		}
	}

	@Override
	public void close()
	{
		for (SettingButton button : buttons)
		{
			button.close();
		}
	}

	private boolean isHover(int X, int Y, int W, int H, int mX, int mY)
	{
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}


	public void setX(int x)
	{
		X = x;
	}

	public void setY(int y)
	{
		Y = y;
	}

	public boolean isOpen()
	{
		return open;
	}

	public Module getModule()
	{
		return module;
	}

	public ArrayList<SettingButton> getButtons()
	{
		return buttons;
	}

	public int getShowingModuleCount()
	{
		return showingModuleCount;
	}

	public boolean isOpening()
	{
		return opening;
	}

	public boolean isClosing()
	{
		return closing;
	}

	public void processRightClick()
	{
		if (!open)
		{
			showingModuleCount = 0;
			opening = true;
		}
		else
		{
			showingModuleCount = buttons.size();
			closing = true;
		}
	}
}
