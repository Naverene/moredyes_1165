package net.neverandy.moredyes.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neverandy.moredyes.data.client.ModBlockStateProvider;
import net.neverandy.moredyes.data.client.ModItemModelProvider;
import net.neverandy.moredyes.data.client.ModLangProvider;
import net.neverandy.moredyes.data.server.ModBlockTagsProvider;
import net.neverandy.moredyes.data.server.ModChiselProvider;
import net.neverandy.moredyes.data.server.ModItemTagsProvider;
import net.neverandy.moredyes.data.server.ModLootTableProvider;
import net.neverandy.moredyes.data.server.ModRecipeProvider;
import net.neverandy.moredyes.data.server.ModWorldGenProvider;
import net.neverandy.moredyes.reference.Reference;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGen
{
    private DataGen() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        PackOutput output = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        gen.addProvider(event.includeClient(), new ModBlockStateProvider(output, existingFileHelper));
        gen.addProvider(event.includeClient(), new ModItemModelProvider(output, existingFileHelper));
        gen.addProvider(event.includeClient(), new ModLangProvider(output, Reference.MOD_ID, "en_us"));
        ModBlockTagsProvider blockTags = gen.addProvider(event.includeServer(), new ModBlockTagsProvider(output, lookup, existingFileHelper));
        gen.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookup, blockTags.contentsGetter(), existingFileHelper));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(output));
        gen.addProvider(event.includeServer(), ModLootTableProvider.create(output));
        gen.addProvider(event.includeServer(), new ModChiselProvider(output));
        gen.addProvider(event.includeServer(), new ModWorldGenProvider(output));
    }
}
