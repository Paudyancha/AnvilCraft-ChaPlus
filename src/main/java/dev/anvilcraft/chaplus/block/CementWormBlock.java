package dev.anvilcraft.chaplus.block;

import dev.anvilcraft.chaplus.init.AddonBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CementWormBlock extends WormBlock {
    public CementWormBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AddonBlockEntities.CEMENT_WORM_BLOCK_BLOCK_ENTITY.create(pos, state);
    }
}
