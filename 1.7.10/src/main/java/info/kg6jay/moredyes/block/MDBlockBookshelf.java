package info.kg6jay.moredyes.block;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import info.kg6jay.moredyes.client.TintedTextures;
import info.kg6jay.moredyes.utility.BlockInfo;

public class MDBlockBookshelf extends MDBlockColoredMulti {

    public MDBlockBookshelf(String[] colors, BlockInfo info, String colorSet) {
        super(colors, info, colorSet);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.topIcon = TintedTextures.register(iconRegister, "plank");
        this.bottomIcon = this.topIcon;
        this.sideIcon = TintedTextures.register(iconRegister, "bookshelf");
    }

    /** Counts toward an enchanting table's power, like a vanilla bookshelf. */
    @Override
    public float getEnchantPowerBonus(World world, int x, int y, int z) {
        return 1.0F;
    }

    @Override
    public int quantityDropped(Random random) {
        return 3;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Items.book;
    }
}
