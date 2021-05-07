package cat.yoink.yoinkhack.api.gui.old;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ClickGUINew extends GuiScreen /*implements Client.INSTANCE*/
{
	private boolean B = false;
	private boolean D = false;
	private Category draggingCategory = null;
	private int xB = 0;
	private int yB = 0;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawCategories(mouseX, mouseY);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		processClick(mouseX, mouseY, mouseButton);
	}

	public void drawCategories(int mX, int mY)
	{
		for (Category category : Category.values())
		{
			if (D && draggingCategory.equals(category))
			{
				category.setX(mX - xB);
				category.setY(mY - yB);
			}
			drawCategory(category);
		}
	}

	public void drawCategory(Category category)
	{

		// Draws the Category Box
		ClickUtil.drawBox(category.getX(), category.getY(), 80, ClickUtil.getCategoryHeight(category), 5, 30, 30, 30, 255);

		// Draws the Category Name
		FontUtil.drawString(category.getName(), category.getX() + 40 - FontUtil.getStringWidth(category.getName()) / 2f, category.getY() + 1, 0xffffffff);

		// Draws the separator
		ClickUtil.drawBox(category.getX() + 8, category.getY() + 16, 64, 1, 1, 255, 255, 255, 255);

		drawModules(category);
	}

	public void drawModules(Category category)
	{
		int H = 25;
		for (Module module : Client.INSTANCE.moduleManager.getModulesByCategory(category))
		{
			drawModule(category, module, H);
			H += 20;
		}
	}

	public void drawModule(Category category, Module module, int H)
	{

		int color;
		if (module.isEnabled())
		{
			color = 0xffffffff;
		}
		else
		{
			color = 0xff999999;
		}

		// Draw module Strings
		FontUtil.drawString(module.getName(), category.getX() + 40 - FontUtil.getStringWidth(module.getName()) / 2f, category.getY() + H, color);

		if (module.isExpanded())
		{
			// Draw settings for module
			drawSettings(module, category.getX() + 80 + 12, category.getY() + H);
		}
	}

	public void drawSettings(Module module, int X, int Y)
	{

		// Setting count + 1(bind)
		int i = Client.INSTANCE.settingManager.getSettingsByModule(module).size() + 1;

		// Draw setting box
		ClickUtil.drawBox(X, Y, 100, i * 12, 5, 30, 30, 30, 255);

		int H = Y + 2;

		for (Setting setting : Client.INSTANCE.settingManager.getSettingsByModule(module))
		{
			// Draw each setting in module
			drawSetting(setting, X, H);
			H += 12;
		}

		if (!module.isBinding())
		{
			String keyName;

			try
			{
				keyName = Keyboard.getKeyName(module.getBind());
			}
			catch (Exception ignored)
			{
				keyName = "NONE";
			}
			// if not listening
			FontUtil.drawString("Bind", X + 2, H, 0xffffffff);
			FontUtil.drawString(keyName, X + 100 - FontUtil.getStringWidth(keyName) - 2, H, 0xffffffff);
		}
		else
		{
			// if Listening
			FontUtil.drawString("Key...", X + 2, H, 0xffffffff);
		}
	}

	public void drawSetting(Setting setting, int X, int Y)
	{
		String settingValue = "";

		if (setting.isBoolean()) settingValue = String.valueOf(setting.getBoolValue());
		if (setting.isInteger()) settingValue = String.valueOf(setting.getIntValue());
		if (setting.isEnum()) settingValue = String.valueOf(setting.getEnumValue());

		// Draw setting and value
		FontUtil.drawString(ClickUtil.capFirstLetter(setting.getName()), X + 2, Y, 0xffffffff);
		FontUtil.drawString(ClickUtil.capFirstLetter(settingValue), X + 100 - FontUtil.getStringWidth(settingValue) - 2, Y, 0xffffffff);
	}

	public void processClick(int mX, int mY, int mB)
	{
		for (Category category : Category.values())
		{
			if (ClickUtil.isHover(category.getX(), category.getY(), 80, 16, mX, mY))
			{
				draggingCategory = category;
				D = true;
				xB = mX - category.getX();
				yB = mY - category.getY();
			}
			int H = 25;
			for (Module module : Client.INSTANCE.moduleManager.getModulesByCategory(category))
			{
				if (ClickUtil.isHover(category.getX() + 5, category.getY() + H - 5, 75, FontUtil.getFontHeight() + 5, mX, mY))
				{
					switch (mB)
					{
						case 0:
							module.toggle();
							break;
						case 1:
							for (Module mod : Client.INSTANCE.moduleManager.getModulesByCategory(module.getCategory()))
							{
								if (!mod.equals(module)) mod.setExpanded(false);
							}
							module.setExpanded(!module.isExpanded());
							break;
						default:
							break;
					}
				}

				if (module.isExpanded())
				{
					int sX = category.getX() + 80 + 12;
					int sY = category.getY() + H;
					for (Setting setting : Client.INSTANCE.settingManager.getSettingsByModule(module))
					{
						if (ClickUtil.isHover(sX, sY, 100, FontUtil.getFontHeight(), mX, mY))
						{
							processClickSetting(setting, mX, mY, sX, sY - 2, 102, FontUtil.getFontHeight() + 4, mB);
						}
						sY += 12;
					}

					if (ClickUtil.isHover(sX, sY, 100, FontUtil.getFontHeight(), mX, mY))
					{
						if (!module.isBinding()) module.setBinding(true);
						B = true;
					}
				}
				H += 20;
			}
		}
	}

	public void processClickSetting(Setting setting, int mX, int mY, int X, int Y, int W, int H, int mB)
	{
		if (setting.isBoolean())
		{
			setting.setBoolValue(!setting.getBoolValue());
			return;
		}

		if (setting.isInteger())
		{
			int s = setting.getIntValue();
			if (ClickUtil.isHover(X, Y, W / 4, H, mX, mY))
			{
				s -= 5;
			}
			else if (ClickUtil.isHover(X + W / 4, Y, W / 4, H, mX, mY))
			{
				s -= 1;
			}
			else if (ClickUtil.isHover(X + W / 2, Y, W / 4, H, mX, mY))
			{
				s += 1;
			}
			else if (ClickUtil.isHover(X + W / 4 * 3, Y, W / 4, H, mX, mY))
			{
				s += 5;
			}
			if (setting.getIntMinValue() <= s && setting.getIntMaxValue() >= s)
			{
				setting.setIntValue(s);
			}
		}
		if (setting.isEnum())
		{
			ClickUtil.processEnumClick(setting, mB);
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		draggingCategory = null;
		D = false;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode)
	{
		if (!B && keyCode == Keyboard.KEY_ESCAPE)
		{
			mc.player.closeScreen();
			return;
		}
		for (Module module : Client.INSTANCE.moduleManager.getModules())
		{
			if (module.isBinding())
			{
				module.setBinding(false);
				B = false;

				if (keyCode == Keyboard.KEY_ESCAPE)
				{
					module.setBind(Keyboard.KEYBOARD_SIZE);
				}
				else
				{
					module.setBind(keyCode);
				}
			}
		}
	}
}
