package dev.anvilcraft.chaplus.block;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.dubhe.anvilcraft.api.hammer.IHammerRemovable;
import dev.dubhe.anvilcraft.block.better.BetterAnvilBlock;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;

public class ChaAnvilBlock extends BetterAnvilBlock implements IHammerRemovable {
    private static final VoxelShape BASE = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
    private static final VoxelShape X_LEG1 = Block.box(4.0, 4.0, 6.0, 12.0, 10.0, 10.0);
    private static final VoxelShape X_TOP = Block.box(0.0, 10.0, 3.0, 16.0, 16.0, 13.0);
    private static final VoxelShape Z_LEG1 = Block.box(6.0, 4.0, 4.0, 10.0, 10.0, 12.0);
    private static final VoxelShape Z_TOP = Block.box(3.0, 10.0, 0.0, 13.0, 16.0, 16.0);
    private static final VoxelShape SHAPE_X = Shapes.or(BASE, X_LEG1, X_TOP);
    private static final VoxelShape SHAPE_Z = Shapes.or(BASE, Z_LEG1, Z_TOP);

    public static final BooleanProperty HAS_EATEN_MELON = BooleanProperty.create("has_eaten_melon");

    public ChaAnvilBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HAS_EATEN_MELON,false));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? SHAPE_X : SHAPE_Z;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return direction.getAxis() == Direction.Axis.X ? SHAPE_X : SHAPE_Z;
    }

    // 重写onLand方法
    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replaceableState, FallingBlockEntity fallingBlock) {
        if (!level.isClientSide()) {
            BlockPos belowPos = pos.below();
            Block belowBlock = level.getBlockState(pos.below()).getBlock();

            if (level.getBlockState(belowPos).getBlock() == Blocks.MELON) {
                if (level.destroyBlock(belowPos, false))
                    level.setBlockAndUpdate(pos, state.setValue(HAS_EATEN_MELON , true));
            }else  if(state.getValue(HAS_EATEN_MELON))
                level.setBlockAndUpdate(pos, state.setValue(HAS_EATEN_MELON,damageDropped(level , belowPos , belowBlock)));
        }
        super.onLand(level, pos, state, replaceableState, fallingBlock);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HAS_EATEN_MELON);
        super.createBlockStateDefinition(builder);
    }

    // 这个方法用于破坏方块
    private boolean damageDropped(Level level , BlockPos belowPos ,Block belowBlock) {
        if (belowPos.getY() > AnvilCraftChaPlus.CONFIG.maxEffectiveHeight) return true;
        if (belowBlock == Blocks.BEDROCK) {
            level.setBlockAndUpdate(belowPos,ModBlocks.STURDY_DEEPSLATE.getDefaultState());
        } else if (belowBlock == ModBlocks.STURDY_DEEPSLATE.get()) {
            level.setBlockAndUpdate(belowPos,Blocks.DEEPSLATE.defaultBlockState());
        }else if (level.getBlockState(belowPos).is(Tags.Blocks.STONES)) {
            level.destroyBlock(belowPos, false);
        }else {
            return true;
        }
        return false;
    }
}
