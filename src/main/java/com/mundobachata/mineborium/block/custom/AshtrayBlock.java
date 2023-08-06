package com.mundobachata.mineborium.block.custom;

import com.mundobachata.mineborium.block.entity.AshtrayBlockEntity;
import com.mundobachata.mineborium.item.custom.CigaretteItem;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AshtrayBlock extends Block implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(5, 0, 5, 11, 3, 11);

    public AshtrayBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AshtrayBlockEntity(pos, state);
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand,
                                          BlockHitResult blockHitResult) {
        if(!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {
            BlockEntity block = level.getBlockEntity(pos);
            if(block instanceof AshtrayBlockEntity blockEntity) {
                ItemStack stack = player.getItemInHand(hand);
                ItemStackHandler inventory = blockEntity.getInventory();
                if(stack.isEmpty()) {
                    if(blockEntity.getItemStack().isEmpty()) {
                        return InteractionResult.SUCCESS;
                    }
                    ItemStack extracted = inventory.extractItem(0,
                            player.isCrouching() ? inventory.getSlotLimit(0) : 1, false);
                    player.setItemInHand(hand, extracted);
                } else if(stack.getItem() instanceof CigaretteItem &&
                        inventory.getStackInSlot(0).getCount() < AshtrayBlockEntity.MAX_NUMBER_OF_CIGARETTES) {
                    ItemStack toInsert = stack.copy();
                    toInsert.setCount(1);

                    ItemStack leftOver = inventory.insertItem(0, toInsert, false);

                    ItemStack handStack = stack.copy();
                    handStack.setCount(handStack.getCount() - 1);
                    handStack.grow(leftOver.getCount());
                    player.setItemInHand(hand, handStack);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newBlockState, boolean isMoving) {
        if(state.getBlock() != newBlockState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof AshtrayBlockEntity) {
                ((AshtrayBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(state, level, pos, newBlockState, isMoving);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return level.isClientSide ? null : (level0, pos0, state0, blockEntity) -> ((AshtrayBlockEntity)blockEntity).tick();
    }
}
