package info.kg6jay.moredyes.client;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;

import org.lwjgl.opengl.GL11;

import info.kg6jay.moredyes.block.MDBlock;
import info.kg6jay.moredyes.entity.SheepColor;
import info.kg6jay.moredyes.utility.ColorUtil;

/** The vanilla sheep renderer, but it draws the fleece of a sheep dyed with More Dyes in that dye's shade. */
public class RenderColoredSheep extends RenderSheep {

    public RenderColoredSheep() {
        super(new ModelSheep2(), new ModelSheep1(), 0.7F);
    }

    @Override
    protected int shouldRenderPass(EntitySheep sheep, int pass, float partialTicks) {
        int result = super.shouldRenderPass(sheep, pass, partialTicks);
        SheepColor color = SheepColor.of(sheep);
        // jeb_ sheep keep cycling through the vanilla colors.
        if (result == 1 && color != null && color.hasShade() && !"jeb_".equals(sheep.getCustomNameTag())) {
            int set = SheepColor.setOf(color.get());
            int rgb = ColorUtil.shade(MDBlock.colorStrings[set], SheepColor.shadeOf(color.get()));
            GL11.glColor3f((rgb >> 16 & 255) / 255.0F, (rgb >> 8 & 255) / 255.0F, (rgb & 255) / 255.0F);
        }
        return result;
    }
}
