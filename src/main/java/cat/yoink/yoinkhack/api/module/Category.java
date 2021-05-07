package cat.yoink.yoinkhack.api.module;

/**
 * @author yoink
 * @since 8/28/2020
 */
public enum Category
{
	COMBAT("Combat", 3),
	EXPLOIT("Exploit", 113),
	PLAYER("Player", 223),
	MOVEMENT("Movement", 333),
	RENDER("Visuals", 443),
	MISC("Miscellaneous", 553),
	HUD("HUD", 663);

	private String name;
	private int X;
	private int Y;
	private boolean expanded;

	Category(String name, int X)
	{
		this.name = name;
		this.X = X;
		this.Y = 3;
		this.expanded = true;
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

	public boolean isExpanded()
	{
		return expanded;
	}

	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
	}
}
