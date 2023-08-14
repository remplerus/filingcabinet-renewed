package com.rempler.fcrenewed.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class FCBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BEs = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "fcrenewed");

    public static void init(IEventBus modEventBus) {
        BEs.register(modEventBus);
    }
}
