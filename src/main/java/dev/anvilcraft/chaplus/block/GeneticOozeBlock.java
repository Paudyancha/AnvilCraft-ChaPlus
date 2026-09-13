package dev.anvilcraft.chaplus.block;

import dev.anvilcraft.chaplus.init.AddonBlocks;
import dev.anvilcraft.chaplus.init.AddonItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class GeneticOozeBlock extends Block implements BucketPickup {
    public static IntegerProperty INFECTION = IntegerProperty.create("infection", 0, 15);

    public GeneticOozeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(INFECTION,0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INFECTION);
    }

    @Override
    public ItemStack pickupBlock(@Nullable Player player, LevelAccessor level, BlockPos pos, BlockState blockState) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 11);
        return AddonItems.GENETIC_OOZE_BUCKET.asStack();
    }

    @Override
    public Optional<SoundEvent> getPickupSound(BlockState state) {
        return Optional.empty();
    }

    @Override
    @Deprecated
    public Optional<SoundEvent> getPickupSound() {
        return getPickupSound(defaultBlockState());
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return state.getValue(INFECTION) > 0;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        List<BlockPos> blockPosList = new ArrayList<>();
        List<BlockPos> oozePosList = new ArrayList<>();
        for (Direction dir : Direction.values()) {
            BlockPos targetPos = pos.relative(dir);
            if (level.getBlockState(targetPos).is(BlockTags.DIRT)) blockPosList.add(targetPos);
            if (level.getBlockState(targetPos).is(AddonBlocks.GENETIC_OOZE_BLOCK)) oozePosList.add(targetPos);
        }
        if (!blockPosList.isEmpty()){
            level.setBlockAndUpdate(blockPosList.get(random.nextInt(blockPosList.size())), AddonBlocks.GENETIC_OOZE_BLOCK.getDefaultState());
            level.setBlockAndUpdate(pos, state.setValue(INFECTION, state.getValue(INFECTION) - 1));
            return;
        }
        if (!oozePosList.isEmpty()){
            BlockPos targetPos = oozePosList.get(random.nextInt(oozePosList.size()));
            BlockState targetState = level.getBlockState(targetPos);
            int infection = state.getValue(INFECTION);
            int targetInfection = targetState.getValue(INFECTION);
            if (infection > targetInfection) {
                level.setBlockAndUpdate(targetPos, targetState.setValue(INFECTION, targetInfection + 1));
                level.setBlockAndUpdate(pos, state.setValue(INFECTION, infection - 1));
            }
        }
    }
}
