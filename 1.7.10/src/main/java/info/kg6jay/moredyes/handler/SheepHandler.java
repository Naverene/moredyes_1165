package info.kg6jay.moredyes.handler;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.entity.SheepColor;
import info.kg6jay.moredyes.network.PacketHandler;

/**
 * Lets vanilla sheep wear More Dyes shades: keeps the shade on each sheep, sends it to the players who can see the
 * sheep, drops the matching More Dyes wool when the sheep is sheared or killed, and gives some new sheep a random
 * shade. More Dyes dyes apply the shade themselves (see MDItemDye); a vanilla dye takes it off again.
 */
public class SheepHandler {

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntitySheep) {
            SheepColor.attach((EntitySheep) event.entity);
        }
    }

    /** Gives a sheep that is new to the world its chance to spawn in a random More Dyes shade. */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        SheepColor color = SheepColor.of(event.entity);
        if (event.world.isRemote || color == null || color.settled) {
            return;
        }
        color.settled = true;
        Random rand = event.world.rand;
        if (color.hasShade() || rand.nextDouble() >= ConfigHandler.sheepSpawnChance) {
            return;
        }
        int total = 0;
        for (String[] shades : MDBlock.colorStrings) {
            total += shades.length;
        }
        int pick = rand.nextInt(total);
        for (int set = 0; set < MDBlock.colorStrings.length; set++) {
            if (pick < MDBlock.colorStrings[set].length) {
                applyShade((EntitySheep) event.entity, color, set, pick);
                return;
            }
            pick -= MDBlock.colorStrings[set].length;
        }
    }

    @SubscribeEvent
    public void onStartTracking(PlayerEvent.StartTracking event) {
        SheepColor color = SheepColor.of(event.target);
        if (color != null && color.hasShade() && event.entityPlayer instanceof EntityPlayerMP) {
            PacketHandler.sendSheepColor(event.target, color, (EntityPlayerMP) event.entityPlayer);
        }
    }

    @SubscribeEvent
    public void onEntityInteract(EntityInteractEvent event) {
        SheepColor color = SheepColor.of(event.target);
        EntityPlayer player = event.entityPlayer;
        ItemStack held = player.getCurrentEquippedItem();
        // Server only: the server tells the player's game about the new fleece and inventory.
        if (player.worldObj.isRemote || color == null || !color.hasShade() || held == null) {
            return;
        }
        EntitySheep sheep = (EntitySheep) event.target;
        if (held.getItem() == Items.dye) {
            // A vanilla dye replaces the More Dyes shade, even when the sheep's vanilla color is the same.
            if (!sheep.getSheared()) {
                event.setCanceled(true);
                clearShade(sheep, color);
                sheep.setFleeceColor(~held.getItemDamage() & 15);
                useHeldItem(player, held, false);
            }
        } else if (held.getItem() instanceof ItemShears) {
            // Vanilla shearing would drop vanilla wool, so shear the sheep here instead.
            if (!sheep.getSheared() && !sheep.isChild()) {
                event.setCanceled(true);
                shear(sheep, color);
                useHeldItem(player, held, true);
            }
        }
    }

    /** Swaps the vanilla wool a dyed sheep drops when killed for More Dyes wool. */
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        SheepColor color = SheepColor.of(event.entityLiving);
        if (color == null || !color.hasShade()) {
            return;
        }
        Item vanillaWool = Item.getItemFromBlock(Blocks.wool);
        for (EntityItem drop : event.drops) {
            ItemStack stack = drop.getEntityItem();
            if (stack != null && stack.getItem() == vanillaWool) {
                drop.setEntityItemStack(woolFor(color, stack.stackSize));
            }
        }
    }

    public static void applyShade(EntitySheep sheep, SheepColor color, int set, int shade) {
        color.put(SheepColor.pack(set, shade));
        // The color sets follow the vanilla wool colors, so the vanilla color is the closest match.
        sheep.setFleeceColor(set);
        PacketHandler.sendSheepColor(sheep, color);
    }

    public static void clearShade(EntitySheep sheep, SheepColor color) {
        color.put(SheepColor.NONE);
        PacketHandler.sendSheepColor(sheep, color);
    }

    private static ItemStack woolFor(SheepColor color, int count) {
        int set = SheepColor.setOf(color.get());
        return new ItemStack(MDBlock.wool[set], count, SheepColor.shadeOf(color.get()));
    }

    /** Shears a sheep like vanilla shears do, dropping 1 to 3 More Dyes wool. */
    private static void shear(EntitySheep sheep, SheepColor color) {
        Random rand = sheep.worldObj.rand;
        sheep.setSheared(true);
        int count = 1 + rand.nextInt(3);
        for (int i = 0; i < count; i++) {
            EntityItem item = sheep.entityDropItem(woolFor(color, 1), 1.0F);
            item.motionY += rand.nextFloat() * 0.05F;
            item.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
            item.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
        }
        sheep.playSound("mob.sheep.shear", 1.0F, 1.0F);
    }

    /** Takes one dye from the player's hand, or wears down the shears in it. Creative players keep both. */
    private static void useHeldItem(EntityPlayer player, ItemStack held, boolean shears) {
        if (shears) {
            held.damageItem(1, player);
        } else if (!player.capabilities.isCreativeMode) {
            held.stackSize--;
        }
        if (held.stackSize <= 0) {
            player.destroyCurrentEquippedItem();
        }
        player.inventoryContainer.detectAndSendChanges();
    }
}
