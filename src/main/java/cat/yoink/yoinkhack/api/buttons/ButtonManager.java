package cat.yoink.yoinkhack.api.buttons;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ButtonManager
{
	private final ArrayList<Button> buttons = new ArrayList<>();

	public ButtonManager()
	{
		buttons.add(new Button("9b9t", "9b9t.com", 0));
		buttons.add(new Button("2b2t", "2b2t.org", 1));
		buttons.add(new Button("2bpvp", "2b2tpvp.net", 2));
		buttons.add(new Button("cpvp", "crystalpvp.cc", 3));
	}

	public ArrayList<Button> getButtons()
	{
		return buttons;
	}

	public Button getButtonByID(int id)
	{
		for (Button button : getButtons())
		{
			if (button.getId() == id) return button;
		}
		return null;
	}

	public ArrayList<String> getButtonsForConfig()
	{
		ArrayList<String> arrayList = new ArrayList<>();

		for (Button button : getButtons())
		{
			arrayList.add(button.getId() + ":" + button.getName() + ":" + button.getIp());
		}
		return arrayList;
	}
}
