package com.rempler.fcrenewed.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Set;

public class FCBlockLootTableProvider extends LootTableProvider {
    public FCBlockLootTableProvider(PackOutput pOutput, Set<ResourceLocation> pRequiredTables, List<SubProviderEntry> pSubProviders) {
        super(pOutput, pRequiredTables, pSubProviders);
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return null;
    }
}
