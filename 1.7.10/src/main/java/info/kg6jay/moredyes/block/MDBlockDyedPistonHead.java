package info.kg6jay.moredyes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.ColorIndex;

/**
 * The head of an extended dyed piston (see MDBlockDyedPiston), one block for both kinds and every color. Like vanilla's
 * it has no item; it takes the color of the piston behind it, and its metadata holds the direction, plus 8 when the
 * piston is sticky.
 * <p>
 * Vanilla's head only stays attached to the vanilla pistons, so the methods that look at the piston behind it are
 * redone here for the dyed ones.
 */
public class MDBlockDyedPistonHead extends BlockPistonExtension {

    @SideOnly(Side.CLIENT)
    private IIcon sideIcon, topIcon, topStickyIcon;

    public MDBlockDyedPistonHead() {
        this.setBlockName(Reference.MOD_ID + ".pistonHead");
    }

    /** The dyed piston behind a head with this metadata, or null if there is none. */
    private static MDBlockDyedPiston pistonBehind(IBlockAccess world, int x, int y, int z, int meta) {
        int facing = getDirectionMeta(meta);
        Block block = world.getBlock(
            x - Facing.offsetsXForSide[facing],
            y - Facing.offsetsYForSide[facing],
            z - Facing.offsetsZForSide[facing]);
        return block instanceof MDBlockDyedPiston piston ? piston : null;
    }

    /** In creative mode breaking the head also removes the piston, without a drop. */
    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && pistonBehind(world, x, y, z, meta) != null) {
            int facing = getDirectionMeta(meta);
            world.setBlockToAir(
                x - Facing.offsetsXForSide[facing],
                y - Facing.offsetsYForSide[facing],
                z - Facing.offsetsZForSide[facing]);
        }
        super.onBlockHarvested(world, x, y, z, meta, player);
    }

    /** Breaking the head breaks the extended piston behind it, which drops as an item. */
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        super.breakBlock(world, x, y, z, block, meta);
        MDBlockDyedPiston piston = pistonBehind(world, x, y, z, meta);
        if (piston != null) {
            int facing = getDirectionMeta(meta);
            int px = x - Facing.offsetsXForSide[facing];
            int py = y - Facing.offsetsYForSide[facing];
            int pz = z - Facing.offsetsZForSide[facing];
            int pistonMeta = world.getBlockMetadata(px, py, pz);
            if (BlockPistonBase.isExtended(pistonMeta)) {
                piston.dropBlockAsItem(world, px, py, pz, pistonMeta, 0);
                world.setBlockToAir(px, py, pz);
            }
        }
    }

    /** A head without its piston disappears. */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        int meta = world.getBlockMetadata(x, y, z);
        MDBlockDyedPiston piston = pistonBehind(world, x, y, z, meta);
        if (piston == null) {
            world.setBlockToAir(x, y, z);
        } else {
            int facing = getDirectionMeta(meta);
            piston.onNeighborBlockChange(
                world,
                x - Facing.offsetsXForSide[facing],
                y - Facing.offsetsYForSide[facing],
                z - Facing.offsetsZForSide[facing],
                neighbor);
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        MDBlockDyedPiston piston = pistonBehind(world, x, y, z, world.getBlockMetadata(x, y, z));
        return piston == null ? null : new ItemStack(piston, 1, MDBlockDyedPiston.colorAt(world, x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return ColorIndex.rgb(MDBlockDyedPiston.colorAt(world, x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.sideIcon = TintedTextures.register(register, "piston/side");
        this.topIcon = TintedTextures.register(register, "piston/top");
        this.topStickyIcon = TintedTextures.register(register, "piston/topSticky");
    }

    /** Same faces as vanilla. The rod is drawn by vanilla with the vanilla piston texture, so it stays wood. */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int facing = getDirectionMeta(meta);
        if (side == facing) {
            return (meta & 8) != 0 ? this.topStickyIcon : this.topIcon;
        }
        return facing < 6 && side == Facing.oppositeSide[facing] ? this.topIcon : this.sideIcon;
    }
}
