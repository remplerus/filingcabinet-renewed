package com.rempler.fcrenewed;

import com.mojang.logging.LogUtils;
import com.rempler.fcrenewed.common.init.FCInits;
import com.rempler.fcrenewed.common.init.FCItems;
import com.rempler.fcrenewed.util.Config;
import com.rempler.fcrenewed.util.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(Constants.MODID)
public class FCRenewed {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MODID);
    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
            .icon(() -> FCItems.FOLDER.get().getDefaultInstance())
            .title(Component.translatable(Constants.MODID+".tab"))
            .displayItems(((pParameters, pOutput) -> {
                for (RegistryObject<Item> item : FCItems.ITEMS.getEntries()) {
                    pOutput.accept(item.get().getDefaultInstance());
                }
            })).build());

    public FCRenewed() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CREATIVE_MODE_TABS.register(modEventBus);
        FCInits.init(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
