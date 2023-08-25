package com.rempler.fcrenewed.data;

import com.rempler.fcrenewed.FCRenewed;
import com.rempler.fcrenewed.common.init.FCBlocks;
import com.rempler.fcrenewed.common.init.FCItems;
import com.rempler.fcrenewed.util.Constants;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.RegistryObject;

public class FCLangProvider extends LanguageProvider {
    public FCLangProvider(PackOutput output, String locale) {
        super(output, Constants.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        for (RegistryObject<Item> item : FCItems.ITEMS.getEntries()) {
            add(item.get(), item.get().getDescriptionId());
        }
        for (RegistryObject<CreativeModeTab> tab : FCRenewed.CREATIVE_MODE_TABS.getEntries()) {
            add(tab.get().getDisplayName().getString(), tab.get().getDisplayName().getString());
        }


    }
}
