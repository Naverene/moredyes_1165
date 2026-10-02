package info.kg6jay.moredyes.block;

import java.util.List;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.MoreDyes;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.handler.ConfigHandler;
import info.kg6jay.moredyes.handler.GuiHandler;
import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.utility.ColorUtil;

public class MDBlockWorkbench extends BlockWorkbench implements IBlockColored {

    protected String[] blockColors;
    protected String blockName, colorSet;
    @SideOnly(Side.CLIENT)
    protected IIcon topIcon, sideIcon, frontIcon, bottomIcon;

    public MDBlockWorkbench(String[] colors, BlockInfo info, String colorSet) {
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
    }

    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return !ConfigHandler.preventMobSpawning && super.canCreatureSpawn(type, world, x, y, z);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < blockColors.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return switch (side) {
            case 0 -> this.bottomIcon;
            case 1 -> this.topIcon;
            case 2, 4 -> this.frontIcon;
            default -> this.sideIcon;
        };
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

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.topIcon = TintedTextures.register(iconRegister, this.blockName + "/top");
        this.bottomIcon = TintedTextures.register(iconRegister, "plank");
        this.frontIcon = TintedTextures.register(iconRegister, this.blockName + "/front");
        this.sideIcon = TintedTextures.register(iconRegister, this.blockName + "/side");
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
        float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(MoreDyes.instance, GuiHandler.COLORED_WORKBENCH_GUI_ID, world, x, y, z);
        }
        return true;
    }
}
