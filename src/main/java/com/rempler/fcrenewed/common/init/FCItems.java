package com.rempler.fcrenewed.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class FCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "fcrenewed");

    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
