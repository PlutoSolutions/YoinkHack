package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.mixin.mixins.accessor.ITimer;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = Timer.class, priority = 999)
public class TimerMixin implements ITimer
{
	@Shadow
	private float tickLength;

	@Override
	public float getTickLength()
	{
		return tickLength;
	}

	@Override
	public void setTickLength(float tickLength)
	{
		this.tickLength = tickLength;
	}
}
