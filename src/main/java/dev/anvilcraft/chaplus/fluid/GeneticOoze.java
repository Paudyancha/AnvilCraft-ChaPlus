package dev.anvilcraft.chaplus.fluid;

import dev.anvilcraft.chaplus.init.AddonBlocks;
import dev.anvilcraft.chaplus.init.AddonFluids;
import dev.anvilcraft.chaplus.init.AddonItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class GeneticOoze extends Fluid {

    public static final FluidType TYPE = new FluidType(FluidType.Properties.create()
        .descriptionId("block.anvilcraft_chaplus.genetic_ooze_block")
        .density(3000)
        .viscosity(6000)
        .motionScale(0)
        .canPushEntity(false)
        .canSwim(false)
        .fallDistanceModifier(0)
        .canConvertToSource(false)
        .supportsBoating(false)
        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.MUD_PLACE)
    );

    @Override
    public Item getBucket() {
        return AddonItems.GENETIC_OOZE_BUCKET.asItem();
    }

    @Override
    protected boolean canBeReplacedWith(
        FluidState fluidState,
        BlockGetter blockGetter,
        BlockPos blockPos,
        Fluid fluid,
        Direction direction
    ) {
        return false;
    }

    @Override
    protected Vec3 getFlow(BlockGetter blockGetter, BlockPos blockPos, FluidState fluidState) {
        return Vec3.ZERO;
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    public float getHeight(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos) {
        return 0;
    }

    @Override
    public float getOwnHeight(FluidState fluidState) {
        return 0;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState fluidState) {
        return AddonBlocks.GENETIC_OOZE_BLOCK.getDefaultState();
    }

    @Override
    public boolean isSource(FluidState fluidState) {
        return true;
    }

    @Override
    public int getAmount(FluidState fluidState) {
        return 8;
    }

    @Override
    public VoxelShape getShape(FluidState fluidState, BlockGetter level, BlockPos pos) {
        return AddonBlocks.GENETIC_OOZE_BLOCK.getDefaultState().getShape(level, pos);
    }

    @Override
    public FluidType getFluidType() {
        return AddonFluids.GENETIC_OOZE_TYPE.get();
    }
}
