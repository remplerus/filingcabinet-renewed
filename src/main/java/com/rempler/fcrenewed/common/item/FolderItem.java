package com.rempler.fcrenewed.common.item;

import com.rempler.fcrenewed.common.block.CabinetBlock;
import com.rempler.fcrenewed.common.blockentites.CabinetBlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FolderItem extends Item {
    public FolderItem() {
        super(new Item.Properties().stacksTo(64).rarity(Rarity.COMMON));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) {
            return InteractionResult.PASS;
        }
        BlockEntity be = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
        if (be instanceof CabinetBlockEntity cabinet) {
            cabinet.addFolder(pContext.getItemInHand());
            pContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }
}
