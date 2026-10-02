package net.neverandy.moredyes.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;
import net.neverandy.moredyes.client.ChestItemRenderer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/** A dyed chest item. It burns like a vanilla chest, and the client draws it with client/ChestItemRenderer. */
public class ChestItem extends BlockItem
{
    public ChestItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType)
    {
        return 300;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer)
    {
        consumer.accept(new IItemRenderProperties()
        {
            private BlockEntityWithoutLevelRenderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer()
            {
                if (renderer == null)
                {
                    Minecraft minecraft = Minecraft.getInstance();
                    renderer = new ChestItemRenderer(minecraft.getBlockEntityRenderDispatcher(), minecraft.getEntityModels());
                }
                return renderer;
            }
        });
    }
}
