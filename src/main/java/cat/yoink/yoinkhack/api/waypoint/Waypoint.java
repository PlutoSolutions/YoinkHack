package cat.yoink.yoinkhack.api.waypoint;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.util.RenderUtil;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Waypoint
{
	private final Minecraft mc = Minecraft.getMinecraft();
	private final float x;
	private final float y;
	private final float z;
	private final String name;
	private final int dimension;
	private final String ip;
	private float hue;

	public Waypoint(float x, float y, float z, String name, int dimension, String ip)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
		this.dimension = dimension;
		this.ip = ip;
		hue = 0f;
	}

	public void renderBox()
	{
		if (mc.isIntegratedServerRunning() || ip.equals("Singleplayer"))
		{
			rBox();
		}
		else
		{
			if (mc.getCurrentServerData() == null) return;
			if (mc.getCurrentServerData().serverIP.equalsIgnoreCase(ip) && mc.player.dimension == dimension) rBox();
		}
	}

	public void renderTag()
	{
		if (mc.isIntegratedServerRunning() || ip.equals("Singleplayer"))
		{
			rTag();
		}
		else
		{
			if (mc.getCurrentServerData() == null) return;
			if (mc.getCurrentServerData().serverIP.equalsIgnoreCase(ip) && mc.player.dimension == dimension) rTag();
		}
	}

	private void rBox()
	{
		hue += Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 8).getIntValue() / 1000f;
		int rgb = Color.HSBtoRGB(hue, 1.0F, 1.0F);

		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;

		AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x - 0.38f - mc.getRenderManager().viewerPosX, y - mc.getRenderManager().viewerPosY, z - 0.38f - mc.getRenderManager().viewerPosZ, x + 0.38f - mc.getRenderManager().viewerPosX, y + 1.97f - mc.getRenderManager().viewerPosY, z + 0.38f - mc.getRenderManager().viewerPosZ);
		if (Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 3).getEnumValue().equals("Rainbow"))
		{
			RenderUtil.drawBox(axisAlignedBB, r / 255f, g / 255f, b / 255f, Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 7).getIntValue() / 255f);
		}
		else
		{
			RenderUtil.drawBox(axisAlignedBB, Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 4).getIntValue() / 255f, Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 5).getIntValue() / 255f, Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 6).getIntValue() / 255f, Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 7).getIntValue() / 255f);
		}
	}

	private void rTag()
	{
		String display = name;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x - mc.getRenderManager().viewerPosX, y + 2.3f - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);
		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) ((mc.getRenderManager().options.thirdPersonView == 2) ? -1 : 1) * mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		float f = (float) (new Vec3d(mc.getRenderManager().viewerPosX, mc.getRenderManager().viewerPosY, mc.getRenderManager().viewerPosZ)).distanceTo(new Vec3d(x, y + 2.3, z));
		if (f < 2) f = 2;
		float m = (f / 8f) * (float) (Math.pow(1.2589254f, 3.3));
		GlStateManager.scale(m, m, m);
		GlStateManager.scale(-0.025F, -0.025F, 0.025F);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GlStateManager.disableDepth();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		if (Client.INSTANCE.settingManager.getSettingEasy("Waypoints", 2).getBoolValue())
		{
			display += String.format(" \u00A77%d %d %d", Math.round(x), Math.round(y), Math.round(z));
		}
		int i = FontUtil.getStringWidth(display) / 2;
		GlStateManager.disableTexture2D();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(-i - 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		bufferbuilder.pos(-i - 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		bufferbuilder.pos(i + 1, 8, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		bufferbuilder.pos(i + 1, -1, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		FontUtil.drawString(display, -FontUtil.getStringWidth(display) / 2f, 0, 553648127);
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		FontUtil.drawString(display, -FontUtil.getStringWidth(display) / 2f, 0, -1);
		GlStateManager.enableLighting();
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getZ()
	{
		return z;
	}

	public String getName()
	{
		return name;
	}

	public int getDimension()
	{
		return dimension;
	}

	public String getIp()
	{
		return ip;
	}
}
