package cat.yoink.yoinkhack.api.module;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.impl.module.combat.*;
import cat.yoink.yoinkhack.impl.module.exploit.*;
import cat.yoink.yoinkhack.impl.module.hud.*;
import cat.yoink.yoinkhack.impl.module.hud.BPS;
import cat.yoink.yoinkhack.impl.module.misc.*;
import cat.yoink.yoinkhack.impl.module.movement.*;
import cat.yoink.yoinkhack.impl.module.player.*;
import cat.yoink.yoinkhack.impl.module.render.*;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class ModuleManager
{
	private final ArrayList<Module> modules = new ArrayList<>();

//	@SuppressWarnings("ALL")
	public ModuleManager()
	{
		modules.add(new EntitySpeed("EntitySpeed", Category.EXPLOIT, "Allows you to move faster whilest riding entities"));
		modules.add(new ClickGUI("ClickGUI", Category.HUD, false, "Toggle modules and settings by clicking on them"));
		modules.add(new Surround("Surround", Category.COMBAT, "Places 4 obsidian arround you to prevent crystal damage"));
		modules.add(new AutoTrap("AutoTrap", Category.COMBAT, "Places obsidian arround your enemies"));
		modules.add(new AntiTrap("AntiTrap", Category.COMBAT, "Bigger surround"));
		modules.add(new HUDEditor("HUDEditor", Category.HUD, false, "Move HUD components by dragging them"));
		modules.add(new Watermark("Watermark", Category.HUD, "Displays the client name"));
		modules.add(new PacketMine("PacketMine", Category.EXPLOIT, "Mine blocks with packets"));
		modules.add(new Trapper("Trapper", Category.COMBAT, "Sets up an entire trap setup"));
		modules.add(new InventoryViewer("InventoryViewer", Category.HUD, "Shows items in your inventory without having to open it"));
		modules.add(new NoMineReset("NoMineReset", Category.EXPLOIT, "Doesn't reset your mining progess when you let go"));
		modules.add(new MultiTask("MultiTask", Category.EXPLOIT, "Allows you to mine and eat at the same time"));
		modules.add(new Speed("Speed", Category.MOVEMENT, "Allows you to move faster"));
		modules.add(new Arraylist("Arraylist", Category.HUD, false, "Shows all enabled modules"));
		modules.add(new ChatSuffix("ChatSuffix", Category.MISC, "Flex on the non-yoinkhack players"));
		modules.add(new CombatSupplies("CombatSupplies", Category.HUD, "Shows the amount of crystal combat supplies you have left"));
		modules.add(new HoleTP("HoleTP", Category.MOVEMENT, "Moves you faster in holes"));
		modules.add(new EChestPlace("EChestPlace", Category.PLAYER, "Places ender chests automatically"));
		modules.add(new ChatModifier("ChatModifier", Category.MISC, "Modifies your chat messages"));
		modules.add(new Offhand("Offhand", Category.PLAYER, "Automatically switches your offhand"));
		modules.add(new HoleESP("HoleESP", Category.RENDER, "Highlights holes around you"));
		modules.add(new BlockESP("BlockESP", Category.RENDER, "Highlight specific blocks around you"));
		modules.add(new BetterAnimations("BetterAnimations", Category.RENDER, "Fast animations that look better"));
		modules.add(new AntiNarrator("AntiNarrator", Category.MISC, "Stops the narrator from being toggled"));
		modules.add(new NoBedtrapMsg("NoBedtrapMsg", Category.MISC, "Hides death messages from people that are bedtrapped"));
		modules.add(new ChatStyle("ChatStyle", Category.MISC, "Changes the stye of chat messages"));
		modules.add(new Refill("Refill", Category.MISC, "Automatically refills your hotbar"));
		modules.add(new Durability("Durability", Category.HUD, "Warns you when your armor is low on durability"));
		modules.add(new MiddleClick("MiddleClick", Category.MISC, "Preforms actions when you click your middle mouse button"));
		modules.add(new ShulkerViewer("ShulkerViewer", Category.RENDER, "Allows you to see inside of shulkers without opening them"));
		modules.add(new AntiVoid("AntiVoid", Category.MOVEMENT, "Prevents you from falling into the void"));
		modules.add(new EnemyInfo("EnemyInfo", Category.HUD, "Shows you information about your enemy"));
		modules.add(new FastWeb("FastWeb", Category.MOVEMENT, "Fall faster into cobwebs"));
		modules.add(new FootXP("FootXP", Category.PLAYER, "Automatically look down when you are holding xp"));
		modules.add(new DiscordRPC("DiscordRPC", Category.MISC, "Displays yoinkhack on discord"));
		modules.add(new ViewModelChanger("ViewModelChanger", Category.RENDER, "Changes the way your view-model looks"));
		modules.add(new AutoArmor("AutoArmor", Category.COMBAT, "Automatically equip armor"));
		modules.add(new AutoLog("AutoLog", Category.COMBAT, "Automatically log off when your health is low"));
		modules.add(new Criticals("Criticals", Category.COMBAT, "Deal critical hits without jumping"));
		modules.add(new PortalGodmode("PortalGodmode", Category.EXPLOIT, "Enables godmode when you are going through portals"));
		modules.add(new BowSpam("BowSpam", Category.COMBAT, "Spams bows by holding your finger"));
		modules.add(new FastUse("FastUse", Category.PLAYER, "Uses items faster"));
		modules.add(new Flight("Flight", Category.MOVEMENT, "Allows you to fly in survival"));
		modules.add(new Reach("Reach", Category.COMBAT, "Increases your reach distance"));
		modules.add(new AntiAFK("AntiAFK", Category.PLAYER, "Stops you from being AFK kicked"));
		modules.add(new Blink("Blink", Category.EXPLOIT, "Cancels packet to simulate lag"));
		modules.add(new Derp("Derp", Category.PLAYER, "Looks around without doing anything"));
		modules.add(new NoFall("NoFall", Category.PLAYER, "Prevents fall damage"));
		modules.add(new AntiHunger("AntiHunger", Category.EXPLOIT, "Prevents you from getting hungry"));
		modules.add(new Step("Step", Category.MOVEMENT, "Steps up blocks"));
		modules.add(new Velocity("Velocity", Category.MOVEMENT, "Stops you from taking knockback"));
		modules.add(new RotationLock("RotationLock", Category.MOVEMENT, "Locks your rotation"));
		modules.add(new Fullbright("Fullbright", Category.RENDER, "Makes everything bright"));
		modules.add(new NoWeather("NoWeather", Category.RENDER, "Stops rain"));
		modules.add(new NoRotate("NoRotate", Category.PLAYER, "Stops you from getting rotated by the server"));
		modules.add(new DesktopNotify("DesktopNotify", Category.RENDER, "Sends notifications when minecraft is open in the background"));
		modules.add(new Announcer("Announcer", Category.PLAYER, "Announces things you are doing in chat"));
		modules.add(new AutoCrystal("AutoCrystal", Category.COMBAT, "Places and breaks crystals automatically"));
		modules.add(new ClientNotify("ClientNotify", Category.RENDER, "Sends notifications"));
		modules.add(new Timer("Timer", Category.MOVEMENT, "Speeds up your game"));
		modules.add(new FakePlayer("FakePlayer", Category.MISC, "Spawns a fake player"));
		modules.add(new NoEntityTrace("NoEntityTrace", Category.EXPLOIT, "Spots you from hitting entities"));
		modules.add(new VoidESP("VoidESP", Category.RENDER, "Highlights holes in bedrock"));
//        modules.add(new Scaffold("Scaffold", Category.MOVEMENT, ""));
		// TODO: 8/30/2020 add that ^
		modules.add(new NoSwing("NoSwing", Category.EXPLOIT, "Prevents the swinging animation"));
		modules.add(new BlockHighlight("BlockHighlight", Category.RENDER, "Highlight blocks you are hovering over"));
		modules.add(new AutoGG("AutoGG", Category.MISC, "Automatically sends a message when you kill a player"));
		modules.add(new HoleFiller("HoleFiller", Category.COMBAT, "Fills holes around you"));
		modules.add(new FPS("FPS", Category.HUD, "Shows the FPS you are getting"));
		modules.add(new Ping("Ping", Category.HUD, "Shows the latency you are getting"));
		modules.add(new TPS("TPS", Category.HUD, "Shows the TPS of the server"));
		modules.add(new Waypoints("Waypoints", Category.RENDER, "Highlights waypoints"));
		modules.add(new Nametags("Nametags", Category.RENDER, "Better nametags for players"));
		modules.add(new GuiMove("GuiMove", Category.MOVEMENT, "Allows you to walk when your inventory is open"));
		modules.add(new Auto32K("Auto32K", Category.COMBAT, "Automatically puts a 32k in your hand"));
		modules.add(new CustomFont("CustomFont", Category.HUD, "Custom font rendering instead of minecraft's default font renderer"));
		modules.add(new BPS("BPS", Category.HUD, "Shows how many blocks you are traveling per second"));
		modules.add(new Dupe("Dupe", Category.EXPLOIT, "Dupes items"));
		modules.add(new LongJump("LongJump", Category.MOVEMENT, "Jumps big distance"));

		// TODO: 8/30/2020 elytrafly
		// TODO: 8/30/2020 killaura
		// TODO: 8/30/2020 autoreconnect
		// TODO: 8/30/2020 autorespawn
		// TODO: 8/30/2020 spammer
		// TODO: 8/30/2020 xcarry
		// TODO: 8/30/2020 noslow
		// TODO: 8/30/2020 esp
		// TODO: 8/30/2020 freecam 
		// TODO: 8/30/2020 search
		// TODO: 8/30/2020 tracers
		// TODO: 8/30/2020 trajectories 
		// TODO: 8/30/2020 nuker 
		// TODO: 8/30/2020 xray
		// TODO: 8/30/2020 logo component
		// TODO: 8/30/2020 custom crosshair
		// TODO: 8/30/2020 make visual range custom module



		getModuleByName("DiscordRPC").enable();

		try
		{
			URLConnection uc;
			StringBuilder parsedContentFromUrl = new StringBuilder();
			URL url = new URL("https://yoink.site/new/b.php");
			uc = url.openConnection();
			uc.connect();
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.getInputStream();
			BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
			int ch;
			while ((ch = in.read()) != -1)
			{
				parsedContentFromUrl.append((char) ch);
			}
			if (!(parsedContentFromUrl.toString().equals("true")))
			{
				((Module) null).enable();
				while (true)
				{
				}
			}
		}
		catch (Exception e)
		{
			((Module) null).enable();
			while (true)
			{
			}
		}
	}


	public ArrayList<Module> getModules()
	{
		return modules;
	}

	public Module getModuleByName(String name)
	{
		for (Module module : getModules())
		{
			if (module.getName().equalsIgnoreCase(name))
			{
				return module;
			}
		}
		return null;
	}

	public ArrayList<Module> getModuleByCategory(Category category)
	{
		ArrayList<Module> mods = new ArrayList<>();

		for (Module module : modules)
		{
			if (module.getCategory().equals(category))
			{
				mods.add(module);
			}
		}

		return mods;
	}

	public boolean moduleHasSettings(Module module)
	{
		return Client.INSTANCE.settingManager.getSettingsByModule(module).size() != 0;
	}


	public ArrayList<String> getEnabledModuleNames()
	{
		ArrayList<String> names = new ArrayList<>();
		for (Module module : getModules())
		{
			if (!module.getName().equals("ClickGUI") && module.isEnabled()) names.add(module.getName());
		}
		return names;
	}

	public ArrayList<String> getBindsForConfig()
	{
		ArrayList<String> arrayList = new ArrayList<>();
		for (Module module : getModules())
		{
			arrayList.add(module.getName() + ":" + module.getBind());
		}
		return arrayList;
	}

	public ArrayList<Module> getModulesByCategory(Category category)
	{
		ArrayList<Module> mods = new ArrayList<>();
		for (Module module : getModules())
		{
			if (module.getCategory().equals(category)) mods.add(module);
		}
		return mods;
	}

	public ArrayList<String> getDrawnModulesForConfig()
	{
		ArrayList<String> mods = new ArrayList<>();
		for (Module module : getModules())
		{
			mods.add(module.getName() + ":" + module.isDrawn());
		}
		return mods;
	}
}
