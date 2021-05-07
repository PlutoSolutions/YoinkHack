package cat.yoink.yoinkhack.mixin.mixins.accessor;

/**
 * @author yoink
 * @since 8/28/2020
 */
public interface ICPacketPlayer
{
	void setOnGround(boolean onGround);

	void setX(double x);

	void setY(double y);

	void setZ(double z);

	void setYaw(float yaw);

	void setPitch(float pitch);
}
