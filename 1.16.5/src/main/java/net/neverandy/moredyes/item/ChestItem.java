package net.neverandy.moredyes.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

/** A dyed chest item. It burns like a vanilla chest, and the client draws it with client/ChestItemRenderer. */
public class ChestItem extends BlockItem
{
    public ChestItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public int getBurnTime(ItemStack stack)
    {
        return 300;
    }
}
