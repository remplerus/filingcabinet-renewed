package com.rempler.fcrenewed.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class FolderItem extends Item {
    public FolderItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }
}
