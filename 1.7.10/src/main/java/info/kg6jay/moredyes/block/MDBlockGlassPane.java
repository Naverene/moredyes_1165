package info.kg6jay.moredyes.block;

import java.util.List;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.handler.ConfigHandler;
import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.utility.ColorUtil;

public class MDBlockGlassPane extends BlockPane implements IBlockColored {

    protected String[] blockColors;
    protected String blockName, colorSet, variant;
    private int blockIndex;
    @SideOnly(Side.CLIENT)
    protected IIcon iconBlock, iconPane;

    protected MDBlockGlassPane(String[] colors, BlockInfo info, String colorSet, int index, String variant) {
        // false: like vanilla glass panes, drops nothing unless broken with silk touch
        super("", "", info.blockMaterial, false);
        this.blockIndex = index;
        this.blockColors = colors;
        this.blockName = info.blockName;
        this.colorSet = colorSet;
        this.variant = variant;
        this.setHardness(info.hardness);
        this.setHarvestLevel(info.harvestTool, info.harvestLevel);
        this.setStepSound(info.sound);
        this.setResistance(info.resistance);
        char tmp = (char) (((int) this.blockName.charAt(0)) - 32);
        char tmpVar = (char) (((int) this.variant.charAt(0)) - 32);
        this.setBlockName(
            colorSet + "Mix" + tmp + this.blockName.substring(1) + tmpVar + this.variant.substring(1) + "Pane");
        this.setCreativeTab(info.tab);
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType() {
        return this.blockMaterial == Material.glass ? 41 : 18;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public IIcon func_150097_e() {
        return this.iconPane;
    }

    /**
     * Return true if a player with Silk Touch can harvest this block directly, and not its normal drops.
     */
    protected boolean canSilkHarvest() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.iconBlock = TintedTextures.register(iconRegister, this.blockName + "/" + this.variant);
        this.iconPane = TintedTextures.register(iconRegister, this.blockName + "/pane");
    }

    public boolean canPaneConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir) {
        return canPaneConnectToBlock(world.getBlock(x, y, z)) || world.isSideSolid(x, y, z, dir.getOpposite(), false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < blockColors.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.iconBlock;
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

    public String getColorSet() {
        return this.colorSet;
    }

    public int getMaxMeta() {
        return this.blockColors.length - 1;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return !ConfigHandler.preventMobSpawning && super.canCreatureSpawn(type, world, x, y, z);
    }
}
