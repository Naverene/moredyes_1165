package info.kg6jay.moredyes.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.MoreDyes;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDColor;
import info.kg6jay.moredyes.handler.ConfigHandler;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.ColorIndex;

/**
 * A dyed slab in every color, with the color kept in a tile entity (see TileColor). Unlike vanilla, the single and
 * double slab are the same block: the metadata is BOTTOM, TOP or DOUBLE. MDItemBlockDyedSlab joins two slabs of the
 * same color into a double slab.
 */
public class MDBlockDyedSlab extends Block {

    public static final int BOTTOM = 0, TOP = 1, DOUBLE = 2;

    private final TileColor.Icons icons;

    /**
     * @param model    a block of the material the slab is made of, which it takes its hardness and sound from
     * @param name     registry name, such as "dioriteSlab"
     * @param textures the tinted texture keys, one for all sides or three for top, side and bottom
     */
    public MDBlockDyedSlab(Block model, String name, String... textures) {
        super(model.getMaterial());
        this.icons = new TileColor.Icons(textures);
        this.setHardness(model.getBlockHardness(null, 0, 0, 0));
        this.setResistance(model.getExplosionResistance(null) * 5.0F / 3.0F);
        this.setStepSound(model.stepSound);
        this.setHarvestLevel(model.getHarvestTool(0), model.getHarvestLevel(0));
        this.setLightOpacity(255);
        this.useNeighborBrightness = true;
        this.setBlockName(Reference.MOD_ID + "." + name);
        this.setCreativeTab(MoreDyes.tabShapes);
        this.setBlockBoundsForItemRender();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        switch (world.getBlockMetadata(x, y, z)) {
            case TOP -> this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            case DOUBLE -> this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            default -> this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
    }

    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list,
        Entity collider) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, mask, list, collider);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == DOUBLE;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        return switch (world.getBlockMetadata(x, y, z)) {
            case DOUBLE -> true;
            case TOP -> side == ForgeDirection.UP;
            default -> side == ForgeDirection.DOWN;
        };
    }

    /** Placed on the upper half of a block's side, or on the underside of a block, it becomes a top slab. */
    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return side != 0 && (side == 1 || hitY <= 0.5F) ? BOTTOM : TOP;
    }

    /** Hides the faces between slabs of this block that touch each other. */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        if (world.getBlock(x, y, z) == this) {
            int other = world.getBlockMetadata(x, y, z);
            int self = world.getBlockMetadata(
                x - Facing.offsetsXForSide[side],
                y - Facing.offsetsYForSide[side],
                z - Facing.offsetsZForSide[side]);
            boolean hidden = switch (side) {
                case 0 -> self != TOP && other != BOTTOM;
                case 1 -> self != BOTTOM && other != TOP;
                default -> other == DOUBLE || other == self;
            };
            if (hidden) {
                return false;
            }
        }
        return super.shouldSideBeRendered(world, x, y, z, side);
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
        return TileColor.drops(this, world, x, y, z, meta == DOUBLE ? 2 : 1);
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
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return !ConfigHandler.preventMobSpawning && world.getBlockMetadata(x, y, z) != BOTTOM
            && super.canCreatureSpawn(type, world, x, y, z);
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
