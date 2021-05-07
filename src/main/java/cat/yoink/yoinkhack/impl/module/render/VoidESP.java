package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.RenderUtil;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yoink
 * @since 8/26/2020
 */
public class VoidESP extends Module
{
	private final Setting range = new Setting("Range", this, 1, 8, 50);
	private final Setting color = new Setting("Color", this, "Static", new ArrayList<>(Arrays.asList("Static", "Rainbow")));
	private final Setting red = new Setting("Red", this, 0, 255, 255);
	private final Setting green = new Setting("Green", this, 0, 20, 255);
	private final Setting blue = new Setting("Blue", this, 0, 20, 255);
	private final Setting alpha = new Setting("Alpha", this, 0, 100, 255);
	private final Setting rainbowSpeed = new Setting("RainbowSpeed", this, 0, 5, 10);
	private final ConcurrentSet<BlockPos> voidHoles = new ConcurrentSet<>();
	private float hue = 0f;

	public VoidESP(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(range);
		addSetting(color);
		addSetting(red);
		addSetting(green);
		addSetting(blue);
		addSetting(alpha);
		addSetting(rainbowSpeed);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null || mc.player.dimension == 1) return;

		voidHoles.clear();

		for (BlockPos pos : getCircle(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ)), range.getIntValue()))
		{
			if (!mc.world.getBlockState(pos).getBlock().equals(Blocks.BEDROCK) && pos.getY() == 0)
			{
				voidHoles.add(pos);
			}
		}
	}

	private List<BlockPos> getCircle(BlockPos loc, float r)
	{
		List<BlockPos> blocks = new ArrayList<>();
		int cx = loc.getX();
		int cz = loc.getZ();
		for (int x = cx - (int) r; x <= cx + r; x++)
		{
			for (int z = cz - (int) r; z <= cz + r; z++)
			{
				double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
				if (dist < r * r)
				{
					BlockPos l = new BlockPos(x, 0, z);
					blocks.add(l);
				}
			}
		}
		return blocks;
	}

	@SubscribeEvent
	public void renderWorld(RenderWorldLastEvent event)
	{
		if (mc.player == null || mc.world == null || voidHoles.isEmpty()) return;

		hue += rainbowSpeed.getIntValue() / 1000f;
		int rgb = Color.HSBtoRGB(hue, 1.0F, 1.0F);

		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;

		if (color.getEnumValue().equals("Rainbow"))
		{
			voidHoles.forEach(blockPos -> RenderUtil.drawBoxFromBlockpos(blockPos, r / 255f, g / 255f, b / 255f, alpha.getIntValue() / 255f));
		}
		else
		{
			voidHoles.forEach(blockPos -> RenderUtil.drawBoxFromBlockpos(blockPos, red.getIntValue() / 255f, green.getIntValue() / 255f, blue.getIntValue() / 255f, alpha.getIntValue() / 255f));
		}
	}
}
