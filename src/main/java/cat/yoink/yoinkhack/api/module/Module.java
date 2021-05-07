package cat.yoink.yoinkhack.api.module;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.setting.Setting;
import cat.yoink.yoinkhack.impl.event.ToggleEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class Module
{
	public final Minecraft mc = Minecraft.getMinecraft();
	private String name;
	private Category category;
	private String description;
	private int bind;
	private boolean enabled;
	private boolean drawn;
	private boolean binding;
	private boolean expanded;

	public Module(String name, Category category)
	{
		this.name = name;
		this.category = category;
		this.description = null;
		this.bind = Keyboard.KEYBOARD_SIZE;
		this.enabled = false;
		this.drawn = true;
		this.expanded = false;
		this.binding = false;
	}

	public Module(String name, Category category, String description)
	{
		this.name = name;
		this.category = category;
		this.description = description;
		this.bind = Keyboard.KEYBOARD_SIZE;
		this.enabled = false;
		this.drawn = true;
		this.expanded = false;
		this.binding = false;
	}

	public Module(String name, Category category, boolean drawn)
	{
		this.name = name;
		this.category = category;
		this.description = null;
		this.bind = Keyboard.KEYBOARD_SIZE;
		this.enabled = false;
		this.drawn = drawn;
		this.expanded = false;
		this.binding = false;
	}

	public Module(String name, Category category, boolean drawn, String description)
	{
		this.name = name;
		this.category = category;
		this.description = description;
		this.bind = Keyboard.KEYBOARD_SIZE;
		this.enabled = false;
		this.drawn = drawn;
		this.expanded = false;
		this.binding = false;
	}

	public void enable()
	{
		MinecraftForge.EVENT_BUS.register(this);
		setEnabled(true);
		onEnable();
		MinecraftForge.EVENT_BUS.post(new ToggleEvent(this, true));
	}

	public void disable()
	{
		MinecraftForge.EVENT_BUS.unregister(this);
		setEnabled(false);
		onDisable();
		MinecraftForge.EVENT_BUS.post(new ToggleEvent(this, false));
	}

	public void toggle()
	{
		if (isEnabled()) disable();
		else enable();
	}

	public void onEnable()
	{
	}

	public void onDisable()
	{
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getBind()
	{
		return bind;
	}

	public void setBind(int bind)
	{
		this.bind = bind;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void addSetting(Setting setting)
	{
		Client.INSTANCE.settingManager.addSetting(setting);
	}

	public boolean isExpanded()
	{
		return expanded;
	}

	public void setExpanded(boolean expanded)
	{
		this.expanded = expanded;
	}

	public boolean isBinding()
	{
		return binding;
	}

	public void setBinding(boolean binding)
	{
		this.binding = binding;
	}

	public boolean isDrawn()
	{
		return drawn;
	}

	public void setDrawn(boolean drawn)
	{
		this.drawn = drawn;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
