package com.rempler.fcrenewed.common.init;

import com.rempler.fcrenewed.common.blockentites.CabinetBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FCBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BEs = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "fcrenewed");

    public static final RegistryObject<BlockEntityType<CabinetBlockEntity>> CABINET = BEs.register("cabinet", () -> BlockEntityType.Builder.of(CabinetBlockEntity::new, FCBlocks.CABINET.get()).build(null));
    public static void init(IEventBus modEventBus) {
        BEs.register(modEventBus);
    }
}
