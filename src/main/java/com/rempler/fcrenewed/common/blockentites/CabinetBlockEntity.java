package com.rempler.fcrenewed.common.blockentites;

import com.mojang.logging.LogUtils;
import com.rempler.fcrenewed.common.block.CabinetBlock;
import com.rempler.fcrenewed.common.init.FCBlockEntities;
import com.rempler.fcrenewed.common.init.FCItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Predicate;

public class CabinetBlockEntity extends BlockEntity implements Container {

    public static final int MAX_FOLDERS_IN_STORAGE = 8;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
    private int lastInteractedSlot = -1;
    public CabinetBlockEntity(BlockPos pPos, BlockState pBlockState) {
        this(FCBlockEntities.CABINET.get(), pPos, pBlockState);
    }

    public CabinetBlockEntity(BlockEntityType<? extends CabinetBlockEntity> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    private void updateState(int pSlot) {
        if (pSlot >= 0 && pSlot < 6) {
            this.lastInteractedSlot = pSlot;
            BlockState blockstate = this.getBlockState();

            for(int i = 0; i < CabinetBlock.SLOT_OCCUPIED_PROPERTIES.size(); ++i) {
                boolean flag = !this.getItem(i).isEmpty();
                BooleanProperty booleanproperty = CabinetBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                blockstate = blockstate.setValue(booleanproperty, Boolean.valueOf(flag));
            }

            Objects.requireNonNull(this.level).setBlock(this.worldPosition, blockstate, 3);
        } else {
            LOGGER.error("Expected slot 0-5, got {}", (int)pSlot);
        }
    }

    public void load(CompoundTag pTag) {
        this.items.clear();
        ContainerHelper.loadAllItems(pTag, this.items);
        this.lastInteractedSlot = pTag.getInt("last_interacted_slot");
    }

    protected void saveAdditional(CompoundTag pTag) {
        ContainerHelper.saveAllItems(pTag, this.items, true);
        pTag.putInt("last_interacted_slot", this.lastInteractedSlot);
    }

    public int count() {
        return (int)this.items.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
    }

    public void clearContent() {
        this.items.clear();
    }

    public int getContainerSize() {
        return MAX_FOLDERS_IN_STORAGE;
    }

    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getItem(int pSlot) {
        return this.items.get(pSlot);
    }

    public ItemStack removeItem(int pSlot, int pAmount) {
        ItemStack itemstack = Objects.requireNonNullElse(this.items.get(pSlot), ItemStack.EMPTY);
        this.items.set(pSlot, ItemStack.EMPTY);
        if (!itemstack.isEmpty()) {
            this.updateState(pSlot);
        }

        return itemstack;
    }

    public ItemStack removeItemNoUpdate(int pSlot) {
        return this.removeItem(pSlot, 1);
    }

    public void setItem(int pSlot, ItemStack pStack) {
        if (pStack.is(FCItems.FOLDER.get())) {
            this.items.set(pSlot, pStack);
            this.updateState(pSlot);
        }

    }

    public boolean canTakeItem(Container pTarget, int pIndex, ItemStack pStack) {
        return pTarget.hasAnyMatching((itemStack) -> {
            if (itemStack.isEmpty()) {
                return true;
            } else {
                return ItemStack.isSameItemSameTags(pStack, itemStack) && itemStack.getCount() + pStack.getCount() <= Math.min(itemStack.getMaxStackSize(), pTarget.getMaxStackSize());
            }
        });
    }

    public int getMaxStackSize() {
        return 1;
    }

    public boolean stillValid(Player pPlayer) {
        return Container.stillValidBlockEntity(this, pPlayer);
    }

    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return pStack.is(FCItems.FOLDER.get()) && this.getItem(pIndex).isEmpty();
    }

    public int getLastInteractedSlot() {
        return this.lastInteractedSlot;
    }

    private LazyOptional<?> itemHandler = LazyOptional.of(this::createUnSidedHandler);
    protected IItemHandler createUnSidedHandler() {
        return new InvWrapper(this);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER)
            return itemHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = LazyOptional.of(this::createUnSidedHandler);
    }

}
