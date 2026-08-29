package dev.anvilcraft.chaplus;

import dev.anvilcraft.chaplus.config.AddonServerConfig;
import dev.anvilcraft.chaplus.data.AddonDatagen;
import dev.anvilcraft.chaplus.init.AddonBlocks;
import dev.anvilcraft.chaplus.init.AddonItemGroups;
import dev.anvilcraft.chaplus.init.AddonItems;
import dev.anvilcraft.lib.v2.config.ConfigManager;
import dev.anvilcraft.lib.v2.registrum.Registrum;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(AnvilCraftChaPlus.MOD_ID)
public class AnvilCraftChaPlus {
    public static final String MOD_ID = "anvilcraft_chaplus";
    public static final String MOD_NAME = "Anvilcraft-ChaPlus";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final AddonServerConfig CONFIG = ConfigManager.register(MOD_ID, AddonServerConfig::new);
    public static final Registrum REGISTRUM = Registrum.create(AnvilCraftChaPlus.MOD_ID);

    public AnvilCraftChaPlus(IEventBus modEventBus, ModContainer container) {
        AddonItems.register();
        AddonBlocks.register();
        AddonItemGroups.register(modEventBus);
        AddonDatagen.init();
    }

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
