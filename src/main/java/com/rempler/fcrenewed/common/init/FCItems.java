package com.rempler.fcrenewed.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FCItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, "fcrenewed");

    public static final RegistryObject<Item> FOLDER = ITEMS.register("folder", () -> new Item(new Item.Properties()));
    public static final RegistryObject<BlockItem> CABINET = ITEMS.register("cabinet", () -> new BlockItem(FCBlocks.CABINET.get(), new Item.Properties()));
    public static void init(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
