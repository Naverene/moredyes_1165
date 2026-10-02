package info.kg6jay.moredyes.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDColor;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.utility.ColorIndex;

/**
 * Shared code for the blocks that keep their color in a TileEntityMDColor (stairs, slabs, walls, trapdoors, pistons).
 * There is one such block for all colors; the item damage is the color's number (see ColorIndex).
 * <p>
 * These blocks keep themselves in the world until harvestBlock when a player breaks them (see removedByPlayer), so
 * the tile entity, and with it the color, is still there when the drops are worked out.
 */
public final class TileColor {

    private TileColor() {}

    /** The color number of the block at this position, or 0 if it has no color tile entity. */
    public static int get(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        return te instanceof TileEntityMDColor color ? color.getColor() : 0;
    }

    public static void set(World world, int x, int y, int z, int color) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMDColor tile) {
            tile.setColor(color);
            world.markBlockForUpdate(x, y, z);
        }
    }

    /** The RGB tint of the block at this position. */
    public static int rgb(IBlockAccess world, int x, int y, int z) {
        return ColorIndex.rgb(get(world, x, y, z));
    }

    public static ItemStack stack(Block block, IBlockAccess world, int x, int y, int z, int count) {
        return new ItemStack(block, count, get(world, x, y, z));
    }

    public static ArrayList<ItemStack> drops(Block block, World world, int x, int y, int z, int count) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        if (count > 0) {
            drops.add(stack(block, world, x, y, z, count));
        }
        return drops;
    }

    /** One item per color, for the creative menu. */
    @SuppressWarnings("unchecked")
    public static void addSubBlocks(Item item, List list) {
        for (int i = 0; i < ColorIndex.count(); i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    /**
     * Tinted icons for the top, sides and bottom. Built from one texture key (all sides alike) or three (top, side,
     * bottom).
     */
    public static final class Icons {

        private final String[] keys;
        @SideOnly(Side.CLIENT)
        private IIcon top, side, bottom;

        public Icons(String... keys) {
            this.keys = keys;
        }

        @SideOnly(Side.CLIENT)
        public void register(IIconRegister register) {
            this.side = TintedTextures.register(register, this.keys.length == 1 ? this.keys[0] : this.keys[1]);
            this.top = this.keys.length == 1 ? this.side : TintedTextures.register(register, this.keys[0]);
            this.bottom = this.keys.length == 1 ? this.side : TintedTextures.register(register, this.keys[2]);
        }

        @SideOnly(Side.CLIENT)
        public IIcon get(int side) {
            return switch (side) {
                case 0 -> this.bottom;
                case 1 -> this.top;
                default -> this.side;
            };
        }
    }
}
