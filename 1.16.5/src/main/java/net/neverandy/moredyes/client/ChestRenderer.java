package net.neverandy.moredyes.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.MDChestTileEntity;

/**
 * Draws dyed chests. This is the vanilla chest renderer, except the textures are the grey copies in
 * textures/entity/chest and the lid and base are multiplied by the dye color. The latch is drawn untinted.
 */
@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ChestRenderer extends TileEntityRenderer<MDChestTileEntity>
{
    private static final RenderMaterial SINGLE = material("normal");
    private static final RenderMaterial LEFT = material("normal_left");
    private static final RenderMaterial RIGHT = material("normal_right");

    // Same boxes as the vanilla ChestTileEntityRenderer.
    private static final ModelRenderer SINGLE_BOTTOM = box(0, 19, 1, 0, 1, 14, 10, 14, 0);
    private static final ModelRenderer SINGLE_LID = box(0, 0, 1, 0, 0, 14, 5, 14, 9);
    private static final ModelRenderer SINGLE_LATCH = box(0, 0, 7, -1, 15, 2, 4, 1, 8);
    private static final ModelRenderer RIGHT_BOTTOM = box(0, 19, 1, 0, 1, 15, 10, 14, 0);
    private static final ModelRenderer RIGHT_LID = box(0, 0, 1, 0, 0, 15, 5, 14, 9);
    private static final ModelRenderer RIGHT_LATCH = box(0, 0, 15, -1, 15, 1, 4, 1, 8);
    private static final ModelRenderer LEFT_BOTTOM = box(0, 19, 0, 0, 1, 15, 10, 14, 0);
    private static final ModelRenderer LEFT_LID = box(0, 0, 0, 0, 0, 15, 5, 14, 9);
    private static final ModelRenderer LEFT_LATCH = box(0, 0, 0, -1, 15, 1, 4, 1, 8);

    static
    {
        SINGLE_LID.rotationPointZ = 1.0F;
        RIGHT_LID.rotationPointZ = 1.0F;
        LEFT_LID.rotationPointZ = 1.0F;
    }

    public ChestRenderer(TileEntityRendererDispatcher dispatcher)
    {
        super(dispatcher);
    }

    @Override
    public void render(MDChestTileEntity chest, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay)
    {
        World world = chest.getWorld();
        if (world == null)
        {
            return;
        }
        BlockState state = chest.getBlockState();
        if (!(state.getBlock() instanceof ChestBlock))
        {
            return;
        }
        ChestBlock block = (ChestBlock) state.getBlock();
        TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> merged = block.combine(state, world, chest.getPos(), true);
        float lid = merged.apply(ChestBlock.getLidRotationCallback(chest)).get(partialTicks);
        int mergedLight = merged.apply(new DualBrightnessCallback<>()).applyAsInt(light);
        render(state.getBlock(), state.get(ChestBlock.TYPE), state.get(ChestBlock.FACING), lid, matrix, buffer, mergedLight, overlay);
    }

    /** Draws a closed single chest facing south, for the item. */
    public static void renderItem(Block block, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay)
    {
        render(block, ChestType.SINGLE, Direction.SOUTH, 0.0F, matrix, buffer, light, overlay);
    }

    private static void render(Block block, ChestType type, Direction facing, float lid, MatrixStack matrix, IRenderTypeBuffer buffer, int light, int overlay)
    {
        int color = ColorHandlers.colorOf(block.getRegistryName());
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;

        matrix.push();
        matrix.translate(0.5D, 0.5D, 0.5D);
        matrix.rotate(Vector3f.YP.rotationDegrees(-facing.getHorizontalAngle()));
        matrix.translate(-0.5D, -0.5D, -0.5D);

        lid = 1.0F - lid;
        lid = 1.0F - lid * lid * lid;
        RenderMaterial material = type == ChestType.LEFT ? LEFT : type == ChestType.RIGHT ? RIGHT : SINGLE;
        IVertexBuilder builder = material.getBuffer(buffer, RenderType::getEntityCutout);
        if (type == ChestType.LEFT)
        {
            renderParts(matrix, builder, LEFT_LID, LEFT_LATCH, LEFT_BOTTOM, lid, light, overlay, r, g, b);
        }
        else if (type == ChestType.RIGHT)
        {
            renderParts(matrix, builder, RIGHT_LID, RIGHT_LATCH, RIGHT_BOTTOM, lid, light, overlay, r, g, b);
        }
        else
        {
            renderParts(matrix, builder, SINGLE_LID, SINGLE_LATCH, SINGLE_BOTTOM, lid, light, overlay, r, g, b);
        }
        matrix.pop();
    }

    private static void renderParts(MatrixStack matrix, IVertexBuilder builder, ModelRenderer lid, ModelRenderer latch, ModelRenderer bottom,
                                    float lidAngle, int light, int overlay, float r, float g, float b)
    {
        lid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));
        latch.rotateAngleX = lid.rotateAngleX;
        lid.render(matrix, builder, light, overlay, r, g, b, 1.0F);
        latch.render(matrix, builder, light, overlay);
        bottom.render(matrix, builder, light, overlay, r, g, b, 1.0F);
    }

    private static ModelRenderer box(int u, int v, float x, float y, float z, float w, float h, float d, float pivotY)
    {
        ModelRenderer part = new ModelRenderer(64, 64, u, v);
        part.addBox(x, y, z, w, h, d, 0.0F);
        part.rotationPointY = pivotY;
        return part;
    }

    private static RenderMaterial material(String name)
    {
        return new RenderMaterial(Atlases.CHEST_ATLAS, new ResourceLocation(Reference.MOD_ID, "entity/chest/" + name));
    }

    /** The grey chest textures are not on the chest atlas unless we add them. */
    @SubscribeEvent
    public static void stitch(TextureStitchEvent.Pre event)
    {
        if (event.getMap().getTextureLocation().equals(Atlases.CHEST_ATLAS))
        {
            event.addSprite(SINGLE.getTextureLocation());
            event.addSprite(LEFT.getTextureLocation());
            event.addSprite(RIGHT.getTextureLocation());
        }
    }
}
