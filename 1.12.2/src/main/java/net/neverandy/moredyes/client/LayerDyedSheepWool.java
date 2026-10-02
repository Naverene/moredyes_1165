package net.neverandy.moredyes.client;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.neverandy.moredyes.handler.DyedSheepHandler;
import net.neverandy.moredyes.reference.ColorStrings;
import net.neverandy.moredyes.utility.ColorUtil;

/** The vanilla wool layer of the sheep, drawing the wool in the mod's color for the sheep that have one. */
@SideOnly(Side.CLIENT)
public class LayerDyedSheepWool extends LayerSheepWool
{
	private static final ResourceLocation TEXTURE=new ResourceLocation("textures/entity/sheep/sheep_fur.png");
	private final RenderSheep sheepRenderer;
	private final ModelSheep1 sheepModel=new ModelSheep1();

	public LayerDyedSheepWool(RenderSheep sheepRenderer)
	{
		super(sheepRenderer);
		this.sheepRenderer=sheepRenderer;
	}
	@Override
	public void doRenderLayer(EntitySheep sheep,float limbSwing,float limbSwingAmount,float partialTicks,float ageInTicks,float netHeadYaw,float headPitch,float scale)
	{
		int index=DyedSheepHandler.getColor(sheep);
		if(index<0)
		{
			super.doRenderLayer(sheep,limbSwing,limbSwingAmount,partialTicks,ageInTicks,netHeadYaw,headPitch,scale);
			return;
		}
		if(!sheep.getSheared()&&!sheep.isInvisible())
		{
			this.sheepRenderer.bindTexture(TEXTURE);
			int color=ColorUtil.fromHex(ColorStrings.ALL[index]);
			GlStateManager.color((color>>16&255)/255.0F,(color>>8&255)/255.0F,(color&255)/255.0F);
			this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
			this.sheepModel.setLivingAnimations(sheep,limbSwing,limbSwingAmount,partialTicks);
			this.sheepModel.render(sheep,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch,scale);
		}
	}
}
