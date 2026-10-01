package net.neverandy.moredyes.data.condition;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.neverandy.moredyes.ConfigHandler;
import net.neverandy.moredyes.reference.Reference;

/** True when dyed walls are registered (the wall_blocks config), so wall recipes are skipped without them. */
public final class WallsEnabledCondition implements ICondition
{
    public static final WallsEnabledCondition INSTANCE = new WallsEnabledCondition();
    private static final ResourceLocation ID = new ResourceLocation(Reference.MOD_ID, "walls_enabled");

    private WallsEnabledCondition() {}

    @Override
    public ResourceLocation getID()
    {
        return ID;
    }

    @Override
    public boolean test()
    {
        return ConfigHandler.wallBlocks.get();
    }

    public static final IConditionSerializer<WallsEnabledCondition> SERIALIZER = new IConditionSerializer<WallsEnabledCondition>()
    {
        @Override
        public void write(JsonObject json, WallsEnabledCondition value)
        {
        }

        @Override
        public WallsEnabledCondition read(JsonObject json)
        {
            return INSTANCE;
        }

        @Override
        public ResourceLocation getID()
        {
            return ID;
        }
    };
}
