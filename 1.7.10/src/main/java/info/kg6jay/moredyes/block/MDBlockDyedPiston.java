package info.kg6jay.moredyes.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.MoreDyes;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDColor;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDPiston;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.ColorIndex;

/**
 * Dyed piston or sticky piston in every color. The metadata holds the direction and whether it is extended, so the
 * color is kept in a tile entity (see TileColor), the same as the trapdoor.
 * <p>
 * It works like the vanilla piston. The parts of vanilla that place the piston head and the moving blocks are copied
 * here, because they are private and name the vanilla blocks: this piston places MDBlockDyedPistonHead as its head, and
 * moves in a TileEntityMDPiston so the color survives extending and retracting.
 * <p>
 * Other pistons cannot push it: blocks with a tile entity cannot be moved in 1.7.10.
 */
public class MDBlockDyedPiston extends BlockPistonBase {

    private final boolean sticky;
    private final String textureKey;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon, topIcon, innerIcon, bottomIcon;

    public MDBlockDyedPiston(boolean sticky, String name) {
        super(sticky);
        this.sticky = sticky;
        this.textureKey = sticky ? "piston/topSticky" : "piston/top";
        this.setBlockName(Reference.MOD_ID + "." + name);
        this.setCreativeTab(MoreDyes.tabShapes);
    }

