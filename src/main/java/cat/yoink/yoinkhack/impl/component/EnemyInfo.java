package cat.yoink.yoinkhack.impl.component;

import cat.yoink.yoinkhack.Client;
import cat.yoink.yoinkhack.api.component.Component;
import cat.yoink.yoinkhack.api.friend.Friend;
import cat.yoink.yoinkhack.api.util.font.FontUtil;
import cat.yoink.yoinkhack.impl.event.PopEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author yoink
 * @since 8/28/2020
 */
public class EnemyInfo extends Component
{
	private final HashMap<String, Integer> popList = new HashMap<>();
	private EntityPlayer enemy;

	public EnemyInfo(String name)
	{
		super(name);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		findNewEnemy();
	}

	@Override
	public void render()
	{

		try
		{
			if (enemy != null)
			{
				if (mc.world.getLoadedEntityList().contains(enemy))
				{
					if (Client.INSTANCE.friendManager.isFriend(enemy.getName()) && !Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getBoolValue())
					{
						return;
					}
					drawPlayer(enemy);
					int textXOffset = 40;

					String name = nameString(enemy);
					String type = typeString(enemy);
					String ping = pingString(enemy);
					String dura = durabilityString(enemy);
					String heal = healthString(enemy);
					String pops = popString(enemy);

					ArrayList<String> str = new ArrayList<>(Arrays.asList(name, type, ping, dura, heal, pops));
					int maxWidth = 0;
					for (String s : str)
					{
						if (FontUtil.getStringWidth(s) > maxWidth) maxWidth = FontUtil.getStringWidth(s);
					}

					boolean background = Client.INSTANCE.settingManager.getSettingEasy(getName(), 1).getBoolValue();
					boolean border = Client.INSTANCE.settingManager.getSettingEasy(getName(), 2).getBoolValue();

					setWidth(textXOffset + maxWidth);
					setHeight(65);

					Color borderColor = new Color(35, 35, 35, 143);
					Color backgroundColor = new Color(35, 35, 35, 50);

					if (border)
					{
						Gui.drawRect(getX() - 1, getY() - 1, getX(), getY() + getHeight() + 1, borderColor.getRGB());
						Gui.drawRect(getX() - 1, getY() - 1, getX() + getWidth() + 1, getY(), borderColor.getRGB());
						Gui.drawRect(getX() + getWidth(), getY() - 1, getX() + getWidth() + 1, getY() + getHeight() + 1, borderColor.getRGB());
						Gui.drawRect(getX() - 1, getY() + getHeight(), getX() + getWidth() + 1, getY() + getHeight() + 1, borderColor.getRGB());
					}

					if (background)
					{
						Gui.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), backgroundColor.getRGB());
					}

					FontUtil.drawStringWithShadow(name, getX() + textXOffset, getY() + 5, 0xffffffff);
					FontUtil.drawStringWithShadow(type, getX() + textXOffset, getY() + 15, 0xffffffff);
					FontUtil.drawStringWithShadow(ping, getX() + textXOffset, getY() + 25, 0xffffffff);
					FontUtil.drawStringWithShadow(dura, getX() + textXOffset, getY() + 35, 0xffffffff);
					FontUtil.drawStringWithShadow(heal, getX() + textXOffset, getY() + 45, 0xffffffff);
					FontUtil.drawStringWithShadow(pops, getX() + textXOffset, getY() + 55, 0xffffffff);

				}
				else
				{
					enemy = null;
				}

				assert enemy != null;
				if (enemy.getHealth() <= 0)
				{
					popList.remove(enemy.getName());
				}
			}
		}
		catch (Exception e)
		{
//            ChatUtil.sendChatMessage("Handled crash exception: " + e.getMessage());
		}

	}

	private String typeString(EntityPlayer enemy)
	{
		StringBuilder text = new StringBuilder("\u00A77Type ");

		boolean isFriend = false;
		boolean isNaked = false;
		boolean isWasp = false;

		for (Friend friend : Client.INSTANCE.friendManager.getFriends())
		{
			if (friend.getName().equalsIgnoreCase(enemy.getName()))
			{
				isFriend = true;
			}
		}

		if (isEmpty(0, enemy) && isEmpty(1, enemy) && isEmpty(2, enemy) && isEmpty(3, enemy))
		{
			isNaked = true;
		}

		if (enemy.inventory.armorInventory.get(2).getItem().equals(Items.ELYTRA))
		{
			isWasp = true;
		}


		if (isFriend)
		{
			text.append(ChatFormatting.BLUE);
			text.append("Friend");
		}
		else if (isNaked)
		{
			text.append(ChatFormatting.WHITE);
			text.append("Naked");
		}
		else if (isWasp)
		{
			text.append(ChatFormatting.GOLD);
			text.append("Wasp");
		}
		else
		{
			text.append(ChatFormatting.RED);
			text.append("Enemy");
		}

		return text.toString();
	}

	private String popString(EntityPlayer enemy)
	{
		StringBuilder text = new StringBuilder("\u00A77Pops \u00A7r");
		if (popList.get(enemy.getName()) != null)
		{
			text.append(popList.get(enemy.getName()));
		}
		else
		{
			text.append("0");
		}
		return text.toString();
	}

	private String nameString(EntityPlayer player)
	{
		return "\u00A77Name \u00A7r" + player.getName();
	}

	private String healthString(EntityPlayer player)
	{
		return "\u00A77Health \u00A7r" + Math.round(player.getHealth() + player.getAbsorptionAmount());
	}

	private String pingString(EntityPlayer player)
	{
		final NetworkPlayerInfo playerInfo = Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID());
		return "\u00A77Ping \u00A7r" + playerInfo.getResponseTime();
	}

	private String durabilityString(EntityPlayer player)
	{
		StringBuilder text = new StringBuilder("\u00A77Durability ");

		if (isEmpty(0, player) && isEmpty(1, player) && isEmpty(2, player) && isEmpty(3, player))
		{
			text.append("\u00A7rNone");
		}
		else if (shouldMendRed(0, player) || shouldMendRed(1, player) || shouldMendRed(2, player) || shouldMendRed(3, player))
		{
			text.append("\u00A7cVery low");
		}
		else if (shouldMendYellow(0, player) || shouldMendYellow(1, player) || shouldMendYellow(2, player) || shouldMendYellow(3, player))
		{
			text.append("\u00A76Low");
		}
		else
		{
			text.append("\u00A7aNormal");
		}

		return text.toString();
	}

	private boolean shouldMendYellow(final int i, EntityPlayer player)
	{
		return player.inventory.armorInventory.get(i).getMaxDamage()
				!= 0 && 100 * player.inventory.armorInventory.get(i).getItemDamage()
				/ player.inventory.armorInventory.get(i).getMaxDamage()
				> 61;
	}

	private boolean shouldMendRed(final int i, EntityPlayer player)
	{
		return player.inventory.armorInventory.get(i).getMaxDamage()
				!= 0 && 100 * player.inventory.armorInventory.get(i).getItemDamage()
				/ player.inventory.armorInventory.get(i).getMaxDamage()
				> 81;
	}

	public boolean isEmpty(int slotNumber, EntityPlayer player)
	{
		return player.inventory.armorInventory.get(slotNumber).isEmpty();
	}

	public void drawPlayer(EntityLivingBase entityLivingBase)
	{
		float mouseYY = Objects.requireNonNull(mc.getRenderViewEntity()).rotationPitch * -1;
		GlStateManager.color(1, 1, 1, 1);
		drawEntityOnScreen(getX() + 16, getY() + 55, /*34*/  mouseYY, entityLivingBase);
	}

	private void drawEntityOnScreen(int posX, int posY, float mouseY, EntityLivingBase ent)
	{
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-26), (float) 26, (float) 26);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-((float) Math.atan(mouseY / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = mc.getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	private void findNewEnemy()
	{

		if (mc.world.loadedEntityList.size() == 0)
		{
			enemy = null;
			return;
		}

		float range = 1000;

		for (Entity entity : mc.world.loadedEntityList)
		{
			float distance = mc.player.getDistance(entity);
			if (entity instanceof EntityPlayer && distance < range && !entity.getName().equals(mc.player.getName()))
			{
				if (Client.INSTANCE.friendManager.isFriend(entity.getName()))
				{
					if (Client.INSTANCE.settingManager.getSettingEasy(getName(), 0).getBoolValue())
					{
						range = distance;
						enemy = (EntityPlayer) entity;
					}
				}
				else
				{
					range = distance;
					enemy = (EntityPlayer) entity;
				}
			}
		}
	}

	@SubscribeEvent
	public void onPop(PopEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (popList.get(event.getEntity().getName()) == null)
		{
			popList.put(event.getEntity().getName(), 1);
		}
		else if (popList.get(event.getEntity().getName()) != null)
		{
			int popCounter = popList.get(event.getEntity().getName());
			int newPopCounter = popCounter + 1;
			popList.put(event.getEntity().getName(), newPopCounter);
		}
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (event.getEntity() instanceof EntityPlayer && popList.get(event.getEntity().getName()) != null)
		{
			popList.remove(event.getEntity().getName());
		}
	}
}
