package info.kg6jay.moredyes.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.block.MDBlockColored;
import info.kg6jay.moredyes.world.feature.WorldGenFlower;

public class FlowerGenerator implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        switch (world.provider.dimensionId) {
            case -1:
                break;
            case 0:
                generateSurface(random, chunkX * 16, chunkZ * 16, world);
                break;
            case 1:
                break;
            default:
                generateSurface(random, chunkX * 16, chunkZ * 16, world);
        }

    }

    private void generateSurface(Random random, int x, int z, World world) {
        generateFlowers(MDBlock.tulip, random, x, z, world);
        generateFlowers(MDBlock.cornflower, random, x, z, world);
    }

    private void generateFlowers(Block[] flowers, Random random, int x, int z, World world) {
        for (int a = 0; a < flowers.length; a++) {
            for (int b = 0; b <= ((MDBlockColored) flowers[a]).getMaxMeta(); b++) {
                if (random.nextInt(100) <= 5) {
                    int xGen = random.nextInt(16) + x;
                    int zGen = random.nextInt(16) + z;
                    int yGen = world.getTopSolidOrLiquidBlock(xGen, zGen);
                    new WorldGenFlower(flowers[a], b).generate(world, random, xGen, yGen, zGen);
                }
            }
        }
    }
}
