package cat.yoink.yoinkhack.api.gui.clickgui.button.settings;

import cat.yoink.yoinkhack.api.gui.clickgui.button.SettingButton;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author yoink
 * @since 8/29/2020
 */
public class BindButton extends SettingButton
{
	private final Module module;
	private boolean binding;

	public BindButton(Module module, int x, int y, int w, int h)
	{
		super(module, x, y, w, h);
		this.module = module;
	}

	@Override
	public void render(int mX, int mY)
	{
		drawButton();

		drawButton(mX, mY);

		FontUtil.drawStringWithShadow("Bind", (float) (getX() + 6), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());

		if (binding)
		{
			FontUtil.drawStringWithShadow("...", (float) ((getX() + getW() - 6) - FontUtil.getStringWidth("...")), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
		}
		else
		{
			try
			{
				FontUtil.drawStringWithShadow(Keyboard.getKeyName(module.getBind()), (float) ((getX() + getW() - 6) - FontUtil.getStringWidth(Keyboard.getKeyName(module.getBind()))), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
			}
			catch (Exception e)
			{
				FontUtil.drawStringWithShadow("NONE", (float) ((getX() + getW() - 6) - FontUtil.getStringWidth("NONE")), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
			}
		}
	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			binding = !binding;
		}
	}

	@Override
	public void keyPress(int key)
	{
		if (binding)
		{
			if (key == Keyboard.KEY_DELETE || key == Keyboard.KEY_BACK || key == Keyboard.KEY_NONE)
			{
				getModule().setBind(Keyboard.KEYBOARD_SIZE);
			}
			else
			{
				getModule().setBind(key);
			}
			binding = false;
		}
	}
}
