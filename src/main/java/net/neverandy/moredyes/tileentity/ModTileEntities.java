package net.neverandy.moredyes.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.neverandy.moredyes.block.MDBlock;
import net.neverandy.moredyes.reference.Reference;

public final class ModTileEntities
{
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID);

    /** One tile entity type shared by every dyed chest. */
    public static final RegistryObject<TileEntityType<MDChestTileEntity>> CHEST = TILE_ENTITIES.register("chest",
            () -> TileEntityType.Builder.create(MDChestTileEntity::new, MDBlock.chestArray).build(null));

    private ModTileEntities() {}

    public static void register(IEventBus modBus)
    {
        TILE_ENTITIES.register(modBus);
    }
}
