package net.neverandy.moredyes.handler;

import net.minecraft.block.Block;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.neverandy.moredyes.block.BlockColoredSapling;
import net.neverandy.moredyes.block.MDBlock;

/** Dyed blocks of coal and dyed saplings burn as long as the vanilla ones; the wooden blocks already do. */
public class FuelHandler
{
	@SubscribeEvent
	public void onFuelBurnTime(FurnaceFuelBurnTimeEvent event)
	{
		Block block=Block.getBlockFromItem(event.getItemStack().getItem());
		if(block instanceof BlockColoredSapling)
		{
			event.setBurnTime(100);
			return;
		}
		for(Block coal:MDBlock.coal)
		{
			if(block==coal)
			{
				event.setBurnTime(16000);
				return;
			}
		}
	}
}
