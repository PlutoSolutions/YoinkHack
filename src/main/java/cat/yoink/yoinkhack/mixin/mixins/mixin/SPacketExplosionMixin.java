package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.mixin.mixins.accessor.ISPacketExplosion;
import net.minecraft.network.play.server.SPacketExplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = SPacketExplosion.class, priority = 999)
public class SPacketExplosionMixin implements ISPacketExplosion
{
	@Shadow
	private float motionX;
	@Shadow
	private float motionY;
	@Shadow
	private float motionZ;

	@Override
	public float getMotionX()
	{
		return motionX;
	}

	@Override
	public void setMotionX(float motionX)
	{
		this.motionX = motionX;
	}

	@Override
	public float getMotionY()
	{
		return motionY;
	}

	@Override
	public void setMotionY(float motionY)
	{
		this.motionY = motionY;
	}

	@Override
	public float getMotionZ()
	{
		return motionZ;
	}

	@Override
	public void setMotionZ(float motionZ)
	{
		this.motionZ = motionZ;
	}
}
