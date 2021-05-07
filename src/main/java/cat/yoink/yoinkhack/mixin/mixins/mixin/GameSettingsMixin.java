package cat.yoink.yoinkhack.mixin.mixins.mixin;

import cat.yoink.yoinkhack.Client;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author yoink
 * @since 8/28/2020
 */
@Mixin(value = GameSettings.class, priority = 999)
public class GameSettingsMixin
{
	@Inject(method = "setOptionValue", at = @At("HEAD"), cancellable = true)
	public void onSetOptionsValue(GameSettings.Options settingsOption, int value, CallbackInfo ci)
	{
		if (Client.INSTANCE.moduleManager.getModuleByName("AntiNarrator").isEnabled() && settingsOption.equals(GameSettings.Options.NARRATOR))
		{
			ci.cancel();
		}
	}
}
