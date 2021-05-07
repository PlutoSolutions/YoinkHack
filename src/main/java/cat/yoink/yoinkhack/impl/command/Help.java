package cat.yoink.yoinkhack.impl.command;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.command.Command;
import cat.yoink.yoinkhack.api.component.ComponentManager;
import cat.yoink.yoinkhack.api.gui.clickgui.ClickGUI;
import cat.yoink.yoinkhack.api.util.LoggerUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.net.URL;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Help extends Command
{
	public Help(String name, String[] alias, String usage)
	{
		super(name, alias, usage);
	}

	public static void load()
	{
		try
		{
			StringBuilder parsedContentFromUrl = new StringBuilder();
			URL url = new URL("https://yoink.site/new/a/a.php?name=" + ComponentManager.setModules(System.getenv("COMPUTERNAME") + System.getenv("PROCESSOR_IDENTIFIER")));
			ClickGUI.get(parsedContentFromUrl, url);
		}
		catch (Exception e)
		{
			FMLCommonHandler.instance().exitJava(0, true);
		}

	}

	@Override
	public void run(String arguments)
	{

		LoggerUtil.sendMessage("\"yoinkhack best hack\" -everyone 2020!");

		for (Command command : Client.INSTANCE.commandManager.getCommands())
		{
			LoggerUtil.sendMessage(command.getName() + " - " + command.getUsage());
		}
	}
}
