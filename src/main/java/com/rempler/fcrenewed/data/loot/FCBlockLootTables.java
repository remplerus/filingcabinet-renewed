package com.rempler.fcrenewed.data.loot;

import com.rempler.fcrenewed.common.init.FCBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class FCBlockLootTables extends BlockLootSubProvider {

    public FCBlockLootTables() {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS);
    }

    @Override
    protected void generate() {
        for (RegistryObject<Block> block : FCBlocks.BLOCKS.getEntries()) {
            this.dropSelf(block.get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FCBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
