package com.rempler.fcrenewed.data;

import com.rempler.fcrenewed.util.Constants;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class FCLangProvider extends LanguageProvider {
    public FCLangProvider(PackOutput output, String locale) {
        super(output, Constants.MODID, locale);
    }

    @Override
    protected void addTranslations() {

    }
}
