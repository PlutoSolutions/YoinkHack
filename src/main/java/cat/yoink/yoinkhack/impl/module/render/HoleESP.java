package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.HoleUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class HoleESP extends Module
{
	private final Setting range = new Setting("Range", this, 1, 6, 20);
	private final Setting ignoreOwn = new Setting("IgnoreOwn", this, false);
	private final Setting holeMode = new Setting("Mode", this, "Block", new ArrayList<>(Arrays.asList("Block", "Flat")));
	private final Setting place = new Setting("Place", this, "Normal", new ArrayList<>(Arrays.asList("Normal", "Down")));
	private final Setting obsidianRed = new Setting("ObbyRed", this, 0, 70, 100);
	private final Setting obsidianGreen = new Setting("ObbyGreen", this, 0, 0, 100);
	private final Setting obsidianBlue = new Setting("ObbyBlue", this, 0, 0, 100);
	private final Setting obsidianAlpha = new Setting("ObbyAlpha", this, 0, 15, 100);
	private final Setting bedrockRed = new Setting("BedrockRed", this, 0, 0, 100);
	private final Setting bedrockGreen = new Setting("BedrockGreen", this, 0, 70, 100);
	private final Setting bedrockBlue = new Setting("BedrockBlue", this, 0, 0, 100);
	private final Setting bedrockAlpha = new Setting("BedrockAlpha", this, 0, 15, 100);

	private final List<HoleUtil> holes = new ArrayList<>();
	private final ICamera camera = new Frustum();

	public HoleESP(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(range);
		addSetting(ignoreOwn);
		addSetting(holeMode);
		addSetting(place);
		addSetting(obsidianRed);
		addSetting(obsidianGreen);
		addSetting(obsidianBlue);
		addSetting(obsidianAlpha);
		addSetting(bedrockRed);
		addSetting(bedrockGreen);
		addSetting(bedrockBlue);
		addSetting(bedrockAlpha);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent tickEvent)
	{
		if (mc.player == null || mc.world == null) return;

		holes.clear();
		Vec3i playerPos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);

		for (int x = playerPos.getX() - range.getIntValue(); x < playerPos.getX() + range.getIntValue(); x++)
		{
			for (int z = playerPos.getZ() - range.getIntValue(); z < playerPos.getZ() + range.getIntValue(); z++)
			{
				for (int y = playerPos.getY() + range.getIntValue(); y > playerPos.getY() - range.getIntValue(); y--)
				{

					final BlockPos blockPos = new BlockPos(x, y, z);
					if (ignoreOwn.getBoolValue() && mc.player.getDistanceSq(blockPos) <= 1)
					{
						continue;
					}
					final IBlockState blockState = mc.world.getBlockState(blockPos);
					final IBlockState downBlockState = mc.world.getBlockState(blockPos.down());
					HoleUtil.HoleTypes holeTypes = isBlockValid(blockState, blockPos);

					if (downBlockState.getBlock() == Blocks.AIR)
					{
						final BlockPos downPos = blockPos.down();
						holeTypes = isBlockValid(downBlockState, blockPos);
						holes.add(new HoleUtil(downPos.getX(), downPos.getY(), downPos.getZ(), downPos, holeTypes, true));
					}
					else
					{
						holes.add(new HoleUtil(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos, holeTypes));
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void renderWorldEvent(RenderWorldLastEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mc.getRenderManager().options == null)
		{
			return;
		}

		new ArrayList<>(holes).forEach(hole ->
		{

			AxisAlignedBB bb;

			if (place.getEnumValue().equals("Normal"))
			{
				bb = new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() + (hole.isTall() ? 2 : 1) - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ);
			}
			else
			{
				bb = new AxisAlignedBB(hole.getX() - mc.getRenderManager().viewerPosX, hole.getY() - 1 - mc.getRenderManager().viewerPosY, hole.getZ() - mc.getRenderManager().viewerPosZ, hole.getX() + 1 - mc.getRenderManager().viewerPosX, hole.getY() - 1 + (hole.isTall() ? 2 : 1) - mc.getRenderManager().viewerPosY, hole.getZ() + 1 - mc.getRenderManager().viewerPosZ);
			}

			camera.setPosition(Objects.requireNonNull(mc.getRenderViewEntity()).posX, mc.getRenderViewEntity().posY, mc.getRenderViewEntity().posZ);

			if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + mc.getRenderManager().viewerPosX, bb.minY + mc.getRenderManager().viewerPosY, bb.minZ + mc.getRenderManager().viewerPosZ, bb.maxX + mc.getRenderManager().viewerPosX, bb.maxY + mc.getRenderManager().viewerPosY, bb.maxZ + mc.getRenderManager().viewerPosZ)))
			{

				GlStateManager.pushMatrix();
				GlStateManager.enableBlend();
				GlStateManager.disableDepth();
				GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
				GlStateManager.disableTexture2D();
				GlStateManager.depthMask(false);
				GL11.glEnable(GL11.GL_LINE_SMOOTH);
				GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
				GL11.glLineWidth(1.5f);

				switch (hole.getHoleTypes())
				{

					case Bedrock:
						render(holeMode.getEnumValue(), bb, bedrockRed.getIntValue() / 100f, bedrockGreen.getIntValue() / 100f, bedrockBlue.getIntValue() / 100f, bedrockAlpha.getIntValue() / 100f);
						break;

					case Obsidian:
						render(holeMode.getEnumValue(), bb, obsidianRed.getIntValue() / 100f, obsidianGreen.getIntValue() / 100f, obsidianBlue.getIntValue() / 100f, obsidianAlpha.getIntValue() / 100f);
						break;

					default:
						break;

				}

				GL11.glDisable(GL11.GL_LINE_SMOOTH);
				GlStateManager.depthMask(true);
				GlStateManager.enableDepth();
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();
				GlStateManager.popMatrix();

			}

		});

	}


	public void render(String mode, final AxisAlignedBB bb, float r, float g, float b, float a)
	{

		switch (mode)
		{

			case "Flat":
				RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, r, g, b, a);
				break;

			case "Block":
				RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, r, g, b, a);
				break;

			default:
				break;

		}

	}


	public HoleUtil.HoleTypes isBlockValid(IBlockState blockState, BlockPos blockPos)
	{

		if (blockState.getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR || mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR)
		{
			return HoleUtil.HoleTypes.None;
		}

		final BlockPos[] touchingBlocks = new BlockPos[]
				{
						blockPos.north(),
						blockPos.south(),
						blockPos.east(),
						blockPos.west()
				};


		boolean bedrock = true;
		boolean obsidian = true;

		int validHorizontalBlocks = 0;
		for (BlockPos touching : touchingBlocks)
		{

			final IBlockState touchingState = mc.world.getBlockState(touching);

			if ((touchingState.getBlock() != Blocks.AIR) && touchingState.isFullBlock())
			{

				validHorizontalBlocks++;

				if (touchingState.getBlock() != Blocks.BEDROCK && bedrock)
				{
					bedrock = false;
				}

				if (!bedrock && touchingState.getBlock() != Blocks.OBSIDIAN && touchingState.getBlock() != Blocks.BEDROCK)
				{
					obsidian = false;
				}

			}
		}

		if (validHorizontalBlocks < 4)
		{
			return HoleUtil.HoleTypes.None;
		}

		if (bedrock)
		{
			return HoleUtil.HoleTypes.Bedrock;
		}

		if (obsidian)
		{
			return HoleUtil.HoleTypes.Obsidian;
		}

		return HoleUtil.HoleTypes.Normal;
	}
}
