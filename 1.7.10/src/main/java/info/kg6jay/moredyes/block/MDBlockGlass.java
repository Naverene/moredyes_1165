package info.kg6jay.moredyes.block;

import java.util.List;

import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.handler.ConfigHandler;
import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.utility.ColorUtil;

public class MDBlockGlass extends BlockStainedGlass implements IBlockColored {

    @SideOnly(Side.CLIENT)
    protected IIcon icon;
    protected String[] blockColors;
    protected String blockName, colorSet;
    private int blockIndex;
    protected String variant;

    public MDBlockGlass(String[] colors, BlockInfo info, String colorSet, int index, String variant) {
        super(info.blockMaterial);
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
        this.setBlockName(colorSet + "Mix" + tmp + this.blockName.substring(1) + tmpVar + this.variant.substring(1));
        this.setCreativeTab(info.tab);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icon = TintedTextures.register(iconRegister, this.blockName + "/" + this.variant);
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return !ConfigHandler.preventMobSpawning && super.canCreatureSpawn(type, world, x, y, z);
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock() {
        return false;
    }

    public int damageDropped(int meta) {
        return meta;
    }

    public String getColorSet() {
        return this.colorSet;
    }

    public int getMaxMeta() {
        return this.blockColors.length - 1;
    }
}
