package net.neverandy.moredyes.handler;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
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
        World world = event.getWorld();
        BlockState state = world.getBlockState(event.getPos());
        ItemStack held = event.getItemStack();
        if (!state.matchesBlock(Blocks.CAULDRON) || held.isEmpty())
        {
            return;
        }
        int water = state.get(CauldronBlock.LEVEL);
        ItemStack washed = washed(world, held);
        if (water <= 0 || washed.isEmpty())
        {
            return;
        }

        // Cancelled on both sides so the held block is not placed; the client still sends the click to the server.
        event.setCanceled(true);
        event.setCancellationResult(ActionResultType.func_233537_a_(world.isRemote));
        if (world.isRemote)
        {
            return;
        }
        PlayerEntity player = event.getPlayer();
        ItemStack result = washed.copy();
        result.setCount(held.getCount());
        player.setHeldItem(event.getHand(), result);
        ((CauldronBlock) Blocks.CAULDRON).setWaterLevel(world, event.getPos(), state, water - 1);
        player.addStat(Stats.USE_CAULDRON);
    }

    /** The vanilla block a dyed block washes back into, or an empty stack if it isn't a dyed block. */
    private static ItemStack washed(World world, ItemStack dyed)
    {
        ResourceLocation item = dyed.getItem().getRegistryName();
        if (item == null || !Reference.MOD_ID.equals(item.getNamespace()))
        {
            return ItemStack.EMPTY;
        }
        Optional<? extends IRecipe<?>> recipe = world.getRecipeManager()
                .getRecipe(new ResourceLocation(Reference.MOD_ID, "washing/" + item.getPath()));
        return recipe.map(IRecipe::getRecipeOutput).orElse(ItemStack.EMPTY);
    }
}
