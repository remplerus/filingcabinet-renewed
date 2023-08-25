package com.rempler.fcrenewed.data;

import com.rempler.fcrenewed.util.Constants;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FCItemModelProvider extends ItemModelProvider {

    public FCItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }
}
