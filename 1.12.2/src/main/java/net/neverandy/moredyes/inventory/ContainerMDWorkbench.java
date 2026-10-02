package net.neverandy.moredyes.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.neverandy.moredyes.block.BlockColoredWorkbench;

/** The vanilla crafting grid, staying open next to a dyed crafting table instead of the vanilla one. */
public class ContainerMDWorkbench extends ContainerWorkbench
{
	private final World world;
	private final BlockPos pos;

	public ContainerMDWorkbench(InventoryPlayer inventory,World world,BlockPos pos)
	{
		super(inventory,world,pos);
		this.world=world;
		this.pos=pos;
	}
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return this.world.getBlockState(this.pos).getBlock() instanceof BlockColoredWorkbench
				&&player.getDistanceSq(this.pos.getX()+0.5D,this.pos.getY()+0.5D,this.pos.getZ()+0.5D)<=64.0D;
	}
}
