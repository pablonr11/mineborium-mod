package com.mundobachata.mineborium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class WeatheringMalboriumFullBlock extends Block implements WeatheringMalborium{
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

    @Override
    public WeatherState getAge() {
        return this.weatherState;
    }
}
