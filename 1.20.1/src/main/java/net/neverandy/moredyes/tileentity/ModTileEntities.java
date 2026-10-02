package net.neverandy.moredyes.tileentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;

public final class ModTileEntities
{
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Reference.MOD_ID);

    /** One block entity type shared by every dyed chest. */
    @SuppressWarnings("DataFlowIssue")
    public static final RegistryObject<BlockEntityType<MDChestTileEntity>> CHEST = TILE_ENTITIES.register("chest",
            () -> BlockEntityType.Builder.of(MDChestTileEntity::new, MDBlock.chestArray).build(null));

    private ModTileEntities() {}

    public static void register(IEventBus modBus)
    {
        TILE_ENTITIES.register(modBus);
    }
}
