package info.kg6jay.moredyes.block;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.item.MDItem;
import info.kg6jay.moredyes.utility.BlockInfo;

public class MDBlockLeaf extends MDBlockColored implements IShearable {

    private static final int DECAY_RANGE = 4;
    private static final int DECAY_DELAY_MIN = 10;
    private static final int DECAY_DELAY_RANDOM = 100;
    private static final int[][] NEIGHBOURS = { { 1, 0, 0 }, { -1, 0, 0 }, { 0, 1, 0 }, { 0, -1, 0 }, { 0, 0, 1 },
        { 0, 0, -1 } };

    int blockIndex;

    public MDBlockLeaf(String[] colors, BlockInfo info, String colorSet, int blockIndex) {
        super(colors, info, colorSet);
        this.setLightOpacity(1);
        this.blockIndex = blockIndex;
    }

    /**
     * Vanilla leaves keep "check decay" and "placed by a player" flags in their metadata, but here the metadata holds
     * the shade, so there is no room for them. Instead, a decay check is scheduled when a nearby log is removed (logs
     * call beginLeavesDecay) and passed on to the neighbours when a leaf decays, so a whole canopy falls apart once
     * its tree is gone. Breaking a leaf by hand does not start a check, so leaves used for building are safe unless
     * a log next to them is removed.
     */
    @Override
    public void beginLeavesDecay(World world, int x, int y, int z) {
        world.scheduleBlockUpdate(x, y, z, this, DECAY_DELAY_MIN + world.rand.nextInt(DECAY_DELAY_RANDOM));
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote || this.isSupported(world, x, y, z)) {
            return;
        }
        this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlockToAir(x, y, z);

        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                for (int dz = -1; dz <= 1; ++dz) {
                    Block neighbour = world.getBlock(x + dx, y + dy, z + dz);
                    if (neighbour.isLeaves(world, x + dx, y + dy, z + dz)) {
                        neighbour.beginLeavesDecay(world, x + dx, y + dy, z + dz);
                    }
                }
            }
        }
    }

    /**
     * True if a log (or anything else that sustains leaves) can be reached within DECAY_RANGE steps, moving only
     * through leaves. This is the same rule vanilla leaves use.
     */
    private boolean isSupported(World world, int x, int y, int z) {
        int r = DECAY_RANGE;
        if (!world.checkChunksExist(x - r - 1, y - r - 1, z - r - 1, x + r + 1, y + r + 1, z + r + 1)) {
            // Never decay into unloaded chunks; the tree may continue there.
            return true;
        }
        int size = 2 * r + 1;
        boolean[] visited = new boolean[size * size * size];
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] { x, y, z, 0 });
        visited[((r * size) + r) * size + r] = true;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            for (int[] d : NEIGHBOURS) {
                int nx = pos[0] + d[0], ny = pos[1] + d[1], nz = pos[2] + d[2];
                int ox = nx - x + r, oy = ny - y + r, oz = nz - z + r;
                if (ox < 0 || oy < 0 || oz < 0 || ox >= size || oy >= size || oz >= size) {
                    continue;
                }
                int index = (ox * size + oy) * size + oz;
                if (visited[index]) {
                    continue;
                }
                visited[index] = true;
                Block block = world.getBlock(nx, ny, nz);
                if (block.canSustainLeaves(world, nx, ny, nz)) {
                    return true;
                }
                if (pos[3] + 1 < r && block.isLeaves(world, nx, ny, nz)) {
                    queue.add(new int[] { nx, ny, nz, pos[3] + 1 });
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates. Args: blockAccess, x, y, z, side
     */
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (world.canLightningStrikeAt(x, y + 1, z) && !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
            && rand.nextInt(15) == 1) {
            double d0 = (double) ((float) x + rand.nextFloat());
            double d1 = (double) y - 0.05D;
            double d2 = (double) ((float) z + rand.nextFloat());
            world.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random rand) {
        return rand.nextInt(10) == 0 ? 1 : 0;
    }

    public Item getItemDropped(int meta, Random rand, int fortune) {

        return Item.getItemFromBlock(MDBlock.sapling[this.blockIndex]);
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float p_149690_6_, int chance) {
        super.dropBlockAsItemWithChance(world, x, y, z, meta, 1.0f, chance);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube? This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     */
    protected ItemStack createStackedBlock(int meta) {
        return new ItemStack(Item.getItemFromBlock(this), 1, meta);
    }

    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z)));
        return ret;
    }

    @Override
    public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        int chance = 20;

        if (fortune > 0) {
            chance -= 2 << fortune;
            if (chance < 10) chance = 10;
        }

        if (world.rand.nextInt(chance) <= 1)
            ret.add(new ItemStack(this.getItemDropped(metadata, world.rand, fortune), 1, this.damageDropped(metadata)));

        chance = 200;
        if (fortune > 0) {
            chance -= 10 << fortune;
            if (chance < 40) chance = 40;
        }
        if (world.rand.nextInt(chance) <= 50) {
            ret.add(new ItemStack(MDItem.dye[this.blockIndex], 1, metadata));
        }
        this.captureDrops(true);
        ret.addAll(this.captureDrops(false));
        return ret;
    }
}
