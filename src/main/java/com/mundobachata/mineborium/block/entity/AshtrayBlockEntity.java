package com.mundobachata.mineborium.block.entity;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AshtrayBlockEntity extends BlockEntity {
    public final static int MAX_NUMBER_OF_CIGARETTES = 4;

    private final ItemStackHandler inventory = new ItemStackHandler() {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            AshtrayBlockEntity.this.setChanged();
        }
    };

    private final LazyOptional<IItemHandler> optional = LazyOptional.of(() -> this.inventory);

    public AshtrayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ASHTRAY_BLOCK_ENTITY.get(), pos, state);
    }

    public void tick() {
        if(this.level == null || this.level.isClientSide) {
            return;
        }

        // Sync client (Should change this with networking for better performance)
        this.level.sendBlockUpdated(this.worldPosition, getBlockState(), getBlockState(),
                Block.UPDATE_ALL);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        CompoundTag mineboriumModData = nbt.getCompound(Mineborium.MOD_ID);

        this.inventory.deserializeNBT(mineboriumModData.getCompound("inventory"));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag nbt) {
        load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        CompoundTag mineboriumModData = new CompoundTag();
        mineboriumModData.put("inventory", this.inventory.serializeNBT());

        nbt.put(Mineborium.MOD_ID, mineboriumModData);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.optional.cast();
        }

        return super.getCapability(cap);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(this.inventory.getSlots());

        for (int i = 0; i< this.inventory.getSlots(); i++) {
            inventory.setItem(i, this.inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public void invalidateCaps() {
        this.optional.invalidate();
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public ItemStack getItemStack() {
        return this.inventory.getStackInSlot(0);
    }

    public boolean hasCigarette() {
        return this.getItemStack().getItem() == ModItems.CIGARETTE.get() ||
                this.getItemStack().getItem() == ModItems.DRY_CIGARETTE.get();
    }

    public LazyOptional<IItemHandler> getOptional() {
        return this.optional;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
