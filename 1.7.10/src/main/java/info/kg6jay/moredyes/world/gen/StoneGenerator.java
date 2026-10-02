package info.kg6jay.moredyes.world.gen;

import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import cpw.mods.fml.common.IWorldGenerator;

/**
 * Generates granite, diorite or andesite in the overworld the way Minecraft 1.8 does: ten veins of up to 33 blocks
 * per chunk, replacing stone between y 0 and 80. Does nothing when another mod provides that stone (see
 * MDBlock.useOwnGranite and the others), which is only known after the generator is registered, so both the block and
 * that check are looked up when generating.
 */
public class StoneGenerator implements IWorldGenerator {

    private static final int VEINS_PER_CHUNK = 10;
    private static final int VEIN_SIZE = 33;
    private static final int MAX_Y = 80;

    private final Supplier<Block> stone;
    private final BooleanSupplier enabled;
    private WorldGenMinable vein;

    public StoneGenerator(Supplier<Block> stone, BooleanSupplier enabled) {
        this.stone = stone;
        this.enabled = enabled;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        if (!this.enabled.getAsBoolean() || world.provider.dimensionId != 0) {
            return;
        }
        if (this.vein == null) {
            this.vein = new WorldGenMinable(this.stone.get(), 0, VEIN_SIZE, Blocks.stone);
        }
        for (int i = 0; i < VEINS_PER_CHUNK; i++) {
            int x = chunkX * 16 + random.nextInt(16);
            int y = random.nextInt(MAX_Y);
            int z = chunkZ * 16 + random.nextInt(16);
            this.vein.generate(world, random, x, y, z);
        }
    }
}
