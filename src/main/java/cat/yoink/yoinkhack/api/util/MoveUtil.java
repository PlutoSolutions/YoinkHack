package cat.yoink.yoinkhack.api.util;

import net.minecraft.client.Minecraft;

/**
 * @author yoink
 * @since 8/31/2020
 */
public class MoveUtil
{
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static double[] directionSpeed(double d)
	{
		float f = mc.player.movementInput.moveForward;
		float f2 = mc.player.movementInput.moveStrafe;
		float f3 = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
		if (f != 0.0f)
		{
			if (f2 > 0.0f)
			{
				f3 += (float) (f > 0.0f ? -45 : 45);
			}
			else if (f2 < 0.0f)
			{
				f3 += (float) (f > 0.0f ? 45 : -45);
			}
			f2 = 0.0f;
			if (f > 0.0f)
			{
				f = 1.0f;
			}
			else if (f < 0.0f)
			{
				f = -1.0f;
			}
		}
		double d2 = Math.sin(Math.toRadians(f3 + 90.0f));
		double d3 = Math.cos(Math.toRadians(f3 + 90.0f));
		double d4 = (double) f * d * d3 + (double) f2 * d * d2;
		double d5 = (double) f * d * d2 - (double) f2 * d * d3;
		return new double[]{d4, d5};
	}
}
