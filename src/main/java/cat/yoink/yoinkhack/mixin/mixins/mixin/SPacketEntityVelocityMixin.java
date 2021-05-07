package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.mixin.mixins.accessor.ISPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = SPacketEntityVelocity.class, priority = 999)
public class SPacketEntityVelocityMixin implements ISPacketEntityVelocity
{
	@Shadow
	private int motionX;
	@Shadow
	private int motionY;
	@Shadow
	private int motionZ;
	@Shadow
	private int entityID;

	@Override
	public int getMotionX()
	{
		return motionX;
	}

	@Override
	public void setMotionX(int x)
	{
		motionX = x;
	}

	@Override
	public int getMotionY()
	{
		return motionY;
	}

	@Override
	public void setMotionY(int y)
	{
		motionY = y;
	}

	@Override
	public int getMotionZ()
	{
		return motionZ;
	}

	@Override
	public void setMotionZ(int z)
	{
		motionZ = z;
	}

	@Override
	public int getEntityID()
	{
		return entityID;
	}

	@Override
	public void setEntityID(int entityID)
	{
		this.entityID = entityID;
	}
}
