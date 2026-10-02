package info.kg6jay.moredyes.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.reference.Reference;
import info.kg6jay.moredyes.utility.BlockInfo;

/**
 * Plain (undyed) granite, diorite or andesite, the base for the dyed versions. Each generates in the world and has a
 * crafting recipe only when no other mod provides it (see MDBlock.useOwnStone). Unlike the dyed blocks these follow
 * the vanilla mob spawning rules, because they generate underground like stone.
 * <p>
 * They share the grey texture of the dyed version, tinted with the stone's natural color.
 */
public class MDBlockPlainStone extends Block {

    private final String name;
    private final int color;

    public MDBlockPlainStone(BlockInfo info, int color) {
        super(info.blockMaterial);
        this.name = info.blockName;
        this.color = color;
        this.setHardness(info.hardness);
        this.setResistance(info.resistance);
        this.setHarvestLevel(info.harvestTool, info.harvestLevel);
        this.setStepSound(info.sound);
        this.setBlockName(Reference.MOD_ID + "." + this.name);
        this.setCreativeTab(info.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = TintedTextures.register(iconRegister, this.name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return this.color;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return this.color;
    }
}
