package dev.anvilcraft.chaplus.event;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.api.fluid.BowlFluidHandler;
import dev.anvilcraft.chaplus.fluid.GeneticOozeBucketWrapper;
import dev.anvilcraft.chaplus.init.AddonBlockEntities;
import dev.anvilcraft.chaplus.init.AddonBlocks;
import dev.anvilcraft.chaplus.init.AddonFluids;
import dev.anvilcraft.chaplus.init.AddonItems;
import dev.dubhe.anvilcraft.block.Layered4LevelCauldronBlock;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;

@EventBusSubscriber(modid = AnvilCraftChaPlus.MOD_ID)
public class AddonCapabilitiesEventListener {
    private AddonCapabilitiesEventListener() {}

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
            Capabilities.FluidHandler.ITEM,
            (stack, context) -> new GeneticOozeBucketWrapper(stack),
            AddonItems.GENETIC_OOZE_BUCKET.get()
        );

        event.registerItem(
            Capabilities.FluidHandler.ITEM,
            (stack, ctx) -> new BowlFluidHandler(stack),
            AddonItems.MUSH_BAR_BOWL, Items.BOWL
        );

        event.registerBlockEntity(
            Capabilities.FluidHandler.BLOCK,
            AddonBlockEntities.NORMAL_WORM_BLOCK_ENTITY.get(),
            (be, side) -> be.getFluidHandler()
        );

        event.registerBlockEntity(
            Capabilities.FluidHandler.BLOCK,
            AddonBlockEntities.CEMENT_WORM_BLOCK_BLOCK_ENTITY.get(),
            (be, side) -> be.getFluidHandler()
        );
    }

    @SubscribeEvent
    public static void registerCauldronFluidContent(RegisterCauldronFluidContentEvent event) {
        event.register(
            AddonBlocks.GENETIC_OOZE_CAULDRON.get(),
            AddonFluids.GENETIC_OOZE.get(),
            1000,
            Layered4LevelCauldronBlock.LEVEL
        );
    }
}
