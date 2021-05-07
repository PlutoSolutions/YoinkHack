package cat.yoink.yoinkhack.impl.event;

import cat.yoink.yoinkhack.api.module.Module;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ToggleEvent extends Event
{
	private final Module module;
	private final boolean state;

	public ToggleEvent(Module module, boolean state)
	{
		this.module = module;
		this.state = state;
	}

	public Module getModule()
	{
		return module;
	}

	public boolean getState()
	{
		return state;
	}
}
