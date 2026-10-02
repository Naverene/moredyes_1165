package net.neverandy.moredyes.client;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neverandy.moredyes.block.BlockColored;
import net.neverandy.moredyes.block.IColoredBlock;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.handler.DyedSheepHandler;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.item.MDItemDye;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.TileEntityDyedChest;
import net.neverandy.moredyes.utility.LogHelper;

/**
 * How the dyed blocks and dyes are drawn. Every color of a kind of block shares one model with a grey texture, and
 * the game multiplies the faces marked for tinting by the dye color.
 */
@SideOnly(Side.CLIENT)
@EventBusSubscriber(modid=Reference.MOD_ID,value=Side.CLIENT)
public class ClientHandler
{
	/** The faces and item layers that take the dye color are tint index 0. */
	private static final int TINTED=0;

	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Block block:MDBlock.ALL)
		{
			IColoredBlock colored=(IColoredBlock)block;
			final String model=Reference.MOD_ID+":"+colored.getTypeName();
			// Every color uses the blockstate file of its kind of block, so the color is left out of the lookup,
			// along with the properties that do not change how the block looks.
			ModelLoader.setCustomStateMapper(block,new StateMapperBase()
			{
				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state)
				{
					Map<IProperty<?>,Comparable<?>> properties=new LinkedHashMap<IProperty<?>,Comparable<?>>(state.getProperties());
					properties.remove(BlockColored.SHADE);
					properties.remove(BlockLeaves.CHECK_DECAY);
					properties.remove(BlockLeaves.DECAYABLE);
					properties.remove(BlockChest.FACING);
					return new ModelResourceLocation(model,this.getPropertyString(properties));
				}
			});
			Item item=Item.getItemFromBlock(block);
			for(int meta=0;meta<colored.getShadeCount();meta++)
			{
				ModelLoader.setCustomModelResourceLocation(item,meta,new ModelResourceLocation(model,"inventory"));
			}
		}
		for(Item dye:MDItem.dye)
		{
			for(int meta=0;meta<((MDItemDye)dye).getShadeCount();meta++)
			{
				ModelLoader.setCustomModelResourceLocation(dye,meta,new ModelResourceLocation(Reference.MOD_ID+":dye","inventory"));
			}
		}
	}

	public static void registerColors()
	{
		Minecraft mc=Minecraft.getMinecraft();
		Block[] blocks=MDBlock.ALL.toArray(new Block[0]);
		mc.getBlockColors().registerBlockColorHandler((state,world,pos,tintIndex)->
		{
			Block block=state.getBlock();
			return tintIndex==TINTED?((IColoredBlock)block).getColor(block.getMetaFromState(state)):-1;
		},blocks);
		mc.getItemColors().registerItemColorHandler((stack,tintIndex)->
		{
			Block block=Block.getBlockFromItem(stack.getItem());
			return tintIndex==TINTED?((IColoredBlock)block).getColor(stack.getMetadata()):-1;
		},blocks);
		mc.getItemColors().registerItemColorHandler((stack,tintIndex)->
		{
			return tintIndex==TINTED?((MDItemDye)stack.getItem()).getColor(stack.getMetadata()):-1;
		},MDItem.dye);
	}

	/** Swaps the wool layer of the vanilla sheep renderer for one that knows the mod's colors. */
	public static void registerSheepLayer()
	{
		Render<?> render=Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntitySheep.class);
		if(!(render instanceof RenderSheep))
		{
			LogHelper.warn("Another mod replaced the sheep renderer; sheep dyed with More Dyes keep their vanilla look");
			return;
		}
		List<LayerRenderer<EntitySheep>> layers=ObfuscationReflectionHelper.getPrivateValue(RenderLivingBase.class,(RenderLivingBase<?>)render,"field_177097_h");
		for(int i=0;i<layers.size();i++)
		{
			if(layers.get(i) instanceof LayerSheepWool)
			{
				layers.set(i,new LayerDyedSheepWool((RenderSheep)render));
			}
		}
	}
	/** Records the color the server sent for a sheep; the wool layer reads it from there. */
	public static void setSheepColor(final int entityId,final int color)
	{
		final Minecraft mc=Minecraft.getMinecraft();
		mc.addScheduledTask(()->
		{
			if(mc.world!=null)
			{
				Entity entity=mc.world.getEntityByID(entityId);
				if(entity instanceof EntitySheep)
				{
					DyedSheepHandler.storeColor((EntitySheep)entity,color);
				}
			}
		});
	}

	/** Chests are drawn by a renderer instead of a model, both in the world and as items. */
	public static void registerChestRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDyedChest.class,new DyedChestRenderer());
		DyedChestItemRenderer itemRenderer=new DyedChestItemRenderer();
		for(Block chest:MDBlock.chest)
		{
			Item.getItemFromBlock(chest).setTileEntityItemStackRenderer(itemRenderer);
		}
	}
}
