package dev.anvilcraft.chaplus.block;

import dev.anvilcraft.chaplus.block.entity.WormBlockEntity;
import dev.anvilcraft.chaplus.init.AddonBlockEntities;
import dev.anvilcraft.lib.v2.recipe.cache.BlockCache;
import dev.dubhe.anvilcraft.api.block.IIgnitableCauldron;
import dev.dubhe.anvilcraft.api.hammer.IHammerRemovable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WormBlock extends Block implements EntityBlock, IHammerRemovable, IIgnitableCauldron {
    public WormBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hidesNeighborFace(BlockGetter level, BlockPos pos, BlockState state, BlockState neighborState, Direction dir) {
        return dir == Direction.UP || dir == Direction.DOWN;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AddonBlockEntities.CEMENT_WORM_BLOCK_ENTITY.create(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(
        ItemStack stack,
        BlockState state,
        Level level,
        BlockPos pos,
        Player player,
        InteractionHand hand,
        BlockHitResult hitResult
    ) {
        InteractionResult result = super.useItemOn(stack, state, level, pos, player, hand, hitResult).result();
        if (result == InteractionResult.PASS) {
            if (level.getBlockEntity(pos) instanceof WormBlockEntity tank) {
                if (tank.onPlayerUse(player, hand)) {
                    return ItemInteractionResult.sidedSuccess(level.isClientSide());
                }
            }

        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = super.getDrops(state, params);
        if (params.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof WormBlockEntity worm
            && !worm.getFluidHandler().getFluidInTank(0).isEmpty()) {
            for (ItemStack drop : drops) {
                if (drop.is(this.asItem())) {
                    worm.saveToItem(drop, params.getLevel().registryAccess());
                }
            }
        }
        return drops;
    }

    @Override
    public Fluid getFluid(BlockCache cache, BlockPos pos) {
        return Fluids.WATER;
    }
}
