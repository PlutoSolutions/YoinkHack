package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.module.Module;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.Comparator;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Arraylist extends Component
{
	private int X;
	private int Y;
	private int mW;
	private int miW;
	private int fH;
	private int C;
	private int oW;
	private int d;
	private float r = 0;

	public Arraylist(String name)
	{
		super(name);
	}

	@Override
	public void render()
	{
		if (Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getEnumValue().equalsIgnoreCase("box"))
		{
			switch (Client.INSTANCE.settingManager.getSettingEasy(getName(), 1).getEnumValue().toLowerCase())
			{
				case "top-left":
					btl();
					break;
				case "top-right":
					btr();
					break;
				case "bot-left":
					bbl();
					break;
				case "bot-right":
					bbr();
					break;
				default:
					break;
			}
		}
		else if (Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getEnumValue().equalsIgnoreCase("rainbow"))
		{
			switch (Client.INSTANCE.settingManager.getSettingEasy(getName(), 1).getEnumValue().toLowerCase())
			{
				case "top-left":
					mtl();
					break;
				case "top-right":
					mtr();
					break;
				case "bot-left":
					mbl();
					break;
				case "bot-right":
					mbr();
					break;
				default:
					break;
			}
		}
		else if (Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getEnumValue().equalsIgnoreCase("new"))
		{
			switch (Client.INSTANCE.settingManager.getSettingEasy(getName(), 1).getEnumValue().toLowerCase())
			{
				case "top-left":
					ntl();
					break;
				case "top-right":
//					ntr();
					break;
				case "bot-left":
//					nbl();
					break;
				case "bot-right":
//					nbr();
					break;
				default:
					break;
			}
		}
	}

	private void ntl()
	{
		r += 0.001;

		final int[] X = {getX()};
		final int[] Y = {getY()};
		final float[] rainbow = {r};
		final int[] maxW = {0};
		final int[] count = {0};

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> rRender(module.getName()))).forEach(module ->
		{
			if (maxW[0] < FontUtil.getStringWidth(module.getName()))
			{
				maxW[0] = FontUtil.getStringWidth(module.getName());
			}

			int c = Color.HSBtoRGB(rainbow[0], 0.9f, 0.85f);

			Gui.drawRect(X[0], Y[0], X[0] + FontUtil.getStringWidth(module.getName()) + 2, Y[0] + FontUtil.getFontHeight() + 4, new Color(0x95000000, true).getRGB());
			Gui.drawRect(X[0] + FontUtil.getStringWidth(module.getName()), Y[0], X[0] + FontUtil.getStringWidth(module.getName()) + 2, Y[0] + FontUtil.getFontHeight() + 4, c);
			FontUtil.drawStringWithShadow(module.getName(), X[0] + 2, Y[0] + 2, c);

			rainbow[0] -= 0.06f;
			Y[0] += FontUtil.getFontHeight() + 2;
			count[0]++;

		});

		setWidth(maxW[0]);
		setHeight((int) (count[0] * 12.5) - 3);
	}

	private void btl()
	{
		oW = 0;

		d = 0;

		fH = 13;

		X = getX();
		Y = getY();

		C = 0;

		mW = 0;
		miW = 10000;

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> rRender(module.getName()))).forEach(module ->
		{

			int w = FontUtil.getStringWidth(module.getName());

			if (mW < w) mW = w;
			if (miW > w) miW = w;

			d = w - oW;

			if (oW != 0) Gui.drawRect(X + w + 3 - d, Y - 1, X + w + 4 - 1, Y, 0xffffffff);
			renderOne(module, w);

		});

		Gui.drawRect(getX() - 4, getY() - 1, getX() + mW + 4, getY(), 0xffffffff);
		Gui.drawRect(X - 4, Y, X + miW + 4, Y + 1, 0xffffffff);


		setWidth(mW);
		setHeight(C * fH);
	}

	private void renderOne(Module module, int w)
	{
		Gui.drawRect(X - 3, Y, X - 4, Y + fH, 0xffffffff);
		Gui.drawRect(X + w + 3, Y, X + w + 4, Y + fH, 0xffffffff);
		Gui.drawRect(X - 3, Y, X + w + 3, Y + fH, 0xff010101);

		FontUtil.drawStringWithShadow(module.getName(), X, Y + 1, 0xffffffff);


		oW = w;
		Y += fH;
		C++;
	}

	private void btr()
	{
		oW = 0;

		d = 0;

		fH = 13;

		X = getX();
		Y = getY();

		C = 0;

		mW = 0;
		miW = 10000;

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> rRender(module.getName()))).forEach(module ->
		{

			int w = FontUtil.getStringWidth(module.getName());

			if (mW < w) mW = w;
			if (miW > w) miW = w;

			d = w - oW;

			X = getX() + mW - w;

			if (oW != 0) Gui.drawRect(X - 3 + d, Y - 1, X - 3, Y, 0xffffffff);
			renderOne(module, w);

		});

		Gui.drawRect(getX() - 4, getY() - 1, getX() + mW + 4, getY(), 0xffffffff);
		Gui.drawRect(X - 4, Y, X + miW + 4, Y + 1, 0xffffffff);


		setWidth(mW);
		setHeight(C * fH);
	}

	private void bbl()
	{
		oW = 0;

		d = 0;

		fH = 13;

		X = getX();
		Y = getY();

		C = 0;

		mW = 0;
		miW = 10000;

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName()))).forEach(module ->
		{

			int w = FontUtil.getStringWidth(module.getName());

			if (mW < w) mW = w;
			if (miW > w) miW = w;

			d = w - oW;

			if (oW != 0) Gui.drawRect(X + w + 3 - d, Y - 1, X + w + 4, Y, 0xffffffff);
			renderOne(module, w);

		});

		Gui.drawRect(getX() - 4, getY() - 1, getX() + miW + 4, getY(), 0xffffffff);
		Gui.drawRect(X - 4, Y, X + mW + 4, Y + 1, 0xffffffff);


		setWidth(mW);
		setHeight(C * fH);
	}

	private void bbr()
	{
		oW = 0;

		d = 0;

		fH = 13;

		X = getX();
		Y = getY();

		C = 0;

		mW = 0;
		miW = 10000;

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName()))).forEach(module ->
		{

			int w = FontUtil.getStringWidth(module.getName());

			if (mW < w) mW = w;
			if (miW > w) miW = w;

			d = w - oW;

			X = getX() + miW - w;

			if (oW != 0) Gui.drawRect(X - 3 + d, Y - 1, X - 4, Y, 0xffffffff);
			renderOne(module, w);

		});

		Gui.drawRect(getX() - 4, getY() - 1, getX() + miW + 4, getY(), 0xffffffff);
		Gui.drawRect(X - 4, Y, X + mW + 4, Y + 1, 0xffffffff);


		setWidth(mW);
		setHeight(C * fH);
	}

	private void mtl()
	{
		r += 0.001;

		final int[] X = {getX()};
		final int[] Y = {getY()};
		final float[] rainbow = {r};
		final int[] maxW = {0};
		final int[] count = {0};

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> rRender(module.getName()))).forEach(module ->
		{
			if (maxW[0] < FontUtil.getStringWidth(module.getName()))
			{
				maxW[0] = FontUtil.getStringWidth(module.getName());
			}

			int c = Color.HSBtoRGB(rainbow[0], 0.9f, 0.85f);
			FontUtil.drawStringWithShadow(module.getName(), X[0], Y[0], c);

			rainbow[0] -= 0.06f;
			Y[0] += 12.5;
			count[0]++;

		});

		setWidth(maxW[0]);
		setHeight((int) (count[0] * 12.5) - 3);
	}

	private void mtr()
	{
		r += 0.001;

		final int[] X = {getX()};
		final int[] Y = {getY()};
		final float[] rainbow = {r};
		final int[] maxW = {0};
		final int[] count = {0};

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> rRender(module.getName()))).forEach(module ->
		{
			if (maxW[0] < FontUtil.getStringWidth(module.getName()))
			{
				maxW[0] = FontUtil.getStringWidth(module.getName());
			}

			renderTwo(X, Y, rainbow, maxW, count, module);

		});

		setWidth(maxW[0]);
		setHeight((int) (count[0] * 12.5));
	}

	private void mbl()
	{
		r += 0.001;

		final int[] X = {getX()};
		final int[] Y = {getY()};
		final float[] rainbow = {r};
		final int[] maxW = {0};
		final int[] count = {0};

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName()))).forEach(module ->
		{
			if (maxW[0] < FontUtil.getStringWidth(module.getName()))
			{
				maxW[0] = FontUtil.getStringWidth(module.getName());
			}

			int c = Color.HSBtoRGB(rainbow[0], 0.9f, 0.85f);
			FontUtil.drawStringWithShadow(module.getName(), X[0], Y[0], c);

			rainbow[0] -= 0.06f;
			Y[0] += 12.5;
			count[0]++;

		});

		setWidth(maxW[0]);
		setHeight((int) (count[0] * 12.5) - 3);
	}

	private void mbr()
	{
		r += 0.001;

		final int[] X = {getX()};
		final int[] Y = {getY()};
		final float[] rainbow = {r};
		final int[] maxW = {0};
		final int[] count = {0};

		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName()))).forEach(module ->
		{
			if (maxW[0] < FontUtil.getStringWidth(module.getName()))
			{
				maxW[0] = FontUtil.getStringWidth(module.getName());
			}
		});


		Client.INSTANCE.moduleManager.getModules().stream().filter(Module::isDrawn).filter(Module::isEnabled).sorted(Comparator.comparing(module -> FontUtil.getStringWidth(module.getName()))).forEach(module -> renderTwo(X, Y, rainbow, maxW, count, module));

		setWidth(maxW[0]);
		setHeight((int) (count[0] * 12.5));
	}

	private void renderTwo(int[] x, int[] y, float[] rainbow, int[] maxW, int[] count, Module module)
	{
		int c = Color.HSBtoRGB(rainbow[0], 0.9f, 0.85f);
		FontUtil.drawStringWithShadow(module.getName(), x[0] + maxW[0] - FontUtil.getStringWidth(module.getName()), y[0], c);

		rainbow[0] -= 0.06f;
		y[0] += 12.5;
		count[0]++;
	}


	private int rRender(String text)
	{
		return 1000 - FontUtil.getStringWidth(text);
	}


}
