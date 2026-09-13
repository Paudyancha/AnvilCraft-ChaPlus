package dev.anvilcraft.chaplus.fluid;

import dev.anvilcraft.chaplus.init.AddonFluids;
import dev.anvilcraft.chaplus.init.AddonItems;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.capability.wrappers.FluidBucketWrapper;

public class GeneticOozeBucketWrapper extends FluidBucketWrapper {
    public GeneticOozeBucketWrapper(ItemStack container) {
        super(container);
    }

    @Override
    public FluidStack getFluid() {
        if (this.container.is(AddonItems.GENETIC_OOZE_BUCKET.get())) {
            return new FluidStack(AddonFluids.GENETIC_OOZE, FluidType.BUCKET_VOLUME);
        }
        return super.getFluid();
    }
}
