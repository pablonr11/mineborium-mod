package com.mundobachata.mineborium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class MalboriumOreBlock extends DropExperienceBlock {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final Vector3f MALBORIUM_PARTICLE_COLOR = Vec3.fromRGB24(0).toVector3f();
    private static final DustParticleOptions SMOKE = new DustParticleOptions(MALBORIUM_PARTICLE_COLOR, 1.0f);

    public MalboriumOreBlock(Properties p_221081_) {
        super(p_221081_);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
    }

    public MalboriumOreBlock(Properties p_221083_, IntProvider p_221084_) {
        super(p_221083_, p_221084_);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(false)));
    }

    public void attack(BlockState blockState, Level level, BlockPos blockPos, Player player) {
        System.out.println("attacking");
        interact(blockState, level, blockPos, true);
        super.attack(blockState, level, blockPos, player);
    }

    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!entity.isSteppingCarefully()) {
            interact(blockState, level, blockPos, false);
        }

        super.stepOn(level, blockPos, blockState, entity);
    }

    private static void interact(BlockState blockState, Level level, BlockPos blockPos, boolean oneTickParticles) {
        spawnParticles(level, blockPos);

        if (!blockState.getValue(LIT) && !oneTickParticles) {
            level.setBlock(blockPos, blockState.setValue(LIT, Boolean.valueOf(true)), 3);
        }
    }

    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(LIT);
    }

    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(LIT)) {
            serverLevel.setBlock(blockPos, blockState.setValue(LIT, Boolean.valueOf(false)), 3);
        }

    }

    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (blockState.getValue(LIT)) {
            spawnParticles(level, blockPos);
        }

    }

    private static void spawnParticles(Level level, BlockPos blockPos) {
        double d0 = 0.5625D;
        RandomSource randomsource = level.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = blockPos.relative(direction);
            if (!level.getBlockState(blockpos).isSolidRender(level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                level.addParticle(SMOKE, (double)blockPos.getX() + d1, (double)blockPos.getY() + d2, (double)blockPos.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(LIT);
    }
}
