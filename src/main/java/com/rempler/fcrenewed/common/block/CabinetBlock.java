package com.rempler.fcrenewed.common.block;

import com.rempler.fcrenewed.FCRenewed;
import com.rempler.fcrenewed.common.blockentites.CabinetBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CabinetBlock extends BaseEntityBlock {

    public CabinetBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(3));
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CabinetBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide) {
            return (level1, blockPos, blockState, t) -> {
                if (t instanceof CabinetBlockEntity be) {
                    be.tickServer();
                }
            };
        }
        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide)
            return InteractionResult.PASS;
        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (be instanceof CabinetBlockEntity cabinet && pPlayer.isShiftKeyDown()) {
            FCRenewed.LOGGER.info("Removing folder");
            ItemStack stack = cabinet.removeFolder();
            if (!stack.isEmpty()) {
                pPlayer.addItem(stack);
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CabinetBlockEntity bdbe) {
                bdbe.drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
