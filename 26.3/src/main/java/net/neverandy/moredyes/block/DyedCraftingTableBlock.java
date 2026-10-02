package net.neverandy.moredyes.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A dyed crafting table. It works like the vanilla one, but the vanilla crafting menu closes itself unless the block
 * is {@code minecraft:crafting_table}, so this opens a menu that stays open for this block.
 */
public class DyedCraftingTableBlock extends CraftingTableBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.crafting");

    public DyedCraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider(
            (containerId, inventory, player) -> new Menu(containerId, inventory, ContainerLevelAccess.create(level, pos), this),
            CONTAINER_TITLE);
    }

    /** The vanilla crafting menu with the vanilla menu type, so the client opens the ordinary crafting screen. */
    private static class Menu extends CraftingMenu {

        private final ContainerLevelAccess access;
        private final DyedCraftingTableBlock block;

        Menu(int containerId, Inventory inventory, ContainerLevelAccess access, DyedCraftingTableBlock block) {
            super(containerId, inventory, access);
            this.access = access;
            this.block = block;
        }

        @Override
        public boolean stillValid(Player player) {
            return stillValid(access, player, block);
        }
    }
}
