package cat.yoink.yoinkhack.impl.module.render;

import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.api.util.RenderUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
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
public class BlockESP extends Module
{

	private final Setting web = new Setting("Web", this, true);
	private final Setting skull = new Setting("Skull", this, true);
	private final Setting range = new Setting("Range", this, 1, 6, 20);
	private final Setting color = new Setting("Color", this, "Static", new ArrayList<>(Arrays.asList("Static", "Rainbow")));
	private final Setting red = new Setting("Red", this, 0, 255, 255);
	private final Setting green = new Setting("Green", this, 0, 20, 255);
	private final Setting blue = new Setting("Blue", this, 0, 20, 255);
	private final Setting alpha = new Setting("Alpha", this, 0, 100, 255);
	private final Setting rainbowSpeed = new Setting("RainbowSpeed", this, 0, 5, 10);

	private final List<BlockPos> blocks = new ArrayList<>();
	private float hue = 0f;

	public BlockESP(String name, Category category, String description)
	{
		super(name, category, description);

		addSetting(web);
		addSetting(skull);
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
		if (mc.player == null || mc.world == null) return;

		blocks.clear();
		Vec3i playerPos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);

		for (int x = playerPos.getX() - range.getIntValue(); x < playerPos.getX() + range.getIntValue(); x++)
		{
			for (int z = playerPos.getZ() - range.getIntValue(); z < playerPos.getZ() + range.getIntValue(); z++)
			{
				for (int y = playerPos.getY() + range.getIntValue(); y > playerPos.getY() - range.getIntValue(); y--)
				{
					BlockPos blockPos = new BlockPos(x, y, z);
					if (web.getBoolValue() && mc.world.getBlockState(blockPos).getBlock().equals(Blocks.WEB) || skull.getBoolValue() && mc.world.getBlockState(blockPos).getBlock().equals(Blocks.SKULL))
					{
						blocks.add(blockPos);
					}
				}
			}
		}
	}


	@SubscribeEvent
	public void onRender(RenderWorldLastEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		hue += rainbowSpeed.getIntValue() / 1000f;
		int rgb = Color.HSBtoRGB(hue, 1.0F, 1.0F);

		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;

		if (color.getEnumValue().equals("Rainbow"))
		{
			blocks.forEach(blockPos -> RenderUtil.drawBoxFromBlockpos(blockPos, r / 255f, g / 255f, b / 255f, alpha.getIntValue() / 255f));
		}
		else
		{
			blocks.forEach(blockPos -> RenderUtil.drawBoxFromBlockpos(blockPos, red.getIntValue() / 255f, green.getIntValue() / 255f, blue.getIntValue() / 255f, alpha.getIntValue() / 255f));
		}
	}
}
