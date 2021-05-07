package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = RenderPlayer.class, priority = 999)
public class RenderPlayerMixin
{
	@Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)
	public void renderLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo ci)
	{
		if (Client.INSTANCE.moduleManager.getModuleByName("Nametags").isEnabled()) ci.cancel();
	}
}
