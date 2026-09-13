package dev.anvilcraft.chaplus.client.event;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonFluids;
import dev.dubhe.anvilcraft.util.ModClientFluidTypeExtensionImpl;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = AnvilCraftChaPlus.MOD_ID, value = Dist.CLIENT)
public class AddonFluidClientExtensions {
    private AddonFluidClientExtensions() {}

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(
            new ModClientFluidTypeExtensionImpl(
                AnvilCraftChaPlus.of("block/genetic_ooze_block"),
                AnvilCraftChaPlus.of("block/genetic_ooze_block")
            ),
            AddonFluids.GENETIC_OOZE_TYPE
        );
    }
}
