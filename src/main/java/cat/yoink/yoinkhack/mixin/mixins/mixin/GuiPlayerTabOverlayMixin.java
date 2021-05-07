package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = GuiPlayerTabOverlay.class, priority = 999)
public class GuiPlayerTabOverlayMixin
{
	@Inject(method = "getPlayerName", at = @At("RETURN"), cancellable = true)
	public void getPlayerName(NetworkPlayerInfo networkPlayerInfoIn, CallbackInfoReturnable<String> cir)
	{
		String name = cir.getReturnValue();
		if (Client.INSTANCE.friendManager.isFriend(name))
		{
			cir.setReturnValue(ChatFormatting.BLUE + name);
		}
	}
}