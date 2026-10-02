package info.kg6jay.moredyes.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFlower extends WorldGenerator {

    private Block block;
    private int meta;

    public WorldGenFlower(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public void func_150550_a(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public boolean generate(World world, Random rand, int x, int z) {
        return generate(world, rand, x, world.getTopSolidOrLiquidBlock(x, z), z);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        if (world.isAirBlock(x, y, z)) {
            if (!world.provider.hasNoSky) {
                if (y < 255) {
                    if (this.block.canBlockStay(world, x, y - 1, z)) {
                        world.setBlock(x, y, z, this.block, this.meta, 2);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
