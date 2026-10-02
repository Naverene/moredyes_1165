package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.world.level.Level;
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
public class ChestRenderer implements BlockEntityRenderer<MDChestTileEntity>
{
    private static final Material SINGLE = material("normal");
    private static final Material LEFT = material("normal_left");
    private static final Material RIGHT = material("normal_right");

    // The vanilla chest models. Kept static so the item renderer can draw with them too; they are baked again
    // whenever resources reload, which also makes a new renderer.
    private static ModelPart SINGLE_BOTTOM, SINGLE_LID, SINGLE_LATCH;
    private static ModelPart RIGHT_BOTTOM, RIGHT_LID, RIGHT_LATCH;
    private static ModelPart LEFT_BOTTOM, LEFT_LID, LEFT_LATCH;

    public ChestRenderer(BlockEntityRendererProvider.Context context)
    {
        bake(context.getModelSet());
    }

    private static void bake(EntityModelSet models)
    {
        ModelPart single = models.bakeLayer(ModelLayers.CHEST);
        SINGLE_BOTTOM = single.getChild("bottom");
        SINGLE_LID = single.getChild("lid");
        SINGLE_LATCH = single.getChild("lock");
        ModelPart left = models.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT);
        LEFT_BOTTOM = left.getChild("bottom");
        LEFT_LID = left.getChild("lid");
        LEFT_LATCH = left.getChild("lock");
        ModelPart right = models.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT);
        RIGHT_BOTTOM = right.getChild("bottom");
        RIGHT_LID = right.getChild("lid");
        RIGHT_LATCH = right.getChild("lock");
    }

    @Override
    public void render(MDChestTileEntity chest, float partialTicks, PoseStack matrix, MultiBufferSource buffer, int light, int overlay)
    {
        Level world = chest.getLevel();
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
        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> merged = block.combine(state, world, chest.getBlockPos(), true);
        float lid = merged.apply(ChestBlock.opennessCombiner(chest)).get(partialTicks);
        int mergedLight = merged.apply(new BrightnessCombiner<>()).applyAsInt(light);
        render(state.getBlock(), state.getValue(ChestBlock.TYPE), state.getValue(ChestBlock.FACING), lid, matrix, buffer, mergedLight, overlay);
    }

    /** Draws a closed single chest facing south, for the item. */
    public static void renderItem(Block block, PoseStack matrix, MultiBufferSource buffer, int light, int overlay)
    {
        if (SINGLE_BOTTOM == null)
        {
            bake(Minecraft.getInstance().getEntityModels());
        }
        render(block, ChestType.SINGLE, Direction.SOUTH, 0.0F, matrix, buffer, light, overlay);
    }

    private static void render(Block block, ChestType type, Direction facing, float lid, PoseStack matrix, MultiBufferSource buffer, int light, int overlay)
    {
        int color = ColorHandlers.colorOf(block.getRegistryName());
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;

        matrix.pushPose();
        matrix.translate(0.5D, 0.5D, 0.5D);
        matrix.mulPose(Vector3f.YP.rotationDegrees(-facing.toYRot()));
        matrix.translate(-0.5D, -0.5D, -0.5D);

        lid = 1.0F - lid;
        lid = 1.0F - lid * lid * lid;
        Material material = type == ChestType.LEFT ? LEFT : type == ChestType.RIGHT ? RIGHT : SINGLE;
        VertexConsumer builder = material.buffer(buffer, RenderType::entityCutout);
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
        matrix.popPose();
    }

    private static void renderParts(PoseStack matrix, VertexConsumer builder, ModelPart lid, ModelPart latch, ModelPart bottom,
                                    float lidAngle, int light, int overlay, float r, float g, float b)
    {
        lid.xRot = -(lidAngle * ((float) Math.PI / 2F));
        latch.xRot = lid.xRot;
        lid.render(matrix, builder, light, overlay, r, g, b, 1.0F);
        latch.render(matrix, builder, light, overlay);
        bottom.render(matrix, builder, light, overlay, r, g, b, 1.0F);
    }

    private static Material material(String name)
    {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Reference.MOD_ID, "entity/chest/" + name));
    }

    /** The grey chest textures are not on the chest atlas unless we add them. */
    @SubscribeEvent
    public static void stitch(TextureStitchEvent.Pre event)
    {
        if (event.getAtlas().location().equals(Sheets.CHEST_SHEET))
        {
            event.addSprite(SINGLE.texture());
            event.addSprite(LEFT.texture());
            event.addSprite(RIGHT.texture());
        }
    }
}
