package com.rempler.fcrenewed.data;

import com.rempler.fcrenewed.util.Constants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FCGatherData {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        BlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(), new FCBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new FCItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(event.includeServer(), new FCRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), FCBlockLootTableProvider.create(packOutput));
        generator.addProvider(event.includeClient(), new FCItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new FCBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new FCLangProvider(packOutput, "en_us"));
    }
}
