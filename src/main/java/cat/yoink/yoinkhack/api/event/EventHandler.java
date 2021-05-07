package cat.yoink.yoinkhack.api.event;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.macro.Macro;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.impl.event.PacketEvent;
import cat.yoink.yoinkhack.impl.event.PopEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class EventHandler
{
	public EventHandler()
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onChatSend(ClientChatEvent event)
	{
		if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null) return;

		if (event.getMessage().startsWith(Client.INSTANCE.commandManager.getPrefix()))
		{
			event.setCanceled(true);
			Minecraft.getMinecraft().ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
			Client.INSTANCE.commandManager.runCommand(event.getMessage());
		}
	}


	@SubscribeEvent
	public void onKey(InputEvent.KeyInputEvent event)
	{
		if (Keyboard.getEventKeyState())
		{
			for (Module module : Client.INSTANCE.moduleManager.getModules())
			{
				if (module.getBind() == Keyboard.getEventKey())
				{
					module.toggle();
				}
			}

			for (Macro macro : Client.INSTANCE.macroManager.getMacros())
			{
				if (macro.getKey() == Keyboard.getEventKey())
				{
					macro.run();
				}
			}
		}
	}


	@SubscribeEvent
	public void onRender(RenderGameOverlayEvent event)
	{
		if (Minecraft.getMinecraft().player == null || Minecraft.getMinecraft().world == null || !event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT))
		{
			return;
		}

		for (Component component : Client.INSTANCE.componentManager.getComponents())
		{
			if (Client.INSTANCE.moduleManager.getModuleByName(component.getName()).isEnabled())
			{
				component.render();
			}
		}
	}

	@SubscribeEvent
	public void onPop(PacketEvent.Receive event)
	{
		if (event.getPacket() instanceof SPacketEntityStatus)
		{
			SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
			if (packet.getOpCode() == 35)
			{
				MinecraftForge.EVENT_BUS.post(new PopEvent(packet.getEntity(Minecraft.getMinecraft().world)));
			}
		}
	}
}
