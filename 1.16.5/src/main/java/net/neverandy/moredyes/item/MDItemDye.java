package net.neverandy.moredyes.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.neverandy.moredyes.MoreDyes;
import net.minecraft.entity.passive.SheepEntity;
import net.neverandy.moredyes.entity.DyedSheep;
import net.neverandy.moredyes.reference.ColorStrings;

public class MDItemDye extends Item
{
    String color;
    public MDItemDye(String name)
    {
        super(new Properties().group(MoreDyes.tabDyes));
        this.color=name;
        //this.setRegistryName(set+"_dye");
        //initModel();
    }
    public ActionResult onItemUse(ItemStack stack, PlayerEntity playerIn, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
    {
        return null;
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand)
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
        if (target instanceof SheepEntity)
        {
            return DyedSheep.dye((SheepEntity) target, colorIndex(), stack);
        }
        return ActionResultType.PASS;
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