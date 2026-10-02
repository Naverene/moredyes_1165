package net.neverandy.moredyes.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.neverandy.moredyes.data.client.ModBlockStateProvider;
import net.neverandy.moredyes.data.client.ModItemModelProvider;
import net.neverandy.moredyes.data.client.ModLangProvider;
import net.neverandy.moredyes.data.server.ModBlockTagsProvider;
import net.neverandy.moredyes.data.server.ModChiselProvider;
import net.neverandy.moredyes.data.server.ModItemTagsProvider;
import net.neverandy.moredyes.data.server.ModLootTableProvider;
import net.neverandy.moredyes.data.server.ModRecipeProvider;
import net.neverandy.moredyes.reference.Reference;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGen
{
    private DataGen(){}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider(new ModLangProvider(gen, Reference.MOD_ID, "en_us"));
        gen.addProvider(new ModLootTableProvider(gen));
        gen.addProvider(new ModChiselProvider(gen));
    }
}