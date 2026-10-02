package info.kg6jay.moredyes.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import info.kg6jay.moredyes.block.TileColor;

/**
 * Item for the blocks that keep their color in a tile entity (see TileColor). The item damage is the color's number;
 * the block is placed with metadata 0, which the block then sets to its direction or shape, and the color goes into
 * the tile entity.
 */
public class MDItemBlockTileColored extends ItemBlock {

    public MDItemBlockTileColored(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "_" + stack.getItemDamage();
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (!super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)) {
            return false;
        }
        if (world.getBlock(x, y, z) == this.field_150939_a) {
            TileColor.set(world, x, y, z, stack.getItemDamage());
        }
        return true;
    }
}
