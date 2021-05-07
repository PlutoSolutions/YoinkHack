package cat.yoink.yoinkhack.mixin.mixins.mixin;

import net.minecraft.client.gui.*;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = GuiIngameMenu.class, priority = 999)
public class GuiIngameMenuMixin extends GuiScreen
{
	@Inject(method = "initGui", at = @At("TAIL"))
	public void initGui(CallbackInfo ci)
	{
		if (!mc.isIntegratedServerRunning())
		{
			buttonList.add(new GuiButton(3794, width / 2 - 100, height / 4 + 120 + -16 + 3 + 20, "Reconnect"));
		}
	}

	@Inject(method = "actionPerformed", at = @At("HEAD"))
	protected void actionPerformed(GuiButton button, CallbackInfo ci)
	{
		if (button.id == 3794 && !mc.isIntegratedServerRunning())
		{
			FMLClientHandler.instance().connectToServer(new GuiMultiplayer(new GuiMainMenu()), mc.getCurrentServerData());
		}
	}
}
