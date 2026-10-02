package info.kg6jay.moredyes.block;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.utility.BlockInfo;

/** A dyed block with different textures on the top, sides and bottom (quartz, sandstone). */
public class MDBlockColoredMulti extends MDBlockColored {

    @SideOnly(Side.CLIENT)
    protected IIcon topIcon, sideIcon, bottomIcon;

    public MDBlockColoredMulti(String[] colors, BlockInfo info, String colorSet) {
        super(colors, info, colorSet);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return switch (side) {
            case 0 -> this.bottomIcon;
            case 1 -> this.topIcon;
            default -> this.sideIcon;
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.topIcon = TintedTextures.register(iconRegister, this.blockName + "/top");
        this.sideIcon = TintedTextures.register(iconRegister, this.blockName + "/side");
        this.bottomIcon = TintedTextures.register(iconRegister, this.blockName + "/bottom");
    }
}
