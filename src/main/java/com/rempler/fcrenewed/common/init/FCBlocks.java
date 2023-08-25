package com.rempler.fcrenewed.common.init;

import com.rempler.fcrenewed.common.block.CabinetBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FCBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, "fcrenewed");

    public static final RegistryObject<CabinetBlock> CABINET = BLOCKS.register("cabinet", CabinetBlock::new);
    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}
