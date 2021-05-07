package cat.yoink.yoinkhack.api.buttons;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Button
{
	private String name;
	private String ip;
	private int id;

	public Button(String name, String ip, int id)
	{
		this.name = name;
		this.ip = ip;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
