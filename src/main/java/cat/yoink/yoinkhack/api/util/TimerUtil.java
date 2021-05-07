package cat.yoink.yoinkhack.api.util;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class TimerUtil
{
	private long time = System.nanoTime() / 1000000L;

	public boolean reach(long time)
	{
		return this.time() >= time;
	}

	public void reset()
	{
		this.time = System.nanoTime() / 1000000L;
	}

	public long time()
	{
		return System.nanoTime() / 1000000L - this.time;
	}
}
