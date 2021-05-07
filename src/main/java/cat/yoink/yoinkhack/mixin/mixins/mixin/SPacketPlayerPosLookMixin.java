package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.mixin.mixins.accessor.ISPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = SPacketPlayerPosLook.class, priority = 999)
public class SPacketPlayerPosLookMixin implements ISPacketPlayerPosLook
{
	@Shadow
	private float yaw;
	@Shadow
	private float pitch;

	@Override
	public float getPitch()
	{
		return pitch;
	}

	@Override
	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}

	@Override
	public float getYaw()
	{
		return yaw;
	}

	@Override
	public void setYaw(float yaw)
	{
		this.yaw = yaw;
	}
}
