package dev.anvilcraft.chaplus;

import dev.anvilcraft.chaplus.init.AddonItems;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(AnvilCraftChaPlus.MOD_ID)
public class AnvilCraftChaPlus {
    public static final String MOD_ID = "anvilcraft_chaplus";
    public static final String MOD_NAME = "Anvilcraft-ChaPlus";
    public static final Registrum REGISTRUM = Registrum.create(AnvilCraftChaPlus.MOD_ID);

    public AnvilCraftChaPlus(IEventBus modEventBus, ModContainer container) {
        AddonItems.register();
    }
}
