package net.neverandy.moredyes.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.util.IWorldPosCallable;

public class ContainerWorkbench extends WorkbenchContainer
{
    public ContainerWorkbench(int id, PlayerInventory player, IWorldPosCallable pos)
    {
        super(id, player, pos);
    }
    public boolean canInteractWith(PlayerEntity player)
    {
        //return isWithinUsableDistance(IWorldPosCallable.of(player.world, player.getPosition()), player);
        return true;
    }
}
