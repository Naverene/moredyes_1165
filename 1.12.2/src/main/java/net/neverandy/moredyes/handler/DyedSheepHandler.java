package net.neverandy.moredyes.handler;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItemDye;
import net.neverandy.moredyes.network.ModNetwork;
import net.neverandy.moredyes.network.SheepColorMessage;
import net.neverandy.moredyes.reference.ColorStrings;

/**
 * Lets vanilla sheep wear the mod's colors. The color is kept in the sheep's own saved data, on top of its vanilla
 * fleece color, and sent to the players who can see the sheep so the wool is drawn in it.
 * <ul>
 * <li>A dye of the mod colors a sheep; a vanilla dye gives it a vanilla color again.</li>
 * <li>Shearing or killing a colored sheep gives wool of its color, and the wool grows back in it.</li>
 * <li>A lamb takes the color of one of its parents.</li>
 * <li>Some sheep get a random color when they first appear in the world.</li>
 * </ul>
 */
public class DyedSheepHandler
{
	/** Saved on the sheep: the position of its color in ColorStrings.ALL. Absent for a vanilla color. */
	private static final String COLOR_TAG="MoreDyesColor";
	/** Saved on every sheep that has had its chance at a random color, so it only gets one. */
	private static final String CHECKED_TAG="MoreDyesChecked";

	/** The mod color of a sheep as a position in ColorStrings.ALL, or -1 when it has a vanilla color. */
	public static int getColor(EntitySheep sheep)
	{
		NBTTagCompound data=sheep.getEntityData();
		if(!data.hasKey(COLOR_TAG))
		{
			return -1;
		}
		int color=data.getInteger(COLOR_TAG);
		return color>=0&&color<ColorStrings.ALL.length?color:-1;
	}
	/** Colors a sheep (-1 for back to its vanilla color) and tells the players who can see it. */
	public static void setColor(EntitySheep sheep,int color)
	{
		storeColor(sheep,color);
		if(!sheep.world.isRemote)
		{
			ModNetwork.CHANNEL.sendToAllTracking(new SheepColorMessage(sheep.getEntityId(),color),sheep);
		}
	}
	/** Records the color on the sheep without telling anyone; the client uses this for what the server sent it. */
	public static void storeColor(EntitySheep sheep,int color)
	{
		if(color<0)
		{
			sheep.getEntityData().removeTag(COLOR_TAG);
		}
		else
		{
			sheep.getEntityData().setInteger(COLOR_TAG,color);
		}
	}
	private static ItemStack wool(int color,int count)
	{
		return new ItemStack(MDBlock.wool[ColorStrings.groupOf(color)],count,ColorStrings.shadeOf(color));
	}

	@SubscribeEvent
	public void onInteract(PlayerInteractEvent.EntityInteract event)
	{
		if(!(event.getTarget() instanceof EntitySheep))
		{
			return;
		}
		EntitySheep sheep=(EntitySheep)event.getTarget();
		EntityPlayer player=event.getEntityPlayer();
		ItemStack held=event.getItemStack();
		boolean server=!event.getWorld().isRemote;
		int color=getColor(sheep);
		if(held.getItem() instanceof MDItemDye)
		{
			int dye=((MDItemDye)held.getItem()).getColorIndex(held.getMetadata());
			if(!sheep.getSheared()&&dye!=color)
			{
				if(server)
				{
					setColor(sheep,dye);
					if(!player.capabilities.isCreativeMode)
					{
						held.shrink(1);
					}
				}
				event.setCanceled(true);
				event.setCancellationResult(EnumActionResult.SUCCESS);
			}
		}
		else if(held.getItem()==Items.DYE)
		{
			// The vanilla dye then colors the sheep as usual.
			if(server&&color>=0&&!sheep.getSheared())
			{
				setColor(sheep,-1);
			}
		}
		else if(held.getItem() instanceof ItemShears&&color>=0&&!sheep.getSheared()&&!sheep.isChild())
		{
			// Vanilla shearing would give wool of the vanilla color underneath, so it is done here instead.
			if(server)
			{
				sheep.setSheared(true);
				Random rand=sheep.getRNG();
				int count=1+rand.nextInt(3);
				for(int i=0;i<count;i++)
				{
					EntityItem item=sheep.entityDropItem(wool(color,1),1.0F);
					item.motionY+=(double)(rand.nextFloat()*0.05F);
					item.motionX+=(double)((rand.nextFloat()-rand.nextFloat())*0.1F);
					item.motionZ+=(double)((rand.nextFloat()-rand.nextFloat())*0.1F);
				}
				held.damageItem(1,player);
				sheep.playSound(SoundEvents.ENTITY_SHEEP_SHEAR,1.0F,1.0F);
			}
			event.setCanceled(true);
			event.setCancellationResult(EnumActionResult.SUCCESS);
		}
	}

	/** A colored sheep that dies with its wool on drops wool of its color. */
	@SubscribeEvent
	public void onDrops(LivingDropsEvent event)
	{
		if(!(event.getEntityLiving() instanceof EntitySheep))
		{
			return;
		}
		int color=getColor((EntitySheep)event.getEntityLiving());
		if(color<0)
		{
			return;
		}
		Item vanillaWool=Item.getItemFromBlock(Blocks.WOOL);
		for(EntityItem drop:event.getDrops())
		{
			if(drop.getItem().getItem()==vanillaWool)
			{
				drop.setItem(wool(color,drop.getItem().getCount()));
			}
		}
	}

	/** A lamb takes after one of its parents, mod color or not. */
	@SubscribeEvent
	public void onBaby(BabyEntitySpawnEvent event)
	{
		if(event.getChild() instanceof EntitySheep&&event.getParentA() instanceof EntitySheep&&event.getParentB() instanceof EntitySheep)
		{
			EntitySheep child=(EntitySheep)event.getChild();
			EntitySheep parent=(EntitySheep)(child.getRNG().nextBoolean()?event.getParentA():event.getParentB());
			int color=getColor(parent);
			if(color>=0)
			{
				storeColor(child,color);
			}
			child.getEntityData().setBoolean(CHECKED_TAG,true);
		}
	}

	/** Gives a sheep its one chance at a random color the first time it is in the world. */
	@SubscribeEvent
	public void onJoinWorld(EntityJoinWorldEvent event)
	{
		Entity entity=event.getEntity();
		if(event.getWorld().isRemote||!(entity instanceof EntitySheep))
		{
			return;
		}
		NBTTagCompound data=entity.getEntityData();
		if(data.getBoolean(CHECKED_TAG))
		{
			return;
		}
		data.setBoolean(CHECKED_TAG,true);
		EntitySheep sheep=(EntitySheep)entity;
		if(getColor(sheep)<0&&sheep.getRNG().nextFloat()<ConfigHandler.sheepSpawnChance)
		{
			storeColor(sheep,sheep.getRNG().nextInt(ColorStrings.ALL.length));
		}
	}

	/** Tells a player the color of a sheep that comes into view. */
	@SubscribeEvent
	public void onStartTracking(PlayerEvent.StartTracking event)
	{
		if(event.getTarget() instanceof EntitySheep&&event.getEntityPlayer() instanceof EntityPlayerMP)
		{
			EntitySheep sheep=(EntitySheep)event.getTarget();
			int color=getColor(sheep);
			if(color>=0)
			{
				ModNetwork.CHANNEL.sendTo(new SheepColorMessage(sheep.getEntityId(),color),(EntityPlayerMP)event.getEntityPlayer());
			}
		}
	}
}
