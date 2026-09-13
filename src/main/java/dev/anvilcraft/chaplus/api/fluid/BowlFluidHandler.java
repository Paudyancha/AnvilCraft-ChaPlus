package dev.anvilcraft.chaplus.api.fluid;

import dev.anvilcraft.chaplus.fluid.GeneticOoze;
import dev.anvilcraft.chaplus.init.AddonFluids;
import dev.anvilcraft.chaplus.init.AddonItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class BowlFluidHandler implements IFluidHandlerItem {
    private static final int BOWL_PER_BOTTLE = 250;

    protected ItemStack container;

    public BowlFluidHandler(ItemStack container) {
        this.container = container;
    }

    @Override
    public ItemStack getContainer() {
        return container;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public FluidStack getFluidInTank(int i) {
        if (container.is(AddonItems.MUSH_BAR_BOWL)) {
            return new FluidStack(AddonFluids.GENETIC_OOZE, BOWL_PER_BOTTLE);
        }
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int i) {
        return BOWL_PER_BOTTLE;
    }

    @Override
    public boolean isFluidValid(int i, FluidStack stack) {
        return stack.getFluid() instanceof GeneticOoze;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.getAmount() < BOWL_PER_BOTTLE) return 0;
        if (!container.is(Items.BOWL)) return 0;
        ItemStack filled = getFilledBowl(resource);
        if (filled.isEmpty()) return 0;

        if (action.execute()) {
            container = filled;
        }
        return BOWL_PER_BOTTLE;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.getAmount() < BOWL_PER_BOTTLE) return FluidStack.EMPTY;
        FluidStack result = getFluidInTank(0);
        if (result.isEmpty()) return FluidStack.EMPTY;
        if (!result.is(resource.getFluid())) return FluidStack.EMPTY;

        if (action.execute()) {
            container = new ItemStack(Items.BOWL);
        }
        return result;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (maxDrain < BOWL_PER_BOTTLE) return FluidStack.EMPTY;
        FluidStack result = getFluidInTank(0);
        if (result.isEmpty()) return FluidStack.EMPTY;

        if (action.execute()) {
            container = new ItemStack(Items.BOWL);
        }
        return result;
    }

    private static ItemStack getFilledBowl(FluidStack resource) {
        if (resource.getFluid() instanceof GeneticOoze) {
            return new ItemStack(AddonItems.MUSH_BAR_BOWL.asItem());
        }
        return ItemStack.EMPTY;
    }
}
