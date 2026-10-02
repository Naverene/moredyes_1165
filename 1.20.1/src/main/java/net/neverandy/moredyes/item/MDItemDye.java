package net.neverandy.moredyes.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neverandy.moredyes.entity.DyedSheep;

/** A MoreDyes dye. It dyes sheep (see entity/DyedSheep) and is a crafting ingredient for the dyed blocks. */
public class MDItemDye extends Item
{
    private final int color;

    public MDItemDye(int color, Properties properties)
    {
        super(properties);
        this.color = color;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand)
    {
        if (target instanceof Sheep sheep)
        {
            return DyedSheep.dye(sheep, color, stack);
        }
        return InteractionResult.PASS;
    }

    /** This dye's index in ColorStrings.ALL. */
    public int colorIndex()
    {
        return color;
    }
}
