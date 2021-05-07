package cat.yoink.yoinkhack.api.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Component
{
	public final Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private int X;
	private int Y;
	private int width;
	private int height;

	public Component(String name)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution resolution = new ScaledResolution(mc);

		setName(name);
		setX(resolution.getScaledWidth() / 2);
		setY(resolution.getScaledHeight() / 2);
		setWidth(100);
		setHeight(100);
	}


	public void render()
	{
	}


	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
}
