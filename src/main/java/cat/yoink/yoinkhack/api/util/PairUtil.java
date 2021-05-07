package cat.yoink.yoinkhack.api.util;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class PairUtil<T, S>
{
	private T key;
	private S value;

	public PairUtil(T key, S value)
	{
		this.key = key;
		this.value = value;
	}

	public T getKey()
	{
		return key;
	}

	public void setKey(T key)
	{
		this.key = key;
	}

	public S getValue()
	{
		return value;
	}

	public void setValue(S value)
	{
		this.value = value;
	}
}