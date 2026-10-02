package net.neverandy.moredyes.block;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import net.neverandy.moredyes.utility.BlockInfo;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class WorkbenchBlock extends CraftingTableBlock
{
    private static final Component CONTAINER_NAME = new TranslatableComponent("container.crafting");

    public WorkbenchBlock(BlockInfo info)
    {
        super(Properties.of(info.blockMaterial)
                .strength(info.hardness, info.resistance)
                .sound(info.sound)
                .lightLevel(value -> info.lightlevel));
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if (worldIn.isClientSide)
        {
            return InteractionResult.FAIL;
        } else
        {
            player.openMenu(state.getMenuProvider(worldIn, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.CONSUME;
        }
    }
    
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos)
    {
        return new SimpleMenuProvider((id, inventory, player) ->
                new ContainerWorkbench(id, inventory, ContainerLevelAccess.create(worldIn, pos)), CONTAINER_NAME);
    }
}
