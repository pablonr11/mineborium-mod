package com.mundobachata.mineborium.block.custom;

import com.mundobachata.mineborium.block.entity.ModBlockEntities;
import com.mundobachata.mineborium.block.entity.RollingMachineBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class RollingMachineBlock extends BaseEntityBlock {

    public RollingMachineBlock(Properties properties) {
        super(properties);
    }

    /* BLOCK ENTITY */

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean isMoving) {
        if(blockState.getBlock() != newBlockState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if(blockEntity instanceof RollingMachineBlockEntity) {
                ((RollingMachineBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(blockState, level, blockPos, newBlockState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos,
                                 Player player, InteractionHand interactionHand, BlockHitResult hit) {

        if(!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(blockPos);
            if(entity instanceof RollingMachineBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer) player), (RollingMachineBlockEntity)entity, blockPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing");
            }
        }
        return super.use(blockState, level, blockPos, player, interactionHand, hit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RollingMachineBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.ROLLING_MACHINE.get(),
                RollingMachineBlockEntity::tick);
    }
}
