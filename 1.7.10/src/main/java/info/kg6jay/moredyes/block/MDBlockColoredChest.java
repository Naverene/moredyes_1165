package info.kg6jay.moredyes.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDBlockColoredChest;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.handler.ConfigHandler;
import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.utility.ColorUtil;

/**
 * A dyed chest. Like every other block in the mod, one block exists per color set and the metadata selects the shade
 * within that set. Because the metadata is used for the color, the facing is stored in the tile entity instead of in
 * the metadata (vanilla chests keep their facing in the metadata, which is why this cannot extend BlockChest).
 * Two adjacent chests of the same block and shade join into a double chest.
 */
public class MDBlockColoredChest extends BlockContainer implements IBlockColored {

    private static final int[][] HORIZONTAL = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 } };

    private final Random random = new Random();
    protected String[] blockColors;
    protected String blockName, colorSet;
    @SideOnly(Side.CLIENT)
    protected IIcon icon;

    public MDBlockColoredChest(String[] colors, BlockInfo info, String colorSet) {
        super(info.blockMaterial);
        this.blockColors = colors;
        this.blockName = info.blockName;
        this.colorSet = colorSet;
        this.setHardness(info.hardness);
        this.setHarvestLevel(info.harvestTool, info.harvestLevel);
        this.setStepSound(info.sound);
        this.setResistance(info.resistance);
        char tmp = (char) (((int) this.blockName.charAt(0)) - 32);
        this.setBlockName(colorSet + "Mix" + tmp + this.blockName.substring(1));
        this.setCreativeTab(info.tab);
        this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
    }

    @Override
    public String getColorSet() {
        return this.colorSet;
    }

    @Override
    public int getMaxMeta() {
        return this.blockColors.length - 1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityMDBlockColoredChest();
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
    public int getRenderType() {
        return RenderIds.chest;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < this.blockColors.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    /**
     * The chest model is drawn by the tile entity renderer; this icon (dyed planks) is only used for the breaking
     * particles, just like vanilla chests use the plank texture.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icon = TintedTextures.register(iconRegister, "plank");
        this.blockIcon = this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return ColorUtil.shade(this.blockColors, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return this.getRenderColor(world.getBlockMetadata(x, y, z));
    }

    /**
     * True if the block at the given position is this chest in the same shade, meaning the two can join.
     */
    public boolean isSameChest(IBlockAccess world, int x, int y, int z, int meta) {
        return world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == meta;
    }

    private boolean isPartOfDoubleChest(World world, int x, int y, int z, int meta) {
        for (int[] d : HORIZONTAL) {
            if (this.isSameChest(world, x + d[0], y, z + d[1], meta)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mirrors the vanilla rule that a chest may join at most one other chest. Called by the item block before placing,
     * because the shade (metadata) is not known in canPlaceBlockAt.
     */
    public boolean canPlaceChestAt(World world, int x, int y, int z, int meta) {
        int neighbours = 0;
        for (int[] d : HORIZONTAL) {
            if (this.isSameChest(world, x + d[0], y, z + d[1], meta)) {
                if (this.isPartOfDoubleChest(world, x + d[0], y, z + d[1], meta)) {
                    return false;
                }
                ++neighbours;
            }
        }
        return neighbours <= 1;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (this.isSameChest(world, x, y, z - 1, meta)) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
        } else if (this.isSameChest(world, x, y, z + 1, meta)) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
        } else if (this.isSameChest(world, x - 1, y, z, meta)) {
            this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        } else if (this.isSameChest(world, x + 1, y, z, meta)) {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
        } else {
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
        int meta = world.getBlockMetadata(x, y, z);
        int facing = switch (MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) {
            case 0 -> 2;
            case 1 -> 5;
            case 2 -> 3;
            default -> 4;
        };

        // A double chest must face perpendicular to the axis it lies on, and both halves must face the same way.
        boolean partnerOnXAxis = this.isSameChest(world, x - 1, y, z, meta)
            || this.isSameChest(world, x + 1, y, z, meta);
        boolean partnerOnZAxis = this.isSameChest(world, x, y, z - 1, meta)
            || this.isSameChest(world, x, y, z + 1, meta);
        if (partnerOnXAxis && facing != 2 && facing != 3) {
            facing = 3;
        } else if (partnerOnZAxis && facing != 4 && facing != 5) {
            facing = 5;
        }

        this.setFacing(world, x, y, z, facing);
        for (int[] d : HORIZONTAL) {
            if (this.isSameChest(world, x + d[0], y, z + d[1], meta)) {
                this.setFacing(world, x + d[0], y, z + d[1], facing);
            }
        }

        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMDBlockColoredChest chest && stack.hasDisplayName()) {
            chest.func_145976_a(stack.getDisplayName());
        }
    }

    private void setFacing(World world, int x, int y, int z, int facing) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMDBlockColoredChest chest) {
            chest.setFacing(facing);
            world.markBlockForUpdate(x, y, z);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(world, x, y, z, neighbor);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMDBlockColoredChest) {
            te.updateContainingBlockInfo();
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMDBlockColoredChest chest && !world.isRemote) {
            for (int i = 0; i < chest.getSizeInventory(); ++i) {
                ItemStack stack = chest.getStackInSlot(i);
                if (stack != null) {
                    float dx = this.random.nextFloat() * 0.8F + 0.1F;
                    float dy = this.random.nextFloat() * 0.8F + 0.1F;
                    float dz = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entity = new EntityItem(world, x + dx, y + dy, z + dz, stack.copy());
                    entity.motionX = this.random.nextGaussian() * 0.05D;
                    entity.motionY = this.random.nextGaussian() * 0.05D + 0.2D;
                    entity.motionZ = this.random.nextGaussian() * 0.05D;
                    world.spawnEntityInWorld(entity);
                    chest.setInventorySlotContents(i, null);
                }
            }
            world.func_147453_f(x, y, z, block);
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    /**
     * Returns the inventory to open at the given position (a combined inventory for a double chest), or null if the
     * chest is blocked from opening.
     */
    public IInventory getInventory(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (!(te instanceof TileEntityMDBlockColoredChest)) {
            return null;
        }
        if (world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN)) {
            return null;
        }

        IInventory inventory = (IInventory) te;
        int meta = world.getBlockMetadata(x, y, z);
        for (int[] d : HORIZONTAL) {
            int nx = x + d[0];
            int nz = z + d[1];
            if (!this.isSameChest(world, nx, y, nz, meta)) {
                continue;
            }
            if (world.isSideSolid(nx, y + 1, nz, ForgeDirection.DOWN)) {
                return null;
            }
            TileEntity other = world.getTileEntity(nx, y, nz);
            if (other instanceof TileEntityMDBlockColoredChest) {
                // Same ordering as vanilla: the chest at the lower coordinate is the top half of the GUI.
                if (d[0] < 0 || d[1] < 0) {
                    inventory = new InventoryLargeChest("container.chestDouble", (IInventory) other, inventory);
                } else {
                    inventory = new InventoryLargeChest("container.chestDouble", inventory, (IInventory) other);
                }
            }
        }
        return inventory;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        IInventory inventory = this.getInventory(world, x, y, z);
        if (inventory != null) {
            // The vanilla chest GUI works for any inventory, so no custom GUI or container is needed.
            player.displayGUIChest(inventory);
        }
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return Container.calcRedstoneFromInventory(this.getInventory(world, x, y, z));
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return !ConfigHandler.preventMobSpawning && super.canCreatureSpawn(type, world, x, y, z);
    }
}
