package cat.yoink.yoinkhack.api.gui.old;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ClickGUIOld extends GuiScreen
{
	private Category selectedCategory;
	private int onCategoryX = 0;
	private int onCategoryY = 0;

	private void drawGUI()
	{
		for (Category category : Category.values())
		{

			int moduleX = category.getX();
			int moduleY = category.getY();

			int red = 0;
			int green = 0;
			int blue = 0;

			for (Setting setting : Objects.requireNonNull(Client.INSTANCE.settingManager.getSettingsByModule(Client.INSTANCE.moduleManager.getModuleByName("ClickGUI"))))
			{
				if (setting.getName().equals("Red"))
				{
					red = setting.getIntValue();
				}
				if (setting.getName().equals("Green"))
				{
					green = setting.getIntValue();
				}
				if (setting.getName().equals("Blue"))
				{
					blue = setting.getIntValue();
				}
			}

			drawRect(category.getX(), category.getY(), category.getX() + 120, category.getY() + 15, getIntFromColor(red, green, blue));
			drawCenteredStringWithShadow(category.getName(), category.getX() + (120 / 2), category.getY() + 4, 0xffffffff);

			if (category.isExpanded())
			{

				drawRect(category.getX() + 3, category.getY() + 15, category.getX() + 120 - 3, category.getY() + 15 + (Client.INSTANCE.moduleManager.getModuleByCategory(category).size() * 16), 0xff333333);

				moduleY--;
				for (Module module : Client.INSTANCE.moduleManager.getModules())
				{
					if (module.getCategory().getName().equals(category.getName()))
					{

						moduleY += 16;

						drawRect(moduleX + 4, moduleY, moduleX + 120 - 4, moduleY + 15, 0xff000000);
						if (module.isBinding())
						{
							FontUtil.drawStringWithShadow("Key...", moduleX + 7, moduleY + 4, 0xffffffff);
						}
						else
						{
							if (module.isEnabled())
							{
								FontUtil.drawStringWithShadow(module.getName(), moduleX + 7, moduleY + 4, 0xffffffff);
								if (Client.INSTANCE.settingManager.getSettingsByModule(module).size() != 0)
								{
									if (module.isExpanded())
									{
										FontUtil.drawStringWithShadow("-", moduleX + 120 - FontUtil.getStringWidth("-") - 7, moduleY + 4, 0xff999999);
									}
									else
									{
										FontUtil.drawStringWithShadow("+", moduleX + 120 - FontUtil.getStringWidth("+") - 7, moduleY + 4, 0xff999999);
									}
								}
							}
							else
							{
								if (Client.INSTANCE.settingManager.getSettingsByModule(module).size() != 0)
								{
									FontUtil.drawStringWithShadow(module.getName(), moduleX + 7, moduleY + 4, 0xff999999);
									if (module.isExpanded())
									{
										FontUtil.drawStringWithShadow("-", moduleX + 120 - FontUtil.getStringWidth("-") - 7, moduleY + 4, 0xff999999);
									}
									else
									{
										FontUtil.drawStringWithShadow("+", moduleX + 120 - FontUtil.getStringWidth("-") - 7, moduleY + 4, 0xff999999);
									}
								}
								else
								{
									FontUtil.drawStringWithShadow(module.getName(), moduleX + 7, moduleY + 4, 0xff999999);
								}
							}
						}


						if (module.isExpanded())
						{
							ArrayList<Setting> modSettings;
							modSettings = Client.INSTANCE.settingManager.getSettingsByModule(module);
							if (modSettings != null)
							{
								for (Setting setting : modSettings)
								{
									moduleY += 15;
									drawRect(moduleX + 4, moduleY + 1, moduleX + 120 - 4, moduleY + 15 + 1, 0xff000000);
									if (setting.isBoolean())
									{
										if (setting.getBoolValue())
										{
											FontUtil.drawStringWithShadow(" " + setting.getName(), moduleX + 8, moduleY + 4, 0xffffffff);
										}
										else
										{
											FontUtil.drawStringWithShadow(" " + setting.getName(), moduleX + 8, moduleY + 4, 0xff999999);
										}
									}
									if (setting.isInteger())
									{
										FontUtil.drawStringWithShadow(" " + setting.getName(), moduleX + 8, moduleY + 4, 0xffffffff);
										FontUtil.drawStringWithShadow(String.valueOf(setting.getIntValue()), moduleX + 120 - 8 - FontUtil.getStringWidth(String.valueOf(setting.getIntValue())), moduleY + 4, 0xffffffff);
									}
									if (setting.isEnum())
									{
										FontUtil.drawStringWithShadow(" " + setting.getName(), moduleX + 8, moduleY + 4, 0xffffffff);
										FontUtil.drawStringWithShadow(setting.getEnumValue(), moduleX + 120 - 8 - FontUtil.getStringWidth(setting.getEnumValue()), moduleY + 4, 0xffffffff);
									}

									moduleY++;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawGUI();
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		for (Category category : Category.values())
		{

			int moduleX = category.getX();
			int moduleY = category.getY();

			if (mouseButton == 1 && mouseX > category.getX() && mouseX < category.getX() + 120 && mouseY > category.getY() && mouseY < category.getY() + 15)
			{
				category.setExpanded(!category.isExpanded());
			}

			if (category.isExpanded())
			{

				moduleY--;

				for (Module module : Client.INSTANCE.moduleManager.getModules())
				{
					if (module.getCategory().getName().equals(category.getName()))
					{

						moduleY += 16;

						if (mouseX > moduleX + 4 && mouseX < moduleX + 120 - 4 && mouseY > moduleY && mouseY < moduleY + 15)
						{
							if (mouseButton == 0)
							{
								module.toggle();
							}
							if (mouseButton == 1)
							{
								module.setExpanded(!module.isExpanded());
							}
							if (mouseButton == 2)
							{
								module.setBinding(true);
							}
						}

						if (module.isExpanded())
						{
							ArrayList<Setting> modSettings;
							modSettings = Client.INSTANCE.settingManager.getSettingsByModule(module);
							if (modSettings != null)
							{
								for (Setting setting : modSettings)
								{
									moduleY += 15;
									if (setting.isBoolean() && mouseButton == 0 && mouseX > moduleX + 5 && mouseX < moduleX + 120 - 5 && mouseY > moduleY && mouseY < moduleY + 15)
									{
										setting.setBoolValue(!setting.getBoolValue());
									}
									if (setting.isInteger())
									{
										int set = setting.getIntValue();
										if (mouseX > moduleX && mouseX < moduleX + (120 / 4) && mouseY > moduleY && mouseY < moduleY + 15)
										{
											set -= 5;
										}
										if (mouseX > moduleX + (120 / 4) && mouseX < moduleX + (120 / 2) && mouseY > moduleY && mouseY < moduleY + 15)
										{
											set -= 1;
										}
										if (mouseX > moduleX + (120 / 2) && mouseX < moduleX + ((120 / 4) * 3) && mouseY > moduleY && mouseY < moduleY + 15)
										{
											set += 1;
										}
										if (mouseX > moduleX + ((120 / 4) * 3) && mouseX < moduleX + 120 && mouseY > moduleY && mouseY < moduleY + 15)
										{
											set += 5;
										}

										if ((setting.getIntMinValue() - 1) < set && (setting.getIntMaxValue() + 1) > set)
										{
											setting.setIntValue(set);
										}

									}
									if (setting.isEnum() && mouseX > moduleX + 5 && mouseX < moduleX + 120 - 5 && mouseY > moduleY && mouseY < moduleY + 15)
									{
										ClickUtil.processEnumClick(setting, mouseButton);

									}
									moduleY++;
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		for (Category category : Category.values())
		{
			if (selectedCategory == null && clickedMouseButton == 0 && mouseX > category.getX() && mouseX < category.getX() + 120 && mouseY > category.getY() && mouseY < category.getY() + 15)
			{
				selectedCategory = category;
				onCategoryX = mouseX - selectedCategory.getX();
				onCategoryY = mouseY - selectedCategory.getY();
			}

			if (selectedCategory != null)
			{
				ScaledResolution scaledResolution = new ScaledResolution(mc);

				if ((mouseX - onCategoryX) < 0)
				{
					selectedCategory.setX(0);
				}
				else if (((mouseX - onCategoryX) + 120) > scaledResolution.getScaledWidth())
				{
					selectedCategory.setX(scaledResolution.getScaledWidth() - 120);
				}
				else
				{
					selectedCategory.setX(mouseX - onCategoryX);
				}

				if ((mouseY - onCategoryY) < 0)
				{
					selectedCategory.setY(0);
				}
				else if ((mouseY - onCategoryY + 15) > scaledResolution.getScaledHeight())
				{
					selectedCategory.setY(scaledResolution.getScaledHeight() - 15);
				}
				else
				{
					selectedCategory.setY(mouseY - onCategoryY);
				}

			}

		}
	}

	@Override
	public void onGuiClosed()
	{
		for (Module module : Client.INSTANCE.moduleManager.getModules())
		{
			if (module.getName().equals("ClickGUI")) module.disable();
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		selectedCategory = null;
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode)
	{
		boolean isBinding = false;
		for (Module module : Client.INSTANCE.moduleManager.getModules())
		{
			if (module.isBinding())
			{
				isBinding = true;
				if (keyCode != Keyboard.KEY_ESCAPE)
				{
					module.setBind(keyCode);
				}
				else
				{
					module.setBind(Keyboard.KEYBOARD_SIZE);
				}
				module.setBinding(false);
			}
		}
		if (!isBinding && keyCode == Keyboard.KEY_ESCAPE)
		{
			mc.player.closeScreen();
		}
	}

	public int getIntFromColor(int red, int green, int blue)
	{
		int r = (red << 16) & 0x00FF0000;
		int g = (green << 8) & 0x0000FF00;
		int b = blue & 0x000000FF;

		return 0xFF000000 | r | g | b;
	}

	public void drawCenteredStringWithShadow(String message, int x, int y, int color)
	{
		int messageWidth = FontUtil.getStringWidth(message);
		int messageX = x + (-(messageWidth >> 1));

		FontUtil.drawStringWithShadow(message, messageX, y, color);
	}

}