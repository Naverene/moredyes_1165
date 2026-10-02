package net.neverandy.moredyes.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.network.ModNetwork;
import net.neverandy.moredyes.network.SheepColorPacket;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.reference.Reference;

import java.util.List;

/**
 * Lets vanilla sheep wear any MoreDyes color. Vanilla sheep only know the 16 vanilla colors, so the MoreDyes color is
 * kept in the sheep's Forge data (saved with the sheep) and sent to the players who can see it. The client draws the
 * wool in that color (client/DyedSheepRenderer), and vanilla wool the sheep drops is swapped for the dyed wool.
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public final class DyedSheep
{
    private static final String KEY = Reference.MOD_ID + ":fleece";

    private DyedSheep() {}

    /** The sheep's MoreDyes color as an index into ColorStrings.ALL, or -1 if it has a vanilla color. */
    public static int getColor(Sheep sheep)
    {
        CompoundTag data = sheep.getPersistentData();
        if (!data.contains(KEY))
        {
            return -1;
        }
        String hex = data.getString(KEY);
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            if (ColorStrings.ALL[i].equals(hex))
            {
                return i;
            }
        }
        return -1;
    }

    /** Sets the MoreDyes color (-1 for none) and, on the server, tells the players who can see the sheep. */
    public static void setColor(Sheep sheep, int color)
    {
        if (color < 0)
        {
            sheep.getPersistentData().remove(KEY);
        }
        else
        {
            sheep.getPersistentData().putString(KEY, ColorStrings.ALL[color]);
        }
        if (!sheep.level().isClientSide && sheep.isAddedToWorld())
        {
            ModNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> sheep), new SheepColorPacket(sheep.getId(), color));
        }
    }

    /** Dyes a sheep with a MoreDyes dye. Called from MDItemDye. */
    public static InteractionResult dye(Sheep sheep, int color, ItemStack stack)
    {
        if (!sheep.isAlive() || sheep.isSheared() || getColor(sheep) == color)
        {
            return InteractionResult.PASS;
        }
        if (!sheep.level().isClientSide)
        {
            setColor(sheep, color);
            stack.shrink(1);
        }
        return InteractionResult.sidedSuccess(sheep.level().isClientSide);
    }

    /** A vanilla dye on a sheep with a MoreDyes color gives it that vanilla color again. */
    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event)
    {
        ItemStack stack = event.getItemStack();
        if (!(event.getTarget() instanceof Sheep sheep) || !(stack.getItem() instanceof DyeItem dye))
        {
            return;
        }
        if (!sheep.isAlive() || sheep.isSheared() || getColor(sheep) < 0)
        {
            return;
        }
        if (!sheep.level().isClientSide)
        {
            setColor(sheep, -1);
            sheep.setColor(dye.getDyeColor());
            if (!event.getEntity().getAbilities().instabuild)
            {
                stack.shrink(1);
            }
        }
        event.setCancellationResult(InteractionResult.sidedSuccess(sheep.level().isClientSide));
        event.setCanceled(true);
    }

    /**
     * Shears and dispensers drop the wool one block above the sheep, right after marking it sheared. Vanilla wool
     * that appears exactly there, above a sheared sheep with a MoreDyes color, becomes that color's wool.
     */
    @SubscribeEvent
    public static void onItemSpawn(EntityJoinLevelEvent event)
    {
        if (event.getLevel().isClientSide || !(event.getEntity() instanceof ItemEntity item))
        {
            return;
        }
        ItemStack stack = item.getItem();
        if (!isVanillaWool(stack))
        {
            return;
        }
        double x = item.getX(), y = item.getY() - 1.0D, z = item.getZ();
        // Items also join the world when their chunk loads from disk. Asking that chunk for its sheep then would wait
        // for the chunk to finish loading, which never happens, and the world hangs at "Preparing spawn area".
        if (event.loadedFromDisk() || event.getLevel().getChunkSource().getChunkNow(Mth.floor(x) >> 4, Mth.floor(z) >> 4) == null)
        {
            return;
        }
        List<Sheep> sheep = event.getLevel().getEntitiesOfClass(Sheep.class,
                new AABB(x - 0.01D, y - 0.01D, z - 0.01D, x + 0.01D, y + 0.01D, z + 0.01D),
                s -> s.isSheared() && getColor(s) >= 0);
        if (!sheep.isEmpty())
        {
            item.setItem(new ItemStack(MDBlock.woolArray[getColor(sheep.get(0))], stack.getCount()));
        }
    }

    /** A dyed sheep that is killed drops its dyed wool. */
    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event)
    {
        if (!(event.getEntity() instanceof Sheep sheep))
        {
            return;
        }
        int color = getColor(sheep);
        if (color < 0)
        {
            return;
        }
        for (ItemEntity drop : event.getDrops())
        {
            if (isVanillaWool(drop.getItem()))
            {
                drop.setItem(new ItemStack(MDBlock.woolArray[color], drop.getItem().getCount()));
            }
        }
    }

    /**
     * Some sheep spawn in the world with a random MoreDyes color. FinalizeSpawn fires both for sheep placed when a
     * chunk is generated and for sheep that spawn later; it runs last so it sees whether another mod cancelled it.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpawn(MobSpawnEvent.FinalizeSpawn event)
    {
        if (!(event.getEntity() instanceof Sheep sheep) || event.isCanceled()
                || (event.getSpawnType() != MobSpawnType.NATURAL && event.getSpawnType() != MobSpawnType.CHUNK_GENERATION))
        {
            return;
        }
        if (sheep.getRandom().nextDouble() < ConfigHandler.sheepSpawnChance.get())
        {
            setColor(sheep, sheep.getRandom().nextInt(ColorStrings.ALL.length));
        }
    }

    /** A lamb takes the color of one of its parents, picked at random. */
    @SubscribeEvent
    public static void onBreed(BabyEntitySpawnEvent event)
    {
        if (!(event.getParentA() instanceof Sheep a) || !(event.getParentB() instanceof Sheep b) || !(event.getChild() instanceof Sheep child))
        {
            return;
        }
        if (getColor(a) < 0 && getColor(b) < 0)
        {
            return;
        }
        Sheep parent = child.getRandom().nextBoolean() ? a : b;
        if (getColor(parent) >= 0)
        {
            setColor(child, getColor(parent));
        }
        else
        {
            child.setColor(parent.getColor());
        }
    }

    /** A player who starts seeing a dyed sheep is told its color. */
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof Sheep sheep && event.getEntity() instanceof ServerPlayer player)
        {
            int color = getColor(sheep);
            if (color >= 0)
            {
                ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SheepColorPacket(sheep.getId(), color));
            }
        }
    }

    private static boolean isVanillaWool(ItemStack stack)
    {
        return stack.is(ItemTags.WOOL) && "minecraft".equals(ForgeRegistries.ITEMS.getKey(stack.getItem()).getNamespace());
    }
}
