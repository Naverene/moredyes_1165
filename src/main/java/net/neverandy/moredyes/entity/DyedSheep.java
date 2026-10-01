package net.neverandy.moredyes.entity;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.SpawnReason;
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
import net.minecraftforge.fml.network.PacketDistributor;
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
    public static int getColor(SheepEntity sheep)
    {
        CompoundNBT data = sheep.getPersistentData();
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
    public static void setColor(SheepEntity sheep, int color)
    {
        if (color < 0)
        {
            sheep.getPersistentData().remove(KEY);
        }
        else
        {
            sheep.getPersistentData().putString(KEY, ColorStrings.ALL[color]);
        }
        if (!sheep.world.isRemote && sheep.isAddedToWorld())
        {
            ModNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> sheep), new SheepColorPacket(sheep.getEntityId(), color));
        }
    }

    /** Dyes a sheep with a MoreDyes dye. Called from MDItemDye. */
    public static ActionResultType dye(SheepEntity sheep, int color, ItemStack stack)
    {
        if (!sheep.isAlive() || sheep.getSheared() || getColor(sheep) == color)
        {
            return ActionResultType.PASS;
        }
        if (!sheep.world.isRemote)
        {
            setColor(sheep, color);
            stack.shrink(1);
        }
        return ActionResultType.func_233537_a_(sheep.world.isRemote);
    }

    /** A vanilla dye on a sheep with a MoreDyes color gives it that vanilla color again. */
    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event)
    {
        ItemStack stack = event.getItemStack();
        if (!(event.getTarget() instanceof SheepEntity) || !(stack.getItem() instanceof DyeItem))
        {
            return;
        }
        SheepEntity sheep = (SheepEntity) event.getTarget();
        if (!sheep.isAlive() || sheep.getSheared() || getColor(sheep) < 0)
        {
            return;
        }
        if (!sheep.world.isRemote)
        {
            setColor(sheep, -1);
            sheep.setFleeceColor(((DyeItem) stack.getItem()).getDyeColor());
            if (!event.getPlayer().abilities.isCreativeMode)
            {
                stack.shrink(1);
            }
        }
        event.setCancellationResult(ActionResultType.func_233537_a_(sheep.world.isRemote));
        event.setCanceled(true);
    }

    /**
     * Shears and dispensers drop the wool one block above the sheep, right after marking it sheared. Vanilla wool
     * that appears exactly there, above a sheared sheep with a MoreDyes color, becomes that color's wool.
     */
    @SubscribeEvent
    public static void onItemSpawn(EntityJoinWorldEvent event)
    {
        if (event.getWorld().isRemote || !(event.getEntity() instanceof ItemEntity))
        {
            return;
        }
        ItemEntity item = (ItemEntity) event.getEntity();
        ItemStack stack = item.getItem();
        if (!isVanillaWool(stack))
        {
            return;
        }
        double x = item.getPosX(), y = item.getPosY() - 1.0D, z = item.getPosZ();
        List<SheepEntity> sheep = event.getWorld().getEntitiesWithinAABB(SheepEntity.class,
                new AxisAlignedBB(x - 0.01D, y - 0.01D, z - 0.01D, x + 0.01D, y + 0.01D, z + 0.01D),
                s -> s.getSheared() && getColor(s) >= 0);
        if (!sheep.isEmpty())
        {
            item.setItem(new ItemStack(MDBlock.woolArray[getColor(sheep.get(0))], stack.getCount()));
        }
    }

    /** A dyed sheep that is killed drops its dyed wool. */
    @SubscribeEvent
    public static void onDrops(LivingDropsEvent event)
    {
        if (!(event.getEntityLiving() instanceof SheepEntity))
        {
            return;
        }
        int color = getColor((SheepEntity) event.getEntityLiving());
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
        if (!(event.getEntity() instanceof SheepEntity) || event.getResult() == Event.Result.DENY
                || (event.getSpawnReason() != SpawnReason.NATURAL && event.getSpawnReason() != SpawnReason.CHUNK_GENERATION))
        {
            return;
        }
        SheepEntity sheep = (SheepEntity) event.getEntity();
        if (sheep.getRNG().nextDouble() < ConfigHandler.sheepSpawnChance.get())
        {
            setColor(sheep, sheep.getRNG().nextInt(ColorStrings.ALL.length));
        }
    }

    /** A lamb takes the color of one of its parents, picked at random. */
    @SubscribeEvent
    public static void onBreed(BabyEntitySpawnEvent event)
    {
        if (!(event.getParentA() instanceof SheepEntity) || !(event.getParentB() instanceof SheepEntity)
                || !(event.getChild() instanceof SheepEntity))
        {
            return;
        }
        SheepEntity a = (SheepEntity) event.getParentA();
        SheepEntity b = (SheepEntity) event.getParentB();
        SheepEntity child = (SheepEntity) event.getChild();
        if (getColor(a) < 0 && getColor(b) < 0)
        {
            return;
        }
        SheepEntity parent = child.getRNG().nextBoolean() ? a : b;
        if (getColor(parent) >= 0)
        {
            setColor(child, getColor(parent));
        }
        else
        {
            child.setFleeceColor(parent.getFleeceColor());
        }
    }

    /** A player who starts seeing a dyed sheep is told its color. */
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event)
    {
        if (event.getTarget() instanceof SheepEntity && event.getPlayer() instanceof ServerPlayerEntity)
        {
            int color = getColor((SheepEntity) event.getTarget());
            if (color >= 0)
            {
                ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()),
                        new SheepColorPacket(event.getTarget().getEntityId(), color));
            }
        }
    }

    private static boolean isVanillaWool(ItemStack stack)
    {
        return stack.getItem().isIn(ItemTags.WOOL) && "minecraft".equals(stack.getItem().getRegistryName().getNamespace());
    }
}
