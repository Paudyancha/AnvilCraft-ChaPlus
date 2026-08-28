package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.REGISTRUM;

public class AddonItemGroups {
    private static final DeferredRegister<CreativeModeTab> DEFERRED_REGISTER = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB,
        AnvilCraftChaPlus.MOD_ID
    );

    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> CHAPLUS_ITEMS = DEFERRED_REGISTER.register(
"item",() -> CreativeModeTab.builder()
            .icon(AddonItems.TUNING_FORK::asStack)
            .displayItems((ctx, entries) -> {
            })
            .title(
                REGISTRUM.addLang(
                    "itemGroup",
                    AnvilCraftChaPlus.of("addon_items"),
                    "AnvilCraft: Cha Plus"
                )
            )
            .build()
    );

    public static void register(IEventBus modEventBus) {
        DEFERRED_REGISTER.register(modEventBus);
    }
}
