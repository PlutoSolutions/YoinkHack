package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.mixin.mixins.accessor.IPlayerControllerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = PlayerControllerMP.class, priority = 999)
public class PlayerControllerMPMixin implements IPlayerControllerMP
{
	@Shadow
	private boolean isHittingBlock;

	@Inject(method = "resetBlockRemoving", at = @At(value = "HEAD"), cancellable = true)
	private void resetBlock(CallbackInfo ci)
	{
		if (Client.INSTANCE.moduleManager.getModuleByName("NoMineReset").isEnabled())
		{
			ci.cancel();
		}
	}

	@Inject(method = "getBlockReachDistance", at = @At("RETURN"), cancellable = true)
	private void getReachDistanceHook(final CallbackInfoReturnable<Float> distance)
	{
		if (Client.INSTANCE.moduleManager.getModuleByName("Reach").isEnabled())
		{
			distance.setReturnValue((float) Client.INSTANCE.settingManager.getSettingEasy("Reach", 0).getIntValue());
		}
	}

	@Override
	public boolean isHittingBlock()
	{
		return isHittingBlock;
	}
}
