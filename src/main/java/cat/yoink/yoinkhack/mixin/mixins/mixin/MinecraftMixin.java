package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.mixin.mixins.accessor.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = Minecraft.class, priority = 999)
public class MinecraftMixin implements IMinecraft
{
	@Shadow
	public PlayerControllerMP playerController;
	@Shadow
	@Final
	private Timer timer;

	@Redirect(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0))
	private boolean getIsHittingBlock(PlayerControllerMP playerControllerMP)
	{
		if (Client.INSTANCE.moduleManager.getModuleByName("MultiTask").isEnabled())
		{
			return false;
		}
		return playerController.getIsHittingBlock();
	}

	@Redirect(method = "sendClickBlockToController", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
	private boolean isHandActive(EntityPlayerSP entityPlayerSP)
	{
		if (Client.INSTANCE.moduleManager.getModuleByName("MultiTask").isEnabled())
		{
			return false;
		}
		return entityPlayerSP.isHandActive();
	}

	@Override
	public Timer getTimer()
	{
		return timer;
	}
}
