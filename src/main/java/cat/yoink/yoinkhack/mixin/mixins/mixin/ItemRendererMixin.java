package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.mixin.mixins.accessor.IItemRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = ItemRenderer.class, priority = 999)
public abstract class ItemRendererMixin implements IItemRenderer
{
	@Accessor
	@Override
	public abstract float getPrevEquippedProgressMainHand();

	@Accessor
	@Override
	public abstract void setEquippedProgressMainHand(float f);

	@Accessor
	@Override
	public abstract float getPrevEquippedProgressOffHand();

	@Accessor
	@Override
	public abstract void setEquippedProgressOffHand(float f);

	@Accessor
	@Override
	public abstract void setItemStackMainHand(ItemStack stack);

	@Accessor
	@Override
	public abstract void setItemStackOffHand(ItemStack stack);

	@Inject(method = "transformEatFirstPerson", at = @At("HEAD"), cancellable = true)
	private void transformEatFirstPerson(float p_187454_1_, EnumHandSide hand, ItemStack stack, CallbackInfo ci)
	{
		if (Client.INSTANCE.settingManager.getSettingEasy("ViewModelChanger", 1).getBoolValue()) ci.cancel();
	}

	@Inject(method = "transformSideFirstPerson", at = @At("HEAD"))
	public void transformSideFirstPerson(EnumHandSide hand, float p_187459_2_, CallbackInfo ci)
	{
		if (Client.INSTANCE.moduleManager.getModuleByName("ViewModelChanger").isEnabled())
		{
			if (hand.equals(EnumHandSide.RIGHT))
			{
				GlStateManager.translate(Client.INSTANCE.settingManager.getSettingEasy("ViewModelChanger", 4).getIntValue() / 10f, Client.INSTANCE.settingManager.getSettingEasy("ViewModelChanger", 5).getIntValue() / 10f, Client.INSTANCE.settingManager.getSettingEasy("ViewModelChanger", 6).getIntValue() / 10f);
			}
			else
			{
				GlStateManager.translate(Client.INSTANCE.settingManager.getSettingEasy("ViewModelChanger", 7).getIntValue() / 10f, Client.INSTANCE.settingManager.getSettingEasy("ViewModelChanger", 8).getIntValue() / 10f, Client.INSTANCE.settingManager.getSettingEasy("ViewModelChanger", 9).getIntValue() / 10f);
			}
		}
	}
}
