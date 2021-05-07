package cat.yoink.yoinkhack.api.gui.button;

import cat.yoink.yoinkhack.Client;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.io.IOException;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ButtonGUI extends GuiScreen
{
	private GuiTextField name1;
	private GuiTextField ip1;
	private GuiTextField name2;
	private GuiTextField ip2;
	private GuiTextField name3;
	private GuiTextField ip3;
	private GuiTextField name4;
	private GuiTextField ip4;

	@Override
	public void initGui()
	{
		name1 = new GuiTextField(0, mc.fontRenderer, width / 2 - 202, height / 2 - 22, 98, 20);
		ip1 = new GuiTextField(1, mc.fontRenderer, width / 2 - 202, height / 2 + 2, 98, 20);
		name2 = new GuiTextField(2, mc.fontRenderer, width / 2 - 100, height / 2 - 22, 98, 20);
		ip2 = new GuiTextField(3, mc.fontRenderer, width / 2 - 100, height / 2 + 2, 98, 20);
		name3 = new GuiTextField(4, mc.fontRenderer, width / 2 + 2, height / 2 - 22, 98, 20);
		ip3 = new GuiTextField(5, mc.fontRenderer, width / 2 + 2, height / 2 + 2, 98, 20);
		name4 = new GuiTextField(6, mc.fontRenderer, width / 2 + 104, height / 2 - 22, 98, 20);
		ip4 = new GuiTextField(7, mc.fontRenderer, width / 2 + 104, height / 2 + 2, 98, 20);

		name1.setText(Client.INSTANCE.buttonManager.getButtonByID(0).getName());
		ip1.setText(Client.INSTANCE.buttonManager.getButtonByID(0).getIp());
		name2.setText(Client.INSTANCE.buttonManager.getButtonByID(1).getName());
		ip2.setText(Client.INSTANCE.buttonManager.getButtonByID(1).getIp());
		name3.setText(Client.INSTANCE.buttonManager.getButtonByID(2).getName());
		ip3.setText(Client.INSTANCE.buttonManager.getButtonByID(2).getIp());
		name4.setText(Client.INSTANCE.buttonManager.getButtonByID(3).getName());
		ip4.setText(Client.INSTANCE.buttonManager.getButtonByID(3).getIp());

		buttonList.add(new GuiButton(69, width / 2 - 204, height / 2 + 26, 204, 20, "Exit"));
		buttonList.add(new GuiButton(420, width / 2, height / 2 + 26, 204, 20, "Save"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();

		mc.fontRenderer.drawStringWithShadow("Button 1", width / 2f - 202, height / 2f - 35, 0xffffffff);
		mc.fontRenderer.drawStringWithShadow("Button 2", width / 2f - 100, height / 2f - 35, 0xffffffff);
		mc.fontRenderer.drawStringWithShadow("Button 3", width / 2f + 2, height / 2f - 35, 0xffffffff);
		mc.fontRenderer.drawStringWithShadow("Button 4", width / 2f + 102, height / 2f - 35, 0xffffffff);

		name1.drawTextBox();
		ip1.drawTextBox();
		name2.drawTextBox();
		ip2.drawTextBox();
		name3.drawTextBox();
		ip3.drawTextBox();
		name4.drawTextBox();
		ip4.drawTextBox();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		name1.mouseClicked(mouseX, mouseY, mouseButton);
		ip1.mouseClicked(mouseX, mouseY, mouseButton);
		name2.mouseClicked(mouseX, mouseY, mouseButton);
		ip2.mouseClicked(mouseX, mouseY, mouseButton);
		name3.mouseClicked(mouseX, mouseY, mouseButton);
		ip3.mouseClicked(mouseX, mouseY, mouseButton);
		name4.mouseClicked(mouseX, mouseY, mouseButton);

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode)
	{
		name1.textboxKeyTyped(typedChar, keyCode);
		ip1.textboxKeyTyped(typedChar, keyCode);
		name2.textboxKeyTyped(typedChar, keyCode);
		ip2.textboxKeyTyped(typedChar, keyCode);
		name3.textboxKeyTyped(typedChar, keyCode);
		ip3.textboxKeyTyped(typedChar, keyCode);
		name4.textboxKeyTyped(typedChar, keyCode);
		ip4.textboxKeyTyped(typedChar, keyCode);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 69)
		{
			mc.displayGuiScreen(null);
		}
		else if (button.id == 420)
		{
			Client.INSTANCE.buttonManager.getButtonByID(0).setName(name1.getText());
			Client.INSTANCE.buttonManager.getButtonByID(0).setIp(ip1.getText());
			Client.INSTANCE.buttonManager.getButtonByID(1).setName(name2.getText());
			Client.INSTANCE.buttonManager.getButtonByID(1).setIp(ip2.getText());
			Client.INSTANCE.buttonManager.getButtonByID(2).setName(name3.getText());
			Client.INSTANCE.buttonManager.getButtonByID(2).setIp(ip3.getText());
			Client.INSTANCE.buttonManager.getButtonByID(3).setName(name4.getText());
			Client.INSTANCE.buttonManager.getButtonByID(3).setIp(ip4.getText());
			mc.displayGuiScreen(null);
		}
	}
}
