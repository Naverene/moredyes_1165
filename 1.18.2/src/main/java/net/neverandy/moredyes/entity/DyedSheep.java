package net.neverandy.moredyes.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
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
        if (!sheep.level.isClientSide && sheep.isAddedToWorld())
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
        if (!sheep.level.isClientSide)
        {
            setColor(sheep, color);
            stack.shrink(1);
        }
        return InteractionResult.sidedSuccess(sheep.level.isClientSide);
    }

    /** A vanilla dye on a sheep with a MoreDyes color gives it that vanilla color again. */
    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event)
    {
        ItemStack stack = event.getItemStack();
        if (!(event.getTarget() instanceof Sheep) || !(stack.getItem() instanceof DyeItem))
        {
            return;
        }
        Sheep sheep = (Sheep) event.getTarget();
        if (!sheep.isAlive() || sheep.isSheared() || getColor(sheep) < 0)
        {
            return;
        }
        if (!sheep.level.isClientSide)
        {
            setColor(sheep, -1);
            sheep.setColor(((DyeItem) stack.getItem()).getDyeColor());
            if (!event.getPlayer().getAbilities().instabuild)
            {
                stack.shrink(1);
            }
        }
        event.setCancellationResult(InteractionResult.sidedSuccess(sheep.level.isClientSide));
        event.setCanceled(true);
    }

    /**
     * Shears and dispensers drop the wool one block above the sheep, right after marking it sheared. Vanilla wool
     * that appears exactly there, above a sheared sheep with a MoreDyes color, becomes that color's wool.
     */
    @SubscribeEvent
    public static void onItemSpawn(EntityJoinWorldEvent event)
    {
        if (event.getWorld().isClientSide || !(event.getEntity() instanceof ItemEntity))
        {
            return;
        }
        ItemEntity item = (ItemEntity) event.getEntity();
        ItemStack stack = item.getItem();
        if (!isVanillaWool(stack))
        {
            return;
        }
        double x = item.getX(), y = item.getY() - 1.0D, z = item.getZ();
        // Items also join the world when their chunk loads from disk. Asking that chunk for its sheep then would wait
        // for the chunk to finish loading, which never happens, and the world hangs at "Preparing spawn area".
        if (event.getWorld().getChunkSource().getChunkNow(Mth.floor(x) >> 4, Mth.floor(z) >> 4) == null)
        {
            return;
        }
        List<Sheep> sheep = event.getWorld().getEntitiesOfClass(Sheep.class,
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
        if (!(event.getEntityLiving() instanceof Sheep))
        {
            return;
        }
        int color = getColor((Sheep) event.getEntityLiving());
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
     * Some sheep spawn in the world with a random MoreDyes color. CheckSpawn is the one spawn event that fires both
     * for sheep placed when a chunk is generated and for sheep that spawn later; it runs last so it sees whether
     * another mod denied the spawn.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        if (!(event.getEntity() instanceof Sheep) || event.getResult() == Event.Result.DENY
                || (event.getSpawnReason() != MobSpawnType.NATURAL && event.getSpawnReason() != MobSpawnType.CHUNK_GENERATION))
        {
            return;
        }
        Sheep sheep = (Sheep) event.getEntity();
        if (sheep.getRandom().nextDouble() < ConfigHandler.sheepSpawnChance.get())
        {
            setColor(sheep, sheep.getRandom().nextInt(ColorStrings.ALL.length));
        }
    }

    /** A lamb takes the color of one of its parents, picked at random. */
    @SubscribeEvent
    public static void onBreed(BabyEntitySpawnEvent event)
    {
        if (!(event.getParentA() instanceof Sheep) || !(event.getParentB() instanceof Sheep)
                || !(event.getChild() instanceof Sheep))
        {
            return;
        }
        Sheep a = (Sheep) event.getParentA();
        Sheep b = (Sheep) event.getParentB();
        Sheep child = (Sheep) event.getChild();
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
        if (event.getTarget() instanceof Sheep && event.getPlayer() instanceof ServerPlayer)
        {
            int color = getColor((Sheep) event.getTarget());
            if (color >= 0)
            {
                ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getPlayer()),
                        new SheepColorPacket(event.getTarget().getId(), color));
            }
        }
    }

    private static boolean isVanillaWool(ItemStack stack)
    {
        return stack.is(ItemTags.WOOL) && "minecraft".equals(stack.getItem().getRegistryName().getNamespace());
    }
}
