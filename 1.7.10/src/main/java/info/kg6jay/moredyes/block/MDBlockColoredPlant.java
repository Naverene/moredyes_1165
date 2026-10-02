package info.kg6jay.moredyes.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.block.ILayeredBlock.RenderLayer;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.utility.BlockInfo;
import info.kg6jay.moredyes.utility.ColorUtil;

/**
 * A dyed plant drawn as crossed squares in two layers: the dyed part (petals, leaves) tinted, and the rest (stem,
 * trunk) in its natural color.
 */
public abstract class MDBlockColoredPlant extends MDBlockColored implements ILayeredBlock {

    private final String tintedTexture, naturalTexture;
    @SideOnly(Side.CLIENT)
    protected IIcon tintedIcon, naturalIcon;

    protected MDBlockColoredPlant(String[] colors, BlockInfo info, String colorSet, String tintedTexture,
        String naturalTexture) {
        super(colors, info, colorSet);
        this.tintedTexture = tintedTexture;
        this.naturalTexture = naturalTexture;
    }

    @Override
    public int getRenderType() {
        return RenderIds.layeredPlant;
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
    public int getLayerCount() {
        return 2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getLayerIcon(int side, int meta, int layer) {
        return layer == 0 ? this.tintedIcon : this.naturalIcon;
    }

    @Override
    public int getLayerColor(int meta, int layer) {
        return layer == 0 ? ColorUtil.shade(this.blockColors, meta) : ColorUtil.WHITE;
    }

    @Override
    public boolean isPlant() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.getLayerIcon(side, meta, Math.max(RenderLayer.current, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return this.getLayerColor(world.getBlockMetadata(x, y, z), Math.max(RenderLayer.current, 0));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.tintedIcon = TintedTextures.register(iconRegister, this.tintedTexture);
        this.naturalIcon = TintedTextures.register(iconRegister, this.naturalTexture);
    }
}
