package cat.yoink.yoinkhack.api.notification.client;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.util.RenderUtil;
import cat.yoink.yoinkhack.api.util.TimerUtil;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Notification
{
	private final Minecraft mc = Minecraft.getMinecraft();
	private final TimerUtil timer;
	private final String text;
	private final long stayTime;
	private float x;
	private float oldX;
	private float y;
	private float oldY;
	private float width;
	private boolean done;

	Notification(final String text, final long stayTime)
	{
		super();
		this.x = (float) (new ScaledResolution(mc).getScaledWidth() - 2);
		this.y = (float) (new ScaledResolution(mc).getScaledHeight() - 2);
		this.text = text;
		this.stayTime = stayTime;
		(this.timer = new TimerUtil()).reset();
		this.done = false;
	}

	public void renderNotification(final float prevY)
	{
		final float xSpeed = this.width / (Minecraft.getDebugFPS() / 4f);
		final float ySpeed = (new ScaledResolution(mc).getScaledHeight() - prevY) / (Minecraft.getDebugFPS() / 8f);
		if (this.width != FontUtil.getStringWidth(text) + 43f)
		{
			this.width = FontUtil.getStringWidth(text) + 43f;
		}
		if (this.done)
		{
			this.oldX = this.x;
			this.x += xSpeed;
			this.y += ySpeed;
		}
		if (!this.done() && !this.done)
		{
			this.timer.reset();
			this.oldX = this.x;
			if (this.x <= new ScaledResolution(mc).getScaledWidth()/* - 2*/ - this.width + xSpeed)
			{
				this.x = new ScaledResolution(mc).getScaledWidth()/* - 2*/ - this.width;
			}
			else
			{
				this.x -= xSpeed;
			}
		}
		else if (this.timer.reach(this.stayTime))
		{
			this.done = true;
		}
		if (this.x < new ScaledResolution(mc).getScaledWidth() /*- 2 */ - this.width)
		{
			this.oldX = this.x;
			this.x += xSpeed;
		}
		if (this.y != prevY)
		{
			if (this.y > prevY + ySpeed)
			{
				this.y -= ySpeed;
			}
			else
			{
				this.y = prevY;
			}
		}
		else if (this.y < prevY)
		{
			this.oldY = this.y;
			this.y += ySpeed;
		}
		final float finishedX = this.oldX + (this.x - this.oldX);
		final float finishedY = this.oldY + (this.y - this.oldY);

		Color border = new Color(184, 184, 184, 188);

		RenderUtil.drawRect(finishedX, finishedY, this.width, 22f, new Color(0, 0, 0, 129).getRGB());

		RenderUtil.drawRect(finishedX, finishedY, width, 1f, border.getRGB());
		RenderUtil.drawRect(finishedX, finishedY, 1f, 22, border.getRGB());
		RenderUtil.drawRect(finishedX, finishedY + 21f, width, 1f, border.getRGB());

		FontUtil.drawStringWithShadow(this.text, finishedX + 10f, finishedY + 7f, -1);

		if (this.delete())
		{
			Client.INSTANCE.clientNotificationManager.getNotifications().remove(this);
		}
	}

	public boolean done()
	{
		return this.x <= new ScaledResolution(mc).getScaledWidth()/* - 2*/ - this.width;
	}

	public boolean delete()
	{
		return this.x >= new ScaledResolution(mc).getScaledWidth()/* - 2*/ && this.done;
	}
}
