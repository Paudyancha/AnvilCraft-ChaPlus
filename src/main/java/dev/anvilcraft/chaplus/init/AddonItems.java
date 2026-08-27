package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.item.TuningFork;
import dev.anvilcraft.lib.v2.registrum.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.REGISTRUM;

public class AddonItems {
//    static {
//        REGISTRUM.defaultCreativeTab();
//    }
    public static final ItemEntry<TuningFork> TUNING_FORK = REGISTRUM
        .item("tuning_fork", TuningFork::new)
        .register();
    public static void register() {}
}
