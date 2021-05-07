package cat.yoink.yoinkhack.impl.module.misc;

import cat.yoink.yoinkhack.api.gui.clickgui.ClickGUI;
import cat.yoink.yoinkhack.api.module.Category;
import cat.yoink.yoinkhack.api.module.Module;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 * @since 8/30/2020
 */
public class GuiMove extends Module
{
	public GuiMove(String name, Category category, String description)
	{
		super(name, category, description);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{ // TODO: 8/30/2020 make this work
		if (!(mc.currentScreen instanceof GuiOptions) && !(mc.currentScreen instanceof GuiIngameMenu) && !(mc.currentScreen instanceof GuiScreenOptionsSounds) && !(mc.currentScreen instanceof GuiVideoSettings) && !(mc.currentScreen instanceof GuiContainer) && !(mc.currentScreen instanceof ClickGUI)) return;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) mc.player.rotationYaw = mc.player.rotationYaw - 5;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) mc.player.rotationYaw = mc.player.rotationYaw + 5;
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) mc.player.rotationPitch = (mc.player.rotationPitch - 5);
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) mc.player.rotationPitch = (mc.player.rotationPitch + 5);
		mc.player.movementInput.forwardKeyDown = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
		mc.player.movementInput.rightKeyDown = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
		mc.player.movementInput.leftKeyDown = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
		mc.player.movementInput.backKeyDown = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
	}
}
