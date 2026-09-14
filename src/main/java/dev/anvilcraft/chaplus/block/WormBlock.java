package dev.anvilcraft.chaplus.block;

import dev.anvilcraft.lib.v2.recipe.cache.BlockCache;
import dev.dubhe.anvilcraft.api.block.IIgnitableCauldron;
import dev.dubhe.anvilcraft.api.hammer.IHammerRemovable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class WormBlock extends Block implements IHammerRemovable, IIgnitableCauldron {
    public WormBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        return dir == Direction.UP || dir == Direction.DOWN;
    }

    @Override
    public Fluid getFluid(BlockCache cache, BlockPos pos) {
        return Fluids.WATER;
    }
}
