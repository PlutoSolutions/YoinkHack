package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = EntityRenderer.class, priority = 999)
public class EntityRendererMixin
{
	@Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
	public List<Entity> getEntitiesInAABBexcluding(WorldClient worldClient, Entity entityIn, AxisAlignedBB boundingBox, Predicate<? super Entity> predicate)
	{
		return Client.INSTANCE.moduleManager.getModuleByName("NoEntityTrace").isEnabled() ? Client.INSTANCE.settingManager.getSettingEasy("NoEntityTrace", 0).getBoolValue() ? Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemPickaxe ? new ArrayList<>() : worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate) : new ArrayList<>() : worldClient.getEntitiesInAABBexcluding(entityIn, boundingBox, predicate);
	}
}
