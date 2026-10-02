package net.neverandy.moredyes.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neverandy.moredyes.block.IColoredBlock;
import net.neverandy.moredyes.tileentity.TileEntityDyedChest;
import net.neverandy.moredyes.utility.ColorUtil;

/** Draws a dyed chest item with the chest renderer, like the vanilla chest item. */
@SideOnly(Side.CLIENT)
public class DyedChestItemRenderer extends TileEntityItemStackRenderer
{
	private final TileEntityDyedChest chest=new TileEntityDyedChest();

	@Override
	public void renderByItem(ItemStack stack,float partialTicks)
	{
		Block block=Block.getBlockFromItem(stack.getItem());
		this.chest.itemColor=block instanceof IColoredBlock?((IColoredBlock)block).getColor(stack.getMetadata()):ColorUtil.WHITE;
		TileEntityRendererDispatcher.instance.render(this.chest,0.0D,0.0D,0.0D,0.0F,partialTicks);
	}
}
