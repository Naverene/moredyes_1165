package net.neverandy.moredyes.block;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class ContainerWorkbench extends CraftingMenu
{
    private final ContainerLevelAccess access;

    public ContainerWorkbench(int id, Inventory inventory, ContainerLevelAccess access)
    {
        super(id, inventory, access);
        this.access = access;
    }

    /** Open while the player is near a dyed crafting table, as vanilla does for its own table. */
    @Override
    public boolean stillValid(Player player)
    {
        return access.evaluate((level, pos) -> level.getBlockState(pos).getBlock() instanceof WorkbenchBlock
                && player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D, true);
    }
}
