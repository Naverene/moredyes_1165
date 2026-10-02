package net.neverandy.moredyes.block;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;

public class ContainerWorkbench extends CraftingMenu
{
    public ContainerWorkbench(int id, Inventory player, ContainerLevelAccess pos)
    {
        super(id, player, pos);
    }
    public boolean stillValid(Player player)
    {
        //return isWithinUsableDistance(ContainerLevelAccess.of(player.world, player.getPosition()), player);
        return true;
    }
}
