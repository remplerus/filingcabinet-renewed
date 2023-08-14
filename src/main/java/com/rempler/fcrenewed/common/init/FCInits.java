package com.rempler.fcrenewed.common.init;

import net.minecraftforge.eventbus.api.IEventBus;

public class FCInits {
    public static void init(IEventBus modEventBus) {
        FCBlocks.init(modEventBus);
        FCItems.init(modEventBus);
        FCBlockEntities.init(modEventBus);
    }
}
