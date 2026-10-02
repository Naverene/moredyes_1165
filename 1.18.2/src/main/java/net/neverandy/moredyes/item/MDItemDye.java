package net.neverandy.moredyes.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.neverandy.moredyes.MoreDyes;
import net.minecraft.world.entity.animal.Sheep;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.reference.ColorStrings;

import net.minecraft.world.item.Item.Properties;

public class MDItemDye extends Item
{
    String color;
    public MDItemDye(String name)
    {
        super(new Properties().tab(MoreDyes.tabDyes));
        this.color=name;
        //this.setRegistryName(set+"_dye");
        //initModel();
    }
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target, InteractionHand hand)
    {
	   /*
       if (target instanceof EntitySheep)
       {
           EntitySheep entitysheep = (EntitySheep)target;
           EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(stack.getMetadata());

           if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != enumdyecolor)
           {
               entitysheep.setFleeceColor(enumdyecolor);
               --stack.stackSize;
           }

           return true;
       }
       else
       {
           return false;
       }*/
        if (target instanceof Sheep)
        {
            return DyedSheep.dye((Sheep) target, colorIndex(), stack);
        }
        return InteractionResult.PASS;
    }

    /** This dye's index in ColorStrings.ALL. */
    public int colorIndex()
    {
        String hex = color.substring(0, color.indexOf('_'));
        for (int i = 0; i < ColorStrings.ALL.length; i++)
        {
            if (ColorStrings.ALL[i].equals(hex))
            {
                return i;
            }
        }
        return -1;
    }
}