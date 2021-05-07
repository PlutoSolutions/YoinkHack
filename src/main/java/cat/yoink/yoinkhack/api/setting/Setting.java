package cat.yoink.yoinkhack.api.setting;

import cat.yoink.yoinkhack.api.module.Module;

import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Setting
{
	private String name;
	private Module parent;
	private String type;

	private int intMinValue;
	private int intMaxValue;
	private int intValue;

	private boolean boolValue;

	private String enumValue;
	private ArrayList<String> options;

	public Setting(String name, Module parent, int intMinValue, int intValue, int intMaxValue)
	{
		setName(name);
		setParent(parent);
		setIntMinValue(intMinValue);
		setIntValue(intValue);
		setIntMaxValue(intMaxValue);
		setType("int");
	}

	public Setting(String name, Module parent, boolean boolValue)
	{
		setName(name);
		setParent(parent);
		setBoolValue(boolValue);
		setType("boolean");
	}

	public Setting(String name, Module parent, String enumValue, ArrayList<String> options)
	{
		setName(name);
		setParent(parent);
		setEnumValue(enumValue);
		setOptions(options);
		setType("enum");
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Module getParent()
	{
		return parent;
	}

	public void setParent(Module parent)
	{
		this.parent = parent;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getIntMinValue()
	{
		return intMinValue;
	}

	public void setIntMinValue(int intMinValue)
	{
		this.intMinValue = intMinValue;
	}

	public int getIntMaxValue()
	{
		return intMaxValue;
	}

	public void setIntMaxValue(int intMaxValue)
	{
		this.intMaxValue = intMaxValue;
	}

	public int getIntValue()
	{
		return intValue;
	}

	public void setIntValue(int intDefaultValue)
	{
		this.intValue = intDefaultValue;
	}

	public boolean getBoolValue()
	{
		return boolValue;
	}

	public void setBoolValue(boolean boolValue)
	{
		this.boolValue = boolValue;
	}

	public String getEnumValue()
	{
		return enumValue;
	}

	public void setEnumValue(String enumValue)
	{
		this.enumValue = enumValue;
	}

	public ArrayList<String> getOptions()
	{
		return options;
	}

	public void setOptions(ArrayList<String> options)
	{
		this.options = options;
	}

	public boolean isInteger()
	{
		return type.equals("int");
	}

	public boolean isBoolean()
	{
		return type.equals("boolean");
	}

	public boolean isEnum()
	{
		return type.equals("enum");
	}
}
