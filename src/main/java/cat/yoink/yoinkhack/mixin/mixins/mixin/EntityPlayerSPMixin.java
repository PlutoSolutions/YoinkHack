package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.impl.event.MoveEvent;
import cat.yoink.yoinkhack.impl.event.WalkEvent;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = EntityPlayerSP.class, priority = 999)
public class EntityPlayerSPMixin extends AbstractClientPlayer
{
	public EntityPlayerSPMixin(World p_i47378_2_, NetHandlerPlayClient p_i47378_3_)
	{
		super(p_i47378_2_, p_i47378_3_.getGameProfile());
	}


	@Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
	public void move(final AbstractClientPlayer player, final MoverType moverType, final double x, final double y, final double z)
	{
		MoveEvent event = new MoveEvent(moverType, x, y, z);
		MinecraftForge.EVENT_BUS.post(event);
		if (!event.isCanceled())
		{
			super.move(event.getType(), event.getX(), event.getY(), event.getZ());
		}
	}

	@Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
	public void onUpdateWalkingPlayer(CallbackInfo ci)
	{
		WalkEvent walkingUpdateEvent = new WalkEvent();
		MinecraftForge.EVENT_BUS.post(walkingUpdateEvent);

		if (walkingUpdateEvent.isCanceled()) ci.cancel();
	}
}
