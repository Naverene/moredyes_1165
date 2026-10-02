package info.kg6jay.moredyes.block;

import java.util.Random;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import info.kg6jay.moredyes.utility.BlockInfo;

/** Dyed glowstone: full light, and drops 2-4 glowstone dust like vanilla. Silk touch gives the block itself. */
public class MDBlockGlowstone extends MDBlockColored {

    public MDBlockGlowstone(String[] colors, BlockInfo info, String colorSet) {
        super(colors, info, colorSet);
        this.setLightLevel(1.0F);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return MathHelper.clamp_int(this.quantityDropped(random) + random.nextInt(fortune + 1), 1, 4);
    }

    @Override
    public int quantityDropped(Random random) {
        return 2 + random.nextInt(3);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Items.glowstone_dust;
    }

    @Override
    public int damageDropped(int meta) {
        return 0;
    }
}
