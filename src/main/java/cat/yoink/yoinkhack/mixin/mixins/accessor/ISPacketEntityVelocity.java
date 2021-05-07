package cat.yoink.yoinkhack.mixin.mixins.accessor;

/**
 * @author yoink
 * @since 8/28/2020
 */
public interface ISPacketEntityVelocity
{
	int getMotionX();

	void setMotionX(int x);

	int getMotionY();

	void setMotionY(int y);

	int getMotionZ();

	void setMotionZ(int z);

	int getEntityID();

	void setEntityID(int id);
}
