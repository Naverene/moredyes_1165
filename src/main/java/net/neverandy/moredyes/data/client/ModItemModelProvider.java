package net.neverandy.moredyes.data.client;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.BlockFence;
import net.neverandy.moredyes.block.BlockSapling;
import net.neverandy.moredyes.block.FlowerBlock;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;

public class ModItemModelProvider extends ItemModelProvider
{
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels()
    {
        MoreDyes.LOGGER.debug("registerModels");
        for (RegistryObject<Block> entry : MDBlock.BLOCKS.getEntries())
        {
            Block block = entry.get();
            String name = entry.getId().getPath();
            // Registry names look like "<type>_<color>", e.g. "oakfence_334c59" or "saplingoak_334c59".
            String type = name.substring(0, name.lastIndexOf('_'));
            String color = name.substring(name.lastIndexOf('_') + 1);

            if (block instanceof BlockFence)
            {
                String wood = type.substring(0, type.length() - "fence".length());
                fenceInventory(name, modLoc("block/" + wood + "plank/" + color));
            }
            else if (block instanceof BlockSapling)
            {
                String wood = type.substring("sapling".length());
                singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("block/" + wood + "sapling/" + color));
            }
            else if (block instanceof FlowerBlock)
            {
                singleTexture(name, mcLoc("item/generated"), "layer0", modLoc("block/" + type + "/" + color));
            }
            else
            {
                getBuilder(name).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name)));
            }
        }
    }
}
