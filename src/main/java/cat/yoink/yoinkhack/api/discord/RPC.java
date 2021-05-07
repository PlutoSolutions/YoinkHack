package cat.yoink.yoinkhack.api.discord;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class RPC
{
	private String details;
	private String state;

	public String getDetails()
	{
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public ArrayList<String> getForConfig()
	{
		return new ArrayList<>(Collections.singletonList(state));
	}
}