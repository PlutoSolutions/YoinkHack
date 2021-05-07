package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Durability extends Component
{
	public Durability(String name)
	{
		super(name);
	}

	public static int reverseNumber(final int num, final int min, final int max)
	{
		return max + min - num;
	}

	@Override
	public void render()
	{
		String text;

		if (isEmpty(0) && isEmpty(1) && isEmpty(2) && isEmpty(3))
		{
			text = "\u00A7fDurability: None";
		}
		else if (shouldMendRed(0) || shouldMendRed(1) || shouldMendRed(2) || shouldMendRed(3))
		{
			text = "\u00A7cDurability: Very low";
		}
		else if (shouldMendYellow(0) || shouldMendYellow(1) || shouldMendYellow(2) || shouldMendYellow(3))
		{
			text = "\u00A76Durability: Low";
		}
		else
		{
			text = "\u00A7aDurability: Normal";
		}


		boolean background = Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getBoolValue();
		boolean border = Client.INSTANCE.settingManager.getSettingEasy(getName(), 1).getBoolValue();

		Color borderColor = new Color(35, 35, 35, 143);
		Color backgroundColor = new Color(35, 35, 35, 50);


		if (border)
		{
			Gui.drawRect(getX() - 1, getY() - 1, getX(), getY() + getHeight() + 1, borderColor.getRGB());
			Gui.drawRect(getX() - 1, getY() - 1, getX() + getWidth() + 1, getY(), borderColor.getRGB());
			Gui.drawRect(getX() + getWidth(), getY() - 1, getX() + getWidth() + 1, getY() + getHeight() + 1, borderColor.getRGB());
			Gui.drawRect(getX() - 1, getY() + getHeight(), getX() + getWidth() + 1, getY() + getHeight() + 1, borderColor.getRGB());
		}
		if (background)
		{
			Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor.getRGB());
		}


		FontUtil.drawStringWithShadow(text, getX(), getY(), 0xffffffff);

		setWidth(FontUtil.getStringWidth(text));
		setHeight(FontUtil.getFontHeight());
	}

	private boolean shouldMendYellow(final int i)
	{
		return mc.player.inventory.armorInventory.get(i).getMaxDamage()
				!= 0 && 100 * mc.player.inventory.armorInventory.get(i).getItemDamage()
				/ mc.player.inventory.armorInventory.get(i).getMaxDamage()
				> reverseNumber(40, 1, 100);
	}

	private boolean shouldMendRed(final int i)
	{
		return mc.player.inventory.armorInventory.get(i).getMaxDamage()
				!= 0 && 100 * mc.player.inventory.armorInventory.get(i).getItemDamage()
				/ mc.player.inventory.armorInventory.get(i).getMaxDamage()
				> reverseNumber(20, 1, 100);
	}

	private boolean isEmpty(int slotNumber)
	{
		return mc.player.inventory.armorInventory.get(slotNumber).isEmpty();
	}
}
