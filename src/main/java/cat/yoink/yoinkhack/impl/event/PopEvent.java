package cat.yoink.yoinkhack.impl.event;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class PopEvent extends Event
{
	private Entity entity;

	public PopEvent(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}
}
