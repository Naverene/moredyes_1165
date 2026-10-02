package info.kg6jay.moredyes.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.MoreDyes;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDColor;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.utility.ColorIndex;

/**
 * Dyed iron trapdoor in every color (Minecraft 1.8). Like an iron door it only opens with redstone. The metadata holds
 * the direction and whether it is open, so the color is kept in a tile entity (see TileColor).
 */
public class MDBlockDyedTrapdoor extends BlockTrapDoor {

    private final TileColor.Icons icons;

    public MDBlockDyedTrapdoor(BlockInfo info, String name) {
        super(Material.iron);
        this.icons = new TileColor.Icons(info.blockName);
        this.setHardness(info.hardness);
        this.setResistance(info.resistance);
        this.setStepSound(info.sound);
        this.setHarvestLevel(info.harvestTool, info.harvestLevel);
        this.setBlockName(Reference.MOD_ID + "." + name);
        this.setCreativeTab(MoreDyes.tabShapes);
    }

    /**
     * Same as vanilla, except that an unsupported trapdoor drops before it is removed, so the drop can still read the
     * color from the tile entity.
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            int supportX = x, supportZ = z;
            switch (meta & 3) {
                case 0 -> supportZ++;
                case 1 -> supportZ--;
                case 2 -> supportX++;
                default -> supportX--;
            }
            if (!isSupport(world.getBlock(supportX, y, supportZ))
                && !world.isSideSolid(supportX, y, supportZ, ForgeDirection.getOrientation((meta & 3) + 2))) {
                this.dropBlockAsItem(world, x, y, z, meta, 0);
                world.setBlockToAir(x, y, z);
                return;
            }
        }
        super.onNeighborBlockChange(world, x, y, z, neighbor);
    }

    /** The blocks a vanilla trapdoor can hang on. */
    private static boolean isSupport(Block block) {
        if (disableValidation) {
            return true;
        }
        return block.getMaterial()
            .isOpaque() && block.renderAsNormalBlock() || block == Blocks.glowstone
            || block instanceof BlockSlab
            || block instanceof BlockStairs;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileEntityMDColor();
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        return TileColor.drops(this, world, x, y, z, 1);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(world, player, x, y, z, false);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        world.setBlockToAir(x, y, z);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return TileColor.stack(this, world, x, y, z, 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        TileColor.addSubBlocks(item, list);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return ColorIndex.rgb(meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return TileColor.rgb(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons.register(register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icons.get(side);
    }
}
