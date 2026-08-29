package dev.anvilcraft.chaplus.data.lang;

import dev.anvilcraft.chaplus.config.AddonServerConfig;
import dev.anvilcraft.lib.v2.config.ConfigData;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumLangProvider;

public class LangHandler {
    public static void init(RegistrumLangProvider provider) {
        ConfigData.readConfigClass(provider, AddonServerConfig.class);

    }
}