    /**
     * The color number of a dyed piston or piston head at this position, whether it is standing still or moving. A
     * head that has stopped takes the color of the piston behind it.
     */
    public static int colorAt(IBlockAccess world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMDPiston moving) {
            return moving.getColor();
        }
        if (te instanceof TileEntityMDColor) {
            return TileColor.get(world, x, y, z);
        }
        if (world.getBlock(x, y, z) instanceof MDBlockDyedPistonHead) {
            int facing = MDBlockDyedPistonHead.getDirectionMeta(world.getBlockMetadata(x, y, z));
            return TileColor.get(
                world,
                x - Facing.offsetsXForSide[facing],
                y - Facing.offsetsYForSide[facing],
                z - Facing.offsetsZForSide[facing]);
        }
        return 0;
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        return new TileEntityMDColor();
    }

    /**
     * Vanilla only checks for power here when there is no tile entity yet, which is never the case for this block,
     * so check always. onNeighborBlockChange does the same check.
     */
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            this.onNeighborBlockChange(world, x, y, z, this);
        }
    }

    /** Vanilla's, with this mod's head and moving tile entity. Event 0 extends, 1 retracts. */
    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int event, int facing) {
        if (!world.isRemote) {
            boolean powered = isIndirectlyPowered(world, x, y, z, facing);
            if (powered && event == 1) {
                world.setBlockMetadataWithNotify(x, y, z, facing | 8, 2);
                return false;
            }
            if (!powered && event == 0) {
                return false;
            }
        }

        int dx = Facing.offsetsXForSide[facing];
        int dy = Facing.offsetsYForSide[facing];
        int dz = Facing.offsetsZForSide[facing];
        if (event == 0) {
            if (!this.tryExtend(world, x, y, z, facing)) {
                return false;
            }
            world.setBlockMetadataWithNotify(x, y, z, facing | 8, 2);
            world.playSoundEffect(
                x + 0.5D,
                y + 0.5D,
                z + 0.5D,
                "tile.piston.out",
                0.5F,
                world.rand.nextFloat() * 0.25F + 0.6F);
        } else if (event == 1) {
            TileEntity head = world.getTileEntity(x + dx, y + dy, z + dz);
            if (head instanceof TileEntityPiston moving) {
                moving.clearPistonTileEntity();
            }

            // The moving block's metadata tells TileEntityMDPistonRenderer which face the retracting head shows.
            int color = colorAt(world, x, y, z);
            world.setBlock(x, y, z, Blocks.piston_extension, facing | (this.sticky ? 8 : 0), 3);
            world.setTileEntity(x, y, z, new TileEntityMDPiston(this, facing, facing, false, true, color));

            if (this.sticky) {
                int px = x + dx * 2, py = y + dy * 2, pz = z + dz * 2;
                Block block = world.getBlock(px, py, pz);
                int meta = world.getBlockMetadata(px, py, pz);
                boolean pulledMoving = false;

                if (block == Blocks.piston_extension) {
                    TileEntity te = world.getTileEntity(px, py, pz);
                    if (te instanceof TileEntityPiston pulled && pulled.getPistonOrientation() == facing
                        && pulled.isExtending()) {
                        pulled.clearPistonTileEntity();
                        block = pulled.getStoredBlockID();
                        meta = pulled.getBlockMetadata();
                        pulledMoving = true;
                    }
                }

                if (!pulledMoving && block.getMaterial() != Material.air
                    && canPushBlock(block, world, px, py, pz, false)
                    && (block.getMobilityFlag() == 0 || block == Blocks.piston || block == Blocks.sticky_piston)) {
                    world.setBlock(x + dx, y + dy, z + dz, Blocks.piston_extension, meta, 3);
                    world.setTileEntity(
                        x + dx,
                        y + dy,
                        z + dz,
                        BlockPistonMoving.getTileEntity(block, meta, facing, false, false));
                    world.setBlockToAir(px, py, pz);
                } else if (!pulledMoving) {
                    world.setBlockToAir(x + dx, y + dy, z + dz);
                }
            } else {
                world.setBlockToAir(x + dx, y + dy, z + dz);
            }

            world.playSoundEffect(
                x + 0.5D,
                y + 0.5D,
                z + 0.5D,
                "tile.piston.in",
                0.5F,
                world.rand.nextFloat() * 0.15F + 0.6F);
        }
        return true;
    }

    /** Vanilla's: powered from any side but the front, or from the block above (quasi-connectivity). */
    private static boolean isIndirectlyPowered(World world, int x, int y, int z, int facing) {
        return facing != 0 && world.getIndirectPowerOutput(x, y - 1, z, 0)
            || facing != 1 && world.getIndirectPowerOutput(x, y + 1, z, 1)
            || facing != 2 && world.getIndirectPowerOutput(x, y, z - 1, 2)
            || facing != 3 && world.getIndirectPowerOutput(x, y, z + 1, 3)
            || facing != 5 && world.getIndirectPowerOutput(x + 1, y, z, 5)
            || facing != 4 && world.getIndirectPowerOutput(x - 1, y, z, 4)
            || world.getIndirectPowerOutput(x, y, z, 0)
            || world.getIndirectPowerOutput(x, y + 2, z, 1)
            || world.getIndirectPowerOutput(x, y + 1, z - 1, 2)
            || world.getIndirectPowerOutput(x, y + 1, z + 1, 3)
            || world.getIndirectPowerOutput(x - 1, y + 1, z, 4)
            || world.getIndirectPowerOutput(x + 1, y + 1, z, 5);
    }

    /** Vanilla's: whether a piston can move this block (destroy: whether it may break blocks like grass). */
    private static boolean canPushBlock(Block block, World world, int x, int y, int z, boolean destroy) {
        if (block == Blocks.obsidian) {
            return false;
        }
        if (block != Blocks.piston && block != Blocks.sticky_piston) {
            if (block.getBlockHardness(world, x, y, z) == -1.0F || block.getMobilityFlag() == 2) {
                return false;
            }
            if (block.getMobilityFlag() == 1) {
                return destroy;
            }
        } else if (isExtended(world.getBlockMetadata(x, y, z))) {
            return false;
        }
        return !block.hasTileEntity(world.getBlockMetadata(x, y, z));
    }

    /**
     * Vanilla's: pushes up to 12 blocks in front of the piston one block forward (breaking a block that pistons break
     * at the end of the line) and starts the head moving out. False if they cannot be pushed.
     */
    private boolean tryExtend(World world, int x, int y, int z, int facing) {
        int dx = Facing.offsetsXForSide[facing];
        int dy = Facing.offsetsYForSide[facing];
        int dz = Facing.offsetsZForSide[facing];

        // Find the end of the line of blocks to push.
        int ex = x + dx, ey = y + dy, ez = z + dz;
        for (int length = 0;; length++) {
            if (ey <= 0 || ey >= world.getHeight() - 1) {
                return false;
            }
            Block block = world.getBlock(ex, ey, ez);
            if (block.getMaterial() == Material.air) {
                break;
            }
            if (!canPushBlock(block, world, ex, ey, ez, true)) {
                return false;
            }
            if (block.getMobilityFlag() == 1) {
                // Forge: snow layers drop nothing, as in vanilla
                float chance = block instanceof BlockSnow ? -1.0F : 1.0F;
                block.dropBlockAsItemWithChance(world, ex, ey, ez, world.getBlockMetadata(ex, ey, ez), chance, 0);
                world.setBlockToAir(ex, ey, ez);
                break;
            }
            if (length == 12) {
                return false;
            }
            ex += dx;
            ey += dy;
            ez += dz;
        }

        // Move each block into the space in front of it, from the end of the line back to the piston.
        int color = colorAt(world, x, y, z);
        int headMeta = facing | (this.sticky ? 8 : 0);
        Block[] moved = new Block[13];
        int count = 0;
        for (int cx = ex, cy = ey, cz = ez; cx != x || cy != y || cz != z; cx -= dx, cy -= dy, cz -= dz) {
            int px = cx - dx, py = cy - dy, pz = cz - dz;
            Block block = world.getBlock(px, py, pz);
            int meta = world.getBlockMetadata(px, py, pz);
            if (block == this && px == x && py == y && pz == z) {
                world.setBlock(cx, cy, cz, Blocks.piston_extension, headMeta, 4);
                world.setTileEntity(
                    cx,
                    cy,
                    cz,
                    new TileEntityMDPiston(MDBlock.pistonHead, headMeta, facing, true, false, color));
            } else {
                world.setBlock(cx, cy, cz, Blocks.piston_extension, meta, 4);
                world.setTileEntity(cx, cy, cz, BlockPistonMoving.getTileEntity(block, meta, facing, true, false));
            }
            moved[count++] = block;
        }

        count = 0;
        for (int cx = ex, cy = ey, cz = ez; cx != x || cy != y || cz != z; cx -= dx, cy -= dy, cz -= dz) {
            world.notifyBlocksOfNeighborChange(cx - dx, cy - dy, cz - dz, moved[count++]);
        }
        return true;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
        ArrayList<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(this, 1, colorAt(world, x, y, z)));
        return drops;
    }

    /** Keep the block (and its color) until harvestBlock has worked out the drops, the same as TileColor's blocks. */
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
        return new ItemStack(this, 1, colorAt(world, x, y, z));
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
        return ColorIndex.rgb(colorAt(world, x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.sideIcon = TintedTextures.register(register, "piston/side");
        this.topIcon = TintedTextures.register(register, this.textureKey);
        this.innerIcon = TintedTextures.register(register, "piston/inner");
        this.bottomIcon = TintedTextures.register(register, "piston/bottom");
    }

    /** Same faces as vanilla: the top shows the inside of the piston while it is extended. */
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        int facing = getPistonOrientation(meta);
        if (facing > 5) {
            return this.topIcon;
        }
        if (side == facing) {
            boolean shortened = isExtended(meta) || this.minX > 0.0D
                || this.minY > 0.0D
                || this.minZ > 0.0D
                || this.maxX < 1.0D
                || this.maxY < 1.0D
                || this.maxZ < 1.0D;
            return shortened ? this.innerIcon : this.topIcon;
        }
        return side == Facing.oppositeSide[facing] ? this.bottomIcon : this.sideIcon;
    }
}
