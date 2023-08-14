package com.rempler.fcrenewed.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class FCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, "fcrenewed");

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
