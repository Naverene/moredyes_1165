package info.kg6jay.moredyes.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import info.kg6jay.moredyes.block.MDBlockDyedSlab;
import info.kg6jay.moredyes.block.TileColor;

/**
 * Item for the dyed slabs. Like the vanilla slab item, placing a slab against a slab of the same color turns it into a
 * double slab: either the slab that was clicked (clicking the top of a bottom slab or the underside of a top slab), or
 * a slab in the spot the new one would go.
 */
public class MDItemBlockDyedSlab extends MDItemBlockTileColored {

    public MDItemBlockDyedSlab(Block block) {
        super(block);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (stack.stackSize == 0 || !player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        }
        if (this.canJoinClicked(world, x, y, z, side, stack)) {
            this.join(stack, world, x, y, z);
            return true;
        }
        int nx = x + Facing.offsetsXForSide[side];
        int ny = y + Facing.offsetsYForSide[side];
        int nz = z + Facing.offsetsZForSide[side];
        if (this.isJoinable(world, nx, ny, nz, stack)) {
            this.join(stack, world, nx, ny, nz);
            return true;
        }
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    /** Checked on the client before the click is sent, so joining must be allowed here as well. */
    @Override
    public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
        if (this.canJoinClicked(world, x, y, z, side, stack) || this.isJoinable(
            world,
            x + Facing.offsetsXForSide[side],
            y + Facing.offsetsYForSide[side],
            z + Facing.offsetsZForSide[side],
            stack)) {
            return true;
        }
        return super.func_150936_a(world, x, y, z, side, player, stack);
    }

    private boolean canJoinClicked(World world, int x, int y, int z, int side, ItemStack stack) {
        if (!this.isJoinable(world, x, y, z, stack)) {
            return false;
        }
        int meta = world.getBlockMetadata(x, y, z);
        return side == 1 && meta == MDBlockDyedSlab.BOTTOM || side == 0 && meta == MDBlockDyedSlab.TOP;
    }

    /** True if there is a single slab of this block in the stack's color at this position. */
    private boolean isJoinable(World world, int x, int y, int z, ItemStack stack) {
        return world.getBlock(x, y, z) == this.field_150939_a
            && world.getBlockMetadata(x, y, z) != MDBlockDyedSlab.DOUBLE
            && TileColor.get(world, x, y, z) == stack.getItemDamage();
    }

    private void join(ItemStack stack, World world, int x, int y, int z) {
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
        if (world.checkNoEntityCollision(box) && world.setBlockMetadataWithNotify(x, y, z, MDBlockDyedSlab.DOUBLE, 3)) {
            Block block = this.field_150939_a;
            world.playSoundEffect(
                x + 0.5F,
                y + 0.5F,
                z + 0.5F,
                block.stepSound.func_150496_b(),
                (block.stepSound.getVolume() + 1.0F) / 2.0F,
                block.stepSound.getPitch() * 0.8F);
            --stack.stackSize;
        }
    }
}
