package info.kg6jay.moredyes.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.MoreDyes;
import info.kg6jay.moredyes.block.tileentity.TileEntityMDColor;
import info.kg6jay.moredyes.handler.ConfigHandler;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.ColorIndex;

/**
 * A dyed wall in every color, with the color kept in a tile entity (see TileColor). Dyed walls of every material
 * connect to each other.
 */
public class MDBlockDyedWall extends BlockWall {

    private final TileColor.Icons icons;

    /**
     * @param model    a block of the material the wall is made of, which it takes its hardness and sound from
     * @param name     registry name, such as "graniteWall"
     * @param textures the tinted texture keys, one for all sides or three for top, side and bottom
     */
    public MDBlockDyedWall(Block model, String name, String... textures) {
        super(model);
        this.icons = new TileColor.Icons(textures);
        this.setHarvestLevel(model.getHarvestTool(0), model.getHarvestLevel(0));
        this.setBlockName(Reference.MOD_ID + "." + name);
        this.setCreativeTab(MoreDyes.tabShapes);
    }

    @Override
    public boolean canConnectWallTo(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block instanceof MDBlockDyedWall || block == Blocks.fence_gate) {
            return true;
        }
        return block.getMaterial()
            .isOpaque() && block.renderAsNormalBlock()
            && block.getMaterial() != Material.gourd;
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
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return !ConfigHandler.preventMobSpawning && super.canCreatureSpawn(type, world, x, y, z);
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
