package net.neverandy.moredyes.utility;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.neverandy.moredyes.reference.Reference;

public class MDTags
{
    public static class Blocks
    {
        private static Tags.IOptionalNamedTag<Block> createTag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(Reference.MOD_ID, name));
        }
        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }
    public static class Items
    {
        //public static final Tags.IOptionalNamedTag<Item> DYES = createForgeTag("dyes");

        private static Tags.IOptionalNamedTag<Item> createTag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation(Reference.MOD_NAME, name));
        }
        private static Tags.IOptionalNamedTag<Item> createForgeTag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}