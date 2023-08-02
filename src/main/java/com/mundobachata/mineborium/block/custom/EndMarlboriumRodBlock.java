package com.mundobachata.mineborium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class EndMarlboriumRodBlock extends RodBlock {
    public EndMarlboriumRodBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getClickedFace();
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos().relative(direction.getOpposite()));
        return blockState.is(this) && blockState.getValue(FACING) == direction ?
                this.defaultBlockState().setValue(FACING, direction.getOpposite()) :
                this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
        Direction direction = blockState.getValue(FACING);
        double[] particleValues = getParticleValues(direction);

        double d0 = (double)blockPos.getX() + particleValues[0];
        double d1 = (double)blockPos.getY() + particleValues[1];
        double d2 = (double)blockPos.getZ() + particleValues[2];

        level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D,
                0.0D, 0.0D);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }

    private double[] getParticleValues(Direction direction) {
        switch(direction) {
            case UP:
                return new double[]{0.5D, 1.0D, 0.5D};
            case DOWN:
                return new double[]{0.5D, 0.0D, 0.5D};
            case NORTH:
                return new double[]{0.5D, 0.5D, 0.0D};
            case SOUTH:
                return new double[]{0.5D, 0.5D, 1.0D};
            case EAST:
                return new double[]{1.0D, 0.5D, 0.5D};
            case WEST:
                return new double[]{0.0D, 0.5D, 0.5D};
            default:
                return new double[]{0.0D, 0.0D, 0.0D};

        }
    }
}
