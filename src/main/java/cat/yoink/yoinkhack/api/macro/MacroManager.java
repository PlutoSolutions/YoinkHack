package cat.yoink.yoinkhack.api.macro;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class MacroManager
{
	private final ArrayList<Macro> macros = new ArrayList<>();

	public ArrayList<Macro> getMacros()
	{
		return macros;
	}

	public void addMacro(Macro m)
	{
		macros.add(m);
	}

	public void removeMacro(Macro m)
	{
		macros.remove(m);
	}

	public Macro getMacroByKey(int key)
	{
		return getMacros().stream().filter(mm -> mm.getKey() == key).findFirst().orElse(null);
	}

	public ArrayList<String> getMacrosForConfig()
	{
		ArrayList<String> arrayList = new ArrayList<>();
		for (Macro macro : getMacros())
		{
			arrayList.add(macro.getKey() + ":" + macro.getMessage());
		}
		return arrayList;

	}
}
