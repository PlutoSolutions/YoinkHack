package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.buttons.Button;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = GuiMainMenu.class, priority = 999)
public class GuiMainMenuMixin extends GuiScreen
{
	private boolean drawText;

	@Inject(method = "initGui", at = @At("HEAD"))
	public void initGui(CallbackInfo ci)
	{
		drawText = false;

		Button b1 = Client.INSTANCE.buttonManager.getButtonByID(0);
		Button b2 = Client.INSTANCE.buttonManager.getButtonByID(1);
		Button b3 = Client.INSTANCE.buttonManager.getButtonByID(2);
		Button b4 = Client.INSTANCE.buttonManager.getButtonByID(3);

		buttonList.add(new GuiButton(500, width / 2 - 200, height - 25, 98, 20, b1.getName()));
		buttonList.add(new GuiButton(501, width / 2 - 100, height - 25, 98, 20, b2.getName()));
		buttonList.add(new GuiButton(502, width / 2 + 2, height - 25, 98, 20, b3.getName()));
		buttonList.add(new GuiButton(503, width / 2 + 102, height - 25, 98, 20, b4.getName()));

		buttonList.add(new GuiButton(504, width - 34, 2, 30, 20, "..."));
	}

	@Inject(method = "actionPerformed", at = @At("HEAD"))
	public void actionPerformed(GuiButton button, CallbackInfo callbackInfo)
	{
		if (button.id == 504)
		{
			mc.displayGuiScreen(Client.INSTANCE.buttonGUI);
			return;
		}

		Button b1 = Client.INSTANCE.buttonManager.getButtonByID(0);
		Button b2 = Client.INSTANCE.buttonManager.getButtonByID(1);
		Button b3 = Client.INSTANCE.buttonManager.getButtonByID(2);
		Button b4 = Client.INSTANCE.buttonManager.getButtonByID(3);

		switch (button.id)
		{
			case 500:
			{
				ServerData serverData = new ServerData(b1.getName(), b1.getIp(), false);
				try
				{
					net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(new GuiMultiplayer(this), serverData);
				}
				catch (Exception ignored)
				{
					drawText = true;
				}
				break;
			}
			case 501:
			{
				ServerData serverData = new ServerData(b2.getName(), b2.getIp(), false);
				try
				{
					net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(new GuiMultiplayer(this), serverData);
				}
				catch (Exception ignored)
				{
					drawText = true;
				}
				break;
			}
			case 502:
			{
				ServerData serverData = new ServerData(b3.getName(), b3.getIp(), false);
				try
				{
					net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(new GuiMultiplayer(this), serverData);
				}
				catch (Exception ignored)
				{
					drawText = true;
				}
				break;
			}
			case 503:
			{
				ServerData serverData = new ServerData(b4.getName(), b4.getIp(), false);
				try
				{
					net.minecraftforge.fml.client.FMLClientHandler.instance().connectToServer(new GuiMultiplayer(this), serverData);
				}
				catch (Exception ignored)
				{
					drawText = true;
				}
				break;
			}
			default:
				break;
		}
	}

	@Inject(method = "drawScreen", at = @At("TAIL"))
	public void drawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci)
	{
		if (drawText)
		{
			String message = "You have to join a server first for this to work (Forge >:c)";
			mc.fontRenderer.drawStringWithShadow(message, (width / 2f) - (mc.fontRenderer.getStringWidth(message) / 2f), 2, 0xffffffff);
		}
	}
}
