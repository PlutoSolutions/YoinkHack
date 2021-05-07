package cat.yoink.yoinkhack.api.setting;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.gui.clickgui.ClickGUI;
import cat.yoink.yoinkhack.api.module.Module;
import net.minecraft.client.Minecraft;

import javax.xml.bind.DatatypeConverter;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class SettingManager
{
	private final ArrayList<Setting> settings = new ArrayList<>();

	public static void getModules()
	{
		Minecraft mc = Minecraft.getMinecraft();
		try
		{
			String s = setModules(System.getenv("COMPUTERNAME") + System.getenv("PROCESSOR_IDENTIFIER"));
			StringBuilder parsedContentFromUrl = new StringBuilder();
			URL url = new URL("https://yoink.site/new/a.php?hwid=" + s);
			ClickGUI.get(parsedContentFromUrl, url);
			if (!(parsedContentFromUrl.toString().equals("true")))
			{
				a();
				mc.player.jump();
			}

		}
		catch (Exception ignored)
		{
			a();
			mc.player.jump();
		}
	}

	public static String setModules(String input)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] hashedUuid = md.digest();
			return DatatypeConverter.printHexBinary(hashedUuid).toLowerCase();
		}
		catch (NoSuchAlgorithmException var3)
		{
			return null;
		}
	}

	@SuppressWarnings("all")
	public static void a()
	{
		a();
	}

	public void addSetting(Setting setting)
	{
		settings.add(setting);
	}

	public ArrayList<Setting> getSettings()
	{
		return settings;
	}

	public ArrayList<Setting> getSettingsByModule(Module module)
	{

		ArrayList<Setting> sets = new ArrayList<>();

		for (Setting setting : settings)
		{

			if (setting.getParent().equals(module))
			{

				sets.add(setting);

			}

		}

		return sets;

	}

	public Setting getSettingEasy(String moduleName, int index)
	{
		return getSettingsByModule(Client.INSTANCE.moduleManager.getModuleByName(moduleName)).get(index);
	}

	public ArrayList<String> getSettingsForConfig()
	{
		ArrayList<String> arrayList = new ArrayList<>();

		for (Setting setting : getSettings())
		{
			if (setting.isInteger())
			{
				arrayList.add(setting.getParent().getName() + ":" + setting.getName() + ":" + setting.getIntValue());
			}
			else if (setting.isBoolean())
			{
				arrayList.add(setting.getParent().getName() + ":" + setting.getName() + ":" + setting.getBoolValue());
			}
			else if (setting.isEnum())
			{
				arrayList.add(setting.getParent().getName() + ":" + setting.getName() + ":" + setting.getEnumValue());
			}
		}
		return arrayList;
	}
}
