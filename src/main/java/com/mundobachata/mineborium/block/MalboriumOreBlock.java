package com.mundobachata.mineborium.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class MalboriumOreBlock extends DropExperienceBlock {
    private static final Vector3f MALBORIUM_PARTICLE_COLOR = Vec3.fromRGB24(0).toVector3f();
    private static final DustParticleOptions SMOKE = new DustParticleOptions(MALBORIUM_PARTICLE_COLOR, 1.0f);

    public MalboriumOreBlock(Properties p_221081_) {
        super(p_221081_);
    }

    public MalboriumOreBlock(Properties p_221083_, IntProvider p_221084_) {
        super(p_221083_, p_221084_);
    }

    public void stepOn(Level p_154299_, BlockPos p_154300_, BlockState p_154301_, Entity p_154302_) {
        if (!p_154302_.isSteppingCarefully()) {
            interact(p_154301_, p_154299_, p_154300_);
        }

        super.stepOn(p_154299_, p_154300_, p_154301_, p_154302_);
    }

    private static void interact(BlockState blockState, Level level, BlockPos blockPos) {
        spawnParticles(level, blockPos);
    }

    private static void spawnParticles(Level p_55455_, BlockPos p_55456_) {
        double d0 = 0.5625D;
        RandomSource randomsource = p_55455_.random;

        for(Direction direction : Direction.values()) {
            BlockPos blockpos = p_55456_.relative(direction);
            if (!p_55455_.getBlockState(blockpos).isSolidRender(p_55455_, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + 0.5625D * (double)direction.getStepX() : (double)randomsource.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + 0.5625D * (double)direction.getStepY() : (double)randomsource.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + 0.5625D * (double)direction.getStepZ() : (double)randomsource.nextFloat();
                p_55455_.addParticle(SMOKE, (double)p_55456_.getX() + d1, (double)p_55456_.getY() + d2, (double)p_55456_.getZ() + d3, 0.0D, 0.0D, 0.0D);
            }
        }

    }

}
