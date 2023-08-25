package com.rempler.fcrenewed.common.block;

import com.rempler.fcrenewed.common.blockentites.CabinetBlockEntity;
import com.rempler.fcrenewed.common.init.FCItems;
import com.rempler.fcrenewed.util.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class CabinetBlock extends BaseEntityBlock {
    private static final int MAX_FOLDERS_IN_STORAGE = 8;
    public static final int FOLDERS_PER_ROW = 4;
    public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES = List.of(Constants.BlockStateProperties.CABINET_SLOT_0_OCCUPIED,
            Constants.BlockStateProperties.CABINET_SLOT_1_OCCUPIED, Constants.BlockStateProperties.CABINET_SLOT_2_OCCUPIED,
            Constants.BlockStateProperties.CABINET_SLOT_3_OCCUPIED, Constants.BlockStateProperties.CABINET_SLOT_4_OCCUPIED,
            Constants.BlockStateProperties.CABINET_SLOT_5_OCCUPIED, Constants.BlockStateProperties.CABINET_SLOT_6_OCCUPIED,
            Constants.BlockStateProperties.CABINET_SLOT_7_OCCUPIED);

    public CabinetBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(3));
        BlockState blockstate = this.stateDefinition.any().setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH);
        for(BooleanProperty booleanproperty : SLOT_OCCUPIED_PROPERTIES) {
            blockstate = blockstate.setValue(booleanproperty, Boolean.FALSE);
        }

        this.registerDefaultState(blockstate);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CabinetBlockEntity(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof CabinetBlockEntity cabinetBlockEntity) {
            Optional<Vec2> optional = getRelativeHitCoordinatesForBlockFace(pHit, pState.getValue(HorizontalDirectionalBlock.FACING));
            if (optional.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                int i = getHitSlot(optional.get());
                if (pState.getValue(SLOT_OCCUPIED_PROPERTIES.get(i))) {
                    removeBook(pLevel, pPos, pPlayer, cabinetBlockEntity, i);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                } else {
                    ItemStack itemstack = pPlayer.getItemInHand(pHand);
                    if (itemstack.is(FCItems.FOLDER.get())) {
                        addBook(pLevel, pPos, pPlayer, cabinetBlockEntity, itemstack, i);
                        return InteractionResult.sidedSuccess(pLevel.isClientSide);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private static Optional<Vec2> getRelativeHitCoordinatesForBlockFace(BlockHitResult pHitResult, Direction pFace) {
        Direction direction = pHitResult.getDirection();
        if (pFace != direction) {
            return Optional.empty();
        } else {
            BlockPos blockpos = pHitResult.getBlockPos().relative(direction);
            Vec3 vec3 = pHitResult.getLocation().subtract(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            double d0 = vec3.x();
            double d1 = vec3.y();
            double d2 = vec3.z();

            return switch (direction) {
                case NORTH -> Optional.of(new Vec2((float) (1.0D - d0), (float) d1));
                case SOUTH -> Optional.of(new Vec2((float) d0, (float) d1));
                case WEST -> Optional.of(new Vec2((float) d2, (float) d1));
                case EAST -> Optional.of(new Vec2((float) (1.0D - d2), (float) d1));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    private static int getHitSlot(Vec2 pHitPos) {
        int i = pHitPos.y >= 0.5F ? 0 : 1;
        int j = getSection(pHitPos.x);
        return j + i * FOLDERS_PER_ROW;
    }

    private static int getSection(float pX) {
        float f = 0.0625F;
        float f1 = 0.375F;
        if (pX < f1) {
            return 0;
        } else {
            float f2 = 0.6875F;
            return pX < f2 ? 1 : 2;
        }
    }

    private static void addBook(Level pLevel, BlockPos pPos, Player pPlayer, CabinetBlockEntity pBlockEntity, ItemStack pFolderStack, int pSlot) {
        if (!pLevel.isClientSide) {
            pPlayer.awardStat(Stats.ITEM_USED.get(pFolderStack.getItem()));
            pBlockEntity.setItem(pSlot, pFolderStack.split(1));
            pLevel.playSound(null, pPos, SoundEvents.CHISELED_BOOKSHELF_INSERT, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (pPlayer.isCreative()) {
                pFolderStack.grow(1);
            }

            pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos);
        }
    }

    private static void removeBook(Level pLevel, BlockPos pPos, Player pPlayer, CabinetBlockEntity pBlockEntity, int pSlot) {
        if (!pLevel.isClientSide) {
            ItemStack itemstack = pBlockEntity.removeItem(pSlot, 1);
            pLevel.playSound(null, pPos, SoundEvents.CHISELED_BOOKSHELF_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!pPlayer.getInventory().add(itemstack)) {
                pPlayer.drop(itemstack, false);
            }

            pLevel.gameEvent(pPlayer, GameEvent.BLOCK_CHANGE, pPos);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HorizontalDirectionalBlock.FACING);
        SLOT_OCCUPIED_PROPERTIES.forEach(pBuilder::add);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof CabinetBlockEntity cabinetBlockEntity) {
                if (!cabinetBlockEntity.isEmpty()) {
                    for(int i = 0; i < 6; ++i) {
                        ItemStack itemstack = cabinetBlockEntity.getItem(i);
                        if (!itemstack.isEmpty()) {
                            Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), itemstack);
                        }
                    }

                    cabinetBlockEntity.clearContent();
                    pLevel.updateNeighbourForOutputSignal(pPos, this);
                }
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(HorizontalDirectionalBlock.FACING, pRotation.rotate(pState.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return this.rotate(pState, pMirror.getRotation(pState.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.isClientSide()) {
            return 0;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof CabinetBlockEntity cabinetBlockEntity) {
                return cabinetBlockEntity.getLastInteractedSlot() + 1;
            } else {
                return 0;
            }
        }
    }
}
