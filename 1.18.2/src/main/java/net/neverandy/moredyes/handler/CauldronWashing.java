package net.neverandy.moredyes.handler;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neverandy.moredyes.reference.Reference;

import java.util.Optional;

/**
 * Right-clicking a cauldron that has water in it with a stack of dyed blocks washes the whole stack back to the
 * vanilla block and uses up one level of water, like washing dyed leather armor. The vanilla block is the result of
 * the block's "washing/" crafting recipe (dyed block plus water bucket), so a data pack that changes that recipe
 * changes the cauldron too.
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class CauldronWashing
{
    private CauldronWashing() {}

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        Level world = event.getWorld();
        BlockState state = world.getBlockState(event.getPos());
        ItemStack held = event.getItemStack();
        if (!state.is(Blocks.WATER_CAULDRON) || held.isEmpty())
        {
            return;
        }
        int water = state.getValue(LayeredCauldronBlock.LEVEL);
        ItemStack washed = washed(world, held);
        if (water <= 0 || washed.isEmpty())
        {
            return;
        }

        // Cancelled on both sides so the held block is not placed; the client still sends the click to the server.
        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.sidedSuccess(world.isClientSide));
        if (world.isClientSide)
        {
            return;
        }
        Player player = event.getPlayer();
        ItemStack result = washed.copy();
        result.setCount(held.getCount());
        player.setItemInHand(event.getHand(), result);
        LayeredCauldronBlock.lowerFillLevel(state, world, event.getPos());
        player.awardStat(Stats.USE_CAULDRON);
    }

    /** The vanilla block a dyed block washes back into, or an empty stack if it isn't a dyed block. */
    private static ItemStack washed(Level world, ItemStack dyed)
    {
        ResourceLocation item = dyed.getItem().getRegistryName();
        if (item == null || !Reference.MOD_ID.equals(item.getNamespace()))
        {
            return ItemStack.EMPTY;
        }
        Optional<? extends Recipe<?>> recipe = world.getRecipeManager()
                .byKey(new ResourceLocation(Reference.MOD_ID, "washing/" + item.getPath()));
        return recipe.map(Recipe::getResultItem).orElse(ItemStack.EMPTY);
    }
}
