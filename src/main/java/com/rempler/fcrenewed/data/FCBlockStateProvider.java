package com.rempler.fcrenewed.data;

import com.rempler.fcrenewed.util.Constants;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FCBlockStateProvider extends BlockStateProvider {
    public FCBlockStateProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
