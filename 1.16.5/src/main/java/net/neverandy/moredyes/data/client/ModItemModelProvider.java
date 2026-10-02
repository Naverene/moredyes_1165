package net.neverandy.moredyes.data.client;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.neverandy.moredyes.MoreDyes;
import net.neverandy.moredyes.block.BlockChest;
import net.neverandy.moredyes.block.BlockFence;
import net.neverandy.moredyes.block.BlockGlassPane;
import net.neverandy.moredyes.block.BlockPiston;
import net.neverandy.moredyes.block.BlockPistonHead;
import net.neverandy.moredyes.block.BlockSapling;
import net.neverandy.moredyes.block.DyedShapes;
import net.neverandy.moredyes.block.FlowerBlock;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.item.MDItem;
import net.neverandy.moredyes.item.MDItemDye;
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
            if (block instanceof BlockPistonHead || block instanceof SlabBlock || block instanceof StairsBlock || block instanceof WallBlock)
            {
                // Piston heads have no item; slabs, stairs and walls are below.
                continue;
            }
            String name = entry.getId().getPath();
            // Registry names look like "<type>_<color>", e.g. "oakfence_334c59" or "saplingoak_334c59".
            String type = name.substring(0, name.lastIndexOf('_'));

            if (block instanceof BlockFence)
            {
                getBuilder(name).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name + "_inventory")));
            }
            else if (block instanceof BlockSapling)
            {
                // "saplingdarkoak" -> "dark_oak"
                String wood = type.substring("sapling".length()).replace("darkoak", "dark_oak");
                layered(name, wood + "_sapling_leaves", wood + "_sapling_trunk");
            }
            else if (block instanceof BlockChest)
            {
                // The vanilla chest item model draws the item with its item renderer (client/ChestItemRenderer).
                withExistingParent(name, mcLoc("item/chest"));
            }
            else if (block instanceof BlockGlassPane)
            {
                // Like vanilla, a pane item is its glass texture drawn flat.
                String glass = name.startsWith("glassfoggy") ? "glass_foggy" : "glass";
                withExistingParent(name, mcLoc("item/generated")).texture("layer0", modLoc("block/tinted/" + glass));
            }
            else if (block instanceof BlockPiston)
            {
                String model = ((BlockPiston) block).isSticky() ? "sticky_piston" : "piston";
                withExistingParent(name, modLoc("block/tinted/" + model));
            }
            else if (block instanceof FlowerBlock)
            {
                // "tulip", "allium", "lilyofthevalley"...
                layered(name, type + "_petals", type + "_stem");
            }
            else if (block instanceof TallFlowerBlock)
            {
                // Like vanilla, the item is the top half.
                layered(name, type + "_top_petals", type + "_top_stem");
            }
            else
            {
                getBuilder(name).parent(new ModelFile.UncheckedModelFile(modLoc("block/" + name)));
            }
        }

        for (DyedShapes shapes : DyedShapes.ALL)
        {
            String base = "block/shape/" + shapes.type;
            for (int i = 0; i < shapes.slabs.length; i++)
            {
                withExistingParent(shapes.slabs[i].getRegistryName().getPath(), modLoc(base + "_slab"));
                withExistingParent(shapes.stairs[i].getRegistryName().getPath(), modLoc(base + "_stairs"));
            }
            for (WallBlock wall : shapes.walls)
            {
                withExistingParent(wall.getRegistryName().getPath(), modLoc(base + "_wall_inventory"));
            }
        }

        // Dyes are one grey dye texture; the item color handler tints layer 0.
        for (MDItemDye dye : MDItem.dye)
        {
            singleTexture(dye.getRegistryName().getPath(), mcLoc("item/generated"), "layer0", modLoc("block/tinted/dye"));
        }
    }

    /** A flat item whose first layer takes the dye color and second layer keeps its natural color. */
    private void layered(String name, String tinted, String natural)
    {
        withExistingParent(name, mcLoc("item/generated"))
                .texture("layer0", modLoc("block/tinted/" + tinted))
                .texture("layer1", modLoc("block/tinted/" + natural));
    }
}
