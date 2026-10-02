package net.neverandy.moredyes.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.neverandy.moredyes.color.MixColor;
import net.neverandy.moredyes.entity.DyedSheep;

/** One of the mixed dyes. Like a vanilla dye, it can be used on a sheep to dye its wool. */
public class MixDyeItem extends Item {

    private final MixColor color;

    public MixDyeItem(MixColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    public MixColor color() {
        return color;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof Sheep sheep && sheep.isAlive() && !sheep.isSheared()
            && !DyedSheep.getColor(sheep).filter(color::equals).isPresent()) {
            sheep.level().playSound(player, sheep, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            if (!player.level().isClientSide()) {
                DyedSheep.setColor(sheep, color);
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
