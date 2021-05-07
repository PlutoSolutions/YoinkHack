package cat.yoink.yoinkhack.api.gui.clickgui.button;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.gui.clickgui.IComponent;
import cat.yoink.yoinkhack.api.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class SettingButton implements IComponent
{
	public final Minecraft mc = Minecraft.getMinecraft();
	private final int H;
	private Module module;
	private int X;
	private int Y;
	private int W;

	public SettingButton(Module module, int x, int y, int w, int h)
	{
		this.module = module;
		X = x;
		Y = y;
		W = w;
		H = h;
	}

	@Override
	public void render(int mX, int mY)
	{
	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
	}

	@Override
	public void mouseUp(int mX, int mY)
	{
	}

	@Override
	public void keyPress(int key)
	{
	}

	@Override
	public void close()
	{
	}

	public void drawButton()
	{
		Color darkOutlineColor = new Color(7, 7, 7, 255);

		Gui.drawRect(getX(), getY(), getX() + 3, getY() + getH() + 1, darkOutlineColor.getRGB());
		Gui.drawRect(getX(), getY(), getX() + getW(), getY() + 1, darkOutlineColor.getRGB());
		Gui.drawRect(getX() + getW(), getY(), getX() + getW() - 3, getY() + getH() + 1, darkOutlineColor.getRGB());
		Gui.drawRect(getX(), getY() + getH(), getX() + getW(), getY() + getH() + 1, darkOutlineColor.getRGB());
	}

	public void drawButton(int mX, int mY)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			switch (Client.INSTANCE.settingManager.getSettingEasy("ClickGUI", 1).getEnumValue())
			{
				case "Red":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(133, 20, 20, 232).getRGB(), new Color(112, 20, 20, 232).getRGB());
					break;
				case "Green":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(20, 133, 20, 232).getRGB(), new Color(20, 112, 20, 232).getRGB());
					break;
				case "Blue":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(20, 20, 133, 232).getRGB(), new Color(20, 20, 112, 232).getRGB());
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
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(155, 20, 20, 232).getRGB(), new Color(134, 20, 20, 232).getRGB());
					break;
				case "Green":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(20, 155, 20, 232).getRGB(), new Color(20, 134, 20, 232).getRGB());
					break;
				case "Blue":
					Client.INSTANCE.clickGUI.drawGradient(getX() + 3, getY() + 1, getX() + getW() - 3, getY() + getH(), new Color(20, 20, 155, 232).getRGB(), new Color(20, 20, 134, 232).getRGB());
					break;
				default:
					break;
			}
		}
	}

	public int getHeight()
	{
		return H;
	}

	public Module getModule()
	{
		return module;
	}

	public void setModule(Module module)
	{
		this.module = module;
	}

	public int getX()
	{
		return X;
	}

	public void setX(int x)
	{
		X = x;
	}

	public int getY()
	{
		return Y;
	}

	public void setY(int y)
	{
		Y = y;
	}

	public int getW()
	{
		return W;
	}

	public void setW(int w)
	{
		W = w;
	}

	public int getH()
	{
		return H;
	}


	public boolean isHover(int X, int Y, int W, int H, int mX, int mY)
	{
		return mX >= X && mX <= X + W && mY >= Y && mY <= Y + H;
	}
}
