package cat.yoink.yoinkhack.mixin.mixins.accessor;

import net.minecraft.item.ItemStack;

/**
 * @author yoink
 * @since 8/28/2020
 */
public interface IItemRenderer
{
	float getPrevEquippedProgressMainHand();

	void setEquippedProgressMainHand(float progress);

	float getPrevEquippedProgressOffHand();

	void setEquippedProgressOffHand(float progress);

	void setItemStackMainHand(ItemStack stack);

	void setItemStackOffHand(ItemStack stack);
}