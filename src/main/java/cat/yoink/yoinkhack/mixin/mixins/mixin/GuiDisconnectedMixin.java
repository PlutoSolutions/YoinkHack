package cat.yoink.yoinkhack.mixin.mixins.mixin;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author yoink
 * @since 9/3/2020
 */
@Mixin(GuiDisconnected.class)
public class GuiDisconnectedMixin extends GuiScreen
{
/*	@Shadow private int textHeight;

	@Inject(method = "initGui", at = @At("TAIL"))
	public void initGui(CallbackInfo ci)
	{
		buttonList.add(new GuiButton(100, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30) + 25, "Reconnect"));
	}

	@Inject(method = "actionPerformed", at = @At("HEAD"))
	public void actionPerformed(GuiButton button, CallbackInfo ci)
	{
		if (button.id == 100)
		{
			FMLClientHandler.instance().connectToServer(new GuiMultiplayer(new GuiMainMenu()), mc.getCurrentServerData());
		}
	}*/
}
