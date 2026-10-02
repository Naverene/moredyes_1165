package net.neverandy.moredyes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A dyed crafting table. The vanilla crafting menu closes unless the block it was opened from is a vanilla crafting
 * table, so this one opens a menu that stays open (ContainerWorkbench).
 */
public class WorkbenchBlock extends CraftingTableBlock
{
    private static final Component CONTAINER_NAME = Component.translatable("container.crafting");

    public WorkbenchBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos)
    {
        return new SimpleMenuProvider((id, inventory, player) ->
                new ContainerWorkbench(id, inventory, ContainerLevelAccess.create(level, pos)), CONTAINER_NAME);
    }
}
