package info.kg6jay.moredyes.handler;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.world.gen.FlowerGenerator;
import info.kg6jay.moredyes.world.gen.StoneGenerator;
import info.kg6jay.moredyes.world.gen.TreeGenerator;

public class WorldGenHandler {

    public static void initializeWorldGen() {
        if (ConfigHandler.worldgen_flower) registerWorldGen(new FlowerGenerator(), 1);
        if (ConfigHandler.worldgen_tree) registerWorldGen(new TreeGenerator(), 1);
        if (ConfigHandler.worldgen_granite)
            registerWorldGen(new StoneGenerator(() -> MDBlock.granitePlain, () -> MDBlock.useOwnGranite), 0);
        if (ConfigHandler.worldgen_diorite)
            registerWorldGen(new StoneGenerator(() -> MDBlock.dioritePlain, () -> MDBlock.useOwnDiorite), 0);
        if (ConfigHandler.worldgen_andesite)
            registerWorldGen(new StoneGenerator(() -> MDBlock.andesitePlain, () -> MDBlock.useOwnAndesite), 0);
    }

    public static void registerWorldGen(IWorldGenerator worldGenerator, int weightedProbability) {
        GameRegistry.registerWorldGenerator(worldGenerator, weightedProbability);
    }
}
