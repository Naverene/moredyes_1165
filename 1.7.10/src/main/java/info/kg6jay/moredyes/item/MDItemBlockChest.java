package info.kg6jay.moredyes.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import info.kg6jay.moredyes.block.MDBlockColoredChest;

public class MDItemBlockChest extends MDItemBlockColored {

    public MDItemBlockChest(Block block) {
        super(block);
    }

    /**
     * Stops a third chest of the same shade from being placed next to a double chest, like vanilla does. This is
     * checked here rather than in the block because the shade (metadata) is only known at this point.
     */
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (Block.getBlockFromItem(this) instanceof MDBlockColoredChest chest
            && !chest.canPlaceChestAt(world, x, y, z, metadata)) {
            return false;
        }
        return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }
}
