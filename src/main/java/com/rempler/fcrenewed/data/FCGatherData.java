package com.rempler.fcrenewed.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.CompletableFuture;

public class FCGatherData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        generator.addProvider(event.includeServer(), new FCBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new FCItemTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new FCRecipeProvider(packOutput));
        //TODO: Loot tables
        //generator.addProvider(event.includeServer(), new FCBlockLootTableProvider(packOutput, null, null));
        generator.addProvider(event.includeClient(), new FCItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new FCBlockStateProvider(packOutput, existingFileHelper));
    }
}
