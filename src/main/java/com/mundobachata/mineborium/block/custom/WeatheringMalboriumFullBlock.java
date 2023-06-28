package com.mundobachata.mineborium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class WeatheringMalboriumFullBlock extends LeavesBlock implements WeatheringMalborium{
    private final WeatheringMalborium.WeatherState weatherState;
    public WeatheringMalboriumFullBlock(WeatheringMalborium.WeatherState weatherState, Properties properties) {
        super(properties);
        this.weatherState = weatherState;
    }

    public void randomTick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource source) {
        this.onRandomTick(blockState, level, pos, source);
    }
    public boolean isRandomlyTicking(BlockState blockState) {
        return WeatheringMalborium.getNext(blockState.getBlock()).isPresent();
    }

    public VoxelShape getBlockSupportShape(BlockState p_54456_, BlockGetter p_54457_, BlockPos p_54458_) {
        return Shapes.empty();
    }

    @Override
    public float getShadeBrightness(BlockState p_60472_, BlockGetter p_60473_, BlockPos p_60474_) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        return true;
    }

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }
}
