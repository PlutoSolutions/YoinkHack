package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.awt.*;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class CombatSupplies extends Component
{
	public CombatSupplies(String name)
	{
		super(name);
	}

	@Override
	public void render()
	{

		int crystalCount = 0;
		int totemCount = 0;
		int xpCount = 0;
		int gapCount = 0;

		for (int i = 0; i < 45; i++)
		{

			ItemStack itemStack = mc.player.inventory.getStackInSlot(i);

			if (itemStack.getItem().equals(Items.END_CRYSTAL))
			{
				crystalCount += itemStack.getCount();
			}
			else if (itemStack.getItem().equals(Items.TOTEM_OF_UNDYING))
			{
				totemCount += itemStack.getCount();
			}
			else if (itemStack.getItem().equals(Items.EXPERIENCE_BOTTLE))
			{
				xpCount += itemStack.getCount();
			}
			else if (itemStack.getItem().equals(Items.GOLDEN_APPLE)) gapCount += itemStack.getCount();

		}

		String crystalString;
		String totemString;
		String xpString;
		String gapString;

		if (crystalCount == 0)
		{
			crystalString = "\u00A7c0 Crystals";
		}
		else if (crystalCount < 64)
		{
			crystalString = "\u00A76" + crystalCount + " Crystals";
		}
		else
		{
			crystalString = "\u00A7a" + crystalCount + " Crystals";
		}

		if (totemCount == 0)
		{
			totemString = "\u00A7c0 Totems";
		}
		else if (totemCount == 1)
		{
			totemString = "\u00A76" + totemCount + " Totem";
		}
		else
		{
			totemString = "\u00A7a" + totemCount + " Totems";
		}

		if (xpCount == 0)
		{
			xpString = "\u00A7c0 XP Bottles";
		}
		else if (xpCount < 64)
		{
			xpString = "\u00A76" + xpCount + " XP Bottles";
		}
		else
		{
			xpString = "\u00A7a" + xpCount + " XP Bottles";
		}


		if (gapCount == 0)
		{
			gapString = "\u00A7c0 Gapples";
		}
		else if (gapCount < 32)
		{
			gapString = "\u00A76" + gapCount + " Gapples";
		}
		else
		{
			gapString = "\u00A7a" + gapCount + " Gapples";
		}

		String text;

		boolean background = Client.INSTANCE.settingManager.getSettingEasy(getName(), 1).getBoolValue();
		boolean border = Client.INSTANCE.settingManager.getSettingEasy(getName(), 2).getBoolValue();

		String[] array =
				{
						crystalString,
						totemString,
						xpString,
						gapString
				};

		int maxWidth = 0;

		for (String s : array)
		{

			int w = FontUtil.getStringWidth(s);

			if (w > maxWidth)
			{

				maxWidth = w;

			}

		}

		Color borderColor = new Color(35, 35, 35, 143);
		Color backgroundColor = new Color(35, 35, 35, 50);


		switch (Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getEnumValue().toLowerCase())
		{

			case "horizontal":

				text = String.format("%s\u00A77, %s\u00A77, %s\u00A77, %s", totemString, crystalString, xpString, gapString);

				setWidth(FontUtil.getStringWidth(text));
				setHeight(FontUtil.getFontHeight());


				border(border, borderColor);

				if (background)
				{
					Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor.getRGB());
				}

				FontUtil.drawStringWithShadow(text, getX(), getY(), 0xffffffff);

				break;

			case "vertical":

				setWidth(maxWidth);
				setHeight(FontUtil.getFontHeight() * 4);

				border(border, borderColor);

				if (background)
				{
					Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor.getRGB());
				}

				FontUtil.drawStringWithShadow(totemString, getX(), getY(), 0xffffffff);
				FontUtil.drawStringWithShadow(crystalString, getX(), getY() + (FontUtil.getFontHeight()), 0xffffffff);
				FontUtil.drawStringWithShadow(xpString, getX(), getY() + (FontUtil.getFontHeight() * 2), 0xffffffff);
				FontUtil.drawStringWithShadow(gapString, getX(), getY() + (FontUtil.getFontHeight() * 3), 0xffffffff);

				break;

			default:
				break;
		}

	}

	private void border(boolean border, Color borderColor)
	{
		if (border)
		{
			Gui.drawRect(getX() - 1, getY() - 1, getX(), getY() + getHeight() + 1, borderColor.getRGB());
			Gui.drawRect(getX() - 1, getY() - 1, getX() + getWidth() + 1, getY(), borderColor.getRGB());
			Gui.drawRect(getX() + getWidth(), getY() - 1, getX() + getWidth() + 1, getY() + getHeight() + 1, borderColor.getRGB());
			Gui.drawRect(getX() - 1, getY() + getHeight(), getX() + getWidth() + 1, getY() + getHeight() + 1, borderColor.getRGB());
		}
	}
}
