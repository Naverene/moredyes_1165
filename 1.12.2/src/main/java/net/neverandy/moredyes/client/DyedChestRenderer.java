package net.neverandy.moredyes.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neverandy.moredyes.block.IColoredBlock;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.TileEntityDyedChest;

/**
 * The vanilla chest renderer with grey chest textures: the lid and the base are multiplied by the dye color, and
 * the latch keeps its own color.
 */
@SideOnly(Side.CLIENT)
public class DyedChestRenderer extends TileEntitySpecialRenderer<TileEntityDyedChest>
{
	private static final ResourceLocation TEXTURE_NORMAL=new ResourceLocation(Reference.MOD_ID,"textures/entity/chest/normal.png");
	private static final ResourceLocation TEXTURE_NORMAL_DOUBLE=new ResourceLocation(Reference.MOD_ID,"textures/entity/chest/normal_double.png");
	private final ModelChest simpleChest=new ModelChest();
	private final ModelChest largeChest=new ModelLargeChest();

	@Override
	public void render(TileEntityDyedChest te,double x,double y,double z,float partialTicks,int destroyStage,float alpha)
	{
		GlStateManager.enableDepth();
		GlStateManager.depthFunc(515);
		GlStateManager.depthMask(true);
		int facing=0;
		int color=te.itemColor;
		if(te.hasWorld())
		{
			Block block=te.getBlockType();
			facing=te.getBlockMetadata();
			if(block instanceof BlockChest&&facing==0)
			{
				((BlockChest)block).checkForSurroundingChests(te.getWorld(),te.getPos(),te.getWorld().getBlockState(te.getPos()));
				facing=te.getBlockMetadata();
			}
			if(block instanceof IColoredBlock)
			{
				color=((IColoredBlock)block).getColor(0);
			}
			te.checkForAdjacentChests();
		}
		// The second half of a double chest is drawn by the first half.
		if(te.adjacentChestZNeg!=null||te.adjacentChestXNeg!=null)
		{
			return;
		}
		ModelChest model;
		if(te.adjacentChestXPos==null&&te.adjacentChestZPos==null)
		{
			model=this.simpleChest;
			if(destroyStage>=0)
			{
				this.bindTexture(DESTROY_STAGES[destroyStage]);
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scale(4.0F,4.0F,1.0F);
				GlStateManager.translate(0.0625F,0.0625F,0.0625F);
				GlStateManager.matrixMode(5888);
			}
			else
			{
				this.bindTexture(TEXTURE_NORMAL);
			}
		}
		else
		{
			model=this.largeChest;
			if(destroyStage>=0)
			{
				this.bindTexture(DESTROY_STAGES[destroyStage]);
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scale(8.0F,4.0F,1.0F);
				GlStateManager.translate(0.0625F,0.0625F,0.0625F);
				GlStateManager.matrixMode(5888);
			}
			else
			{
				this.bindTexture(TEXTURE_NORMAL_DOUBLE);
			}
		}
		GlStateManager.pushMatrix();
		GlStateManager.enableRescaleNormal();
		GlStateManager.translate((float)x,(float)y+1.0F,(float)z+1.0F);
		GlStateManager.scale(1.0F,-1.0F,-1.0F);
		GlStateManager.translate(0.5F,0.5F,0.5F);
		int angle=0;
		if(facing==2)
		{
			angle=180;
		}
		if(facing==4)
		{
			angle=90;
		}
		if(facing==5)
		{
			angle=-90;
		}
		if(facing==2&&te.adjacentChestXPos!=null)
		{
			GlStateManager.translate(1.0F,0.0F,0.0F);
		}
		if(facing==5&&te.adjacentChestZPos!=null)
		{
			GlStateManager.translate(0.0F,0.0F,-1.0F);
		}
		GlStateManager.rotate((float)angle,0.0F,1.0F,0.0F);
		GlStateManager.translate(-0.5F,-0.5F,-0.5F);
		float lid=te.prevLidAngle+(te.lidAngle-te.prevLidAngle)*partialTicks;
		lid=Math.max(lid,lidAngle(te.adjacentChestZNeg,partialTicks));
		lid=Math.max(lid,lidAngle(te.adjacentChestXNeg,partialTicks));
		lid=Math.max(lid,lidAngle(te.adjacentChestXPos,partialTicks));
		lid=Math.max(lid,lidAngle(te.adjacentChestZPos,partialTicks));
		lid=1.0F-lid;
		lid=1.0F-lid*lid*lid;
		model.chestLid.rotateAngleX=-(lid*((float)Math.PI/2F));
		model.chestKnob.rotateAngleX=model.chestLid.rotateAngleX;
		if(destroyStage<0)
		{
			GlStateManager.color((color>>16&255)/255.0F,(color>>8&255)/255.0F,(color&255)/255.0F,alpha);
		}
		model.chestLid.render(0.0625F);
		model.chestBelow.render(0.0625F);
		if(destroyStage<0)
		{
			GlStateManager.color(1.0F,1.0F,1.0F,alpha);
		}
		model.chestKnob.render(0.0625F);
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		GlStateManager.color(1.0F,1.0F,1.0F,1.0F);
		if(destroyStage>=0)
		{
			GlStateManager.matrixMode(5890);
			GlStateManager.popMatrix();
			GlStateManager.matrixMode(5888);
		}
	}
	private static float lidAngle(TileEntityChest chest,float partialTicks)
	{
		return chest==null?0.0F:chest.prevLidAngle+(chest.lidAngle-chest.prevLidAngle)*partialTicks;
	}
}
