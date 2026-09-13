package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.fluid.GeneticOoze;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AddonFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(
        NeoForgeRegistries.FLUID_TYPES, AnvilCraftChaPlus.MOD_ID
    );

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AnvilCraftChaPlus.MOD_ID);

    public static final DeferredHolder<FluidType, FluidType> GENETIC_OOZE_TYPE = FLUID_TYPES.register(
        "genetic_ooze", () -> GeneticOoze.TYPE
    );

    public static final DeferredHolder<Fluid,GeneticOoze> GENETIC_OOZE = FLUIDS.register(
        "genetic_ooze", GeneticOoze::new
    );

    public static void register(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
    }
}
