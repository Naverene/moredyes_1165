package net.neverandy.moredyes.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraftforge.registries.ForgeRegistries;
import net.neverandy.moredyes.reference.Reference;
import net.neverandy.moredyes.tileentity.MDChestTileEntity;

/**
 * Draws dyed chests. This is the vanilla chest renderer, except the textures are the grey copies in
 * textures/entity/chest and the lid and base are multiplied by the dye color. The latch is drawn untinted.
 * The vanilla chest atlas picks up every texture in textures/entity/chest, so the grey ones need no registering.
 */
public class ChestRenderer implements BlockEntityRenderer<MDChestTileEntity>
{
    private static final Material SINGLE = material("normal");
    private static final Material LEFT = material("normal_left");
    private static final Material RIGHT = material("normal_right");

    private final Parts single;
    private final Parts left;
    private final Parts right;

    public ChestRenderer(BlockEntityRendererProvider.Context context)
    {
        this(context.getModelSet());
    }

    public ChestRenderer(EntityModelSet models)
    {
        single = new Parts(models.bakeLayer(ModelLayers.CHEST));
        left = new Parts(models.bakeLayer(ModelLayers.DOUBLE_CHEST_LEFT));
        right = new Parts(models.bakeLayer(ModelLayers.DOUBLE_CHEST_RIGHT));
    }

    @Override
    public void render(MDChestTileEntity chest, float partialTicks, PoseStack pose, MultiBufferSource buffer, int light, int overlay)
    {
        Level level = chest.getLevel();
        BlockState state = chest.getBlockState();
        if (level == null || !(state.getBlock() instanceof ChestBlock block))
        {
            return;
        }
        DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> merged = block.combine(state, level, chest.getBlockPos(), true);
        float lid = ((Float2FloatFunction) merged.apply(ChestBlock.opennessCombiner(chest))).get(partialTicks);
        int mergedLight = ((Int2IntFunction) merged.apply(new BrightnessCombiner<>())).applyAsInt(light);
        render(block, state.getValue(ChestBlock.TYPE), state.getValue(ChestBlock.FACING), lid, pose, buffer, mergedLight, overlay);
    }

    /** Draws a closed single chest facing south, for the item. */
    public void renderItem(Block block, PoseStack pose, MultiBufferSource buffer, int light, int overlay)
    {
        render(block, ChestType.SINGLE, Direction.SOUTH, 0.0F, pose, buffer, light, overlay);
    }

    private void render(Block block, ChestType type, Direction facing, float lid, PoseStack pose, MultiBufferSource buffer, int light, int overlay)
    {
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);
        int color = name == null ? 0xFFFFFF : ColorHandlers.colorOf(name);
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;

        pose.pushPose();
        pose.translate(0.5F, 0.5F, 0.5F);
        pose.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        pose.translate(-0.5F, -0.5F, -0.5F);

        lid = 1.0F - lid;
        lid = 1.0F - lid * lid * lid;
        Material material = type == ChestType.LEFT ? LEFT : type == ChestType.RIGHT ? RIGHT : SINGLE;
        VertexConsumer consumer = material.buffer(buffer, RenderType::entityCutout);
        Parts parts = type == ChestType.LEFT ? left : type == ChestType.RIGHT ? right : single;
        parts.lid.xRot = -(lid * ((float) Math.PI / 2F));
        parts.lock.xRot = parts.lid.xRot;
        parts.lid.render(pose, consumer, light, overlay, r, g, b, 1.0F);
        parts.lock.render(pose, consumer, light, overlay);
        parts.bottom.render(pose, consumer, light, overlay, r, g, b, 1.0F);
        pose.popPose();
    }

    private static Material material(String name)
    {
        return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Reference.MOD_ID, "entity/chest/" + name));
    }

    /** The boxes of one chest shape, from the vanilla chest models. */
    private static final class Parts
    {
        final ModelPart lid;
        final ModelPart lock;
        final ModelPart bottom;

        Parts(ModelPart root)
        {
            lid = root.getChild("lid");
            lock = root.getChild("lock");
            bottom = root.getChild("bottom");
        }
    }
}
