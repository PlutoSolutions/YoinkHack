package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.util.RenderUtil;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class Nametags extends Module
{
	public Nametags(String name, Category category, String description)
	{
		super(name, category, description);
	}


	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event)
	{
		if (mc.player == null || mc.world == null || mc.getRenderManager().options == null) return;

        for (EntityPlayer player : mc.world.playerEntities)
		{
			if (!mc.player.equals(player)) drawNametag(player.getName(), (float) mc.player.posX, (float) mc.player.posY, (float) mc.player.posZ);
		}
	}

	private void drawNametag(String text, float x, float y, float z)
	{
		RenderUtil.glSetup();
		GlStateManager.translate(x - mc.getRenderManager().viewerPosX, y + 2.3f - mc.getRenderManager().viewerPosY, z - mc.getRenderManager().viewerPosZ);
		GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate((float) (mc.getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GlStateManager.scale(-0.025F, -0.025F, 0.025F);

        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		float i = FontUtil.getStringWidth(text) / 2f;
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
		FontUtil.drawString(text, -FontUtil.getStringWidth(text) / 2f, 0, 553648127);
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
		FontUtil.drawString(text, -FontUtil.getStringWidth(text) / 2f, 0, mc.player.isSneaking() ? 553648127 : -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
		RenderUtil.glCleanup();
	}


}
