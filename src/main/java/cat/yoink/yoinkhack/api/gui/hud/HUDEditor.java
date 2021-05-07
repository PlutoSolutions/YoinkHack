package cat.yoink.yoinkhack.api.gui.hud;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class HUDEditor extends GuiScreen
{
	private boolean dragging;
	private Component draggingComponent;
	private int dragX;
	private int dragY;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		for (Component component : Client.INSTANCE.componentManager.getComponents())
		{
			if (Client.INSTANCE.moduleManager.getModuleByName(component.getName()).isEnabled())
			{
				if (dragging && draggingComponent.equals(component))
				{
					component.setX(mouseX - dragX);
					component.setY(mouseY - dragY);
				}

				if (isHover(component.getX() - 1, component.getY() - 1, component.getWidth() + 2, component.getHeight() + 2, mouseX, mouseY))
				{
					Gui.drawRect(component.getX() - 1, component.getY() - 1, component.getX() + component.getWidth() + 1, component.getY() + component.getHeight() + 1, new Color(30, 30, 30, 150).getRGB());
				}
				else
				{
					Gui.drawRect(component.getX() - 1, component.getY() - 1, component.getX() + component.getWidth() + 1, component.getY() + component.getHeight() + 1, new Color(100, 100, 100, 150).getRGB());
				}

				component.render();

			}

		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		for (Component component : Client.INSTANCE.componentManager.getComponents())
		{
			if (Client.INSTANCE.moduleManager.getModuleByName(component.getName()).isEnabled() && isHover(component.getX() - 1, component.getY() - 1, component.getWidth() + 2, component.getHeight() + 2, mouseX, mouseY))
			{
				draggingComponent = component;
				dragging = true;

				dragX = mouseX - component.getX();
				dragY = mouseY - component.getY();
			}
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		dragging = false;
		draggingComponent = null;
	}

	@Override
	public void onGuiClosed()
	{
		draggingComponent = null;
		dragging = false;

		Client.INSTANCE.moduleManager.getModuleByName("HUDEditor").disable();
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	private boolean isHover(int X, int Y, int W, int H, int mX, int mY)
	{
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}
}
