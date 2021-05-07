package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

import java.awt.*;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class InventoryViewer extends Component
{
	public InventoryViewer(String name)
	{
		super(name);

		setWidth(16 * 9);
		setHeight(16 * 3);
	}

	@Override
	public void render()
	{

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

		GlStateManager.pushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		for (int i = 0; i < 27; i++)
		{
			ItemStack itemStack = mc.player.inventory.mainInventory.get(i + 9);
			int offsetX = getX() + (i % 9) * 16;
			int offsetY = getY() + (i / 9) * 16;
			mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
			mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack, offsetX, offsetY, null);
		}
		RenderHelper.disableStandardItemLighting();
		mc.getRenderItem().zLevel = 0.0F;
		GlStateManager.popMatrix();
	}
}
