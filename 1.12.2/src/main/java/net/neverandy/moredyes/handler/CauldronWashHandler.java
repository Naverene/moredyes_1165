package net.neverandy.moredyes.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.neverandy.moredyes.block.MDBlock;

/** Right-clicking a cauldron holding water with dyed blocks washes the whole stack for one level of water. */
public class CauldronWashHandler
{
	@SubscribeEvent
	public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
	{
		World world=event.getWorld();
		IBlockState state=world.getBlockState(event.getPos());
		if(state.getBlock()!=Blocks.CAULDRON)
		{
			return;
		}
		int water=state.getValue(BlockCauldron.LEVEL);
		ItemStack held=event.getItemStack();
		if(water<=0||held.isEmpty())
		{
			return;
		}
		ItemStack washed=MDBlock.WASHED.get(Block.getBlockFromItem(held.getItem()));
		if(washed==null)
		{
			return;
		}
		// Cancelled on both sides, so the client does not place the held block; it still tells the server.
		event.setCanceled(true);
		event.setCancellationResult(EnumActionResult.SUCCESS);
		if(world.isRemote)
		{
			return;
		}
		EntityPlayer player=event.getEntityPlayer();
		ItemStack result=washed.copy();
		result.setCount(held.getCount());
		player.setHeldItem(event.getHand(),result);
		Blocks.CAULDRON.setWaterLevel(world,event.getPos(),state,water-1);
		player.inventoryContainer.detectAndSendChanges();
	}
}
