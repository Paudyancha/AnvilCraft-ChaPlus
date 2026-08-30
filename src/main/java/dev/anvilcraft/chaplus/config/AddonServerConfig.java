package dev.anvilcraft.chaplus.config;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.lib.v2.config.Comment;
import dev.anvilcraft.lib.v2.config.Config;
import net.neoforged.fml.config.ModConfig;

@Config(name = AnvilCraftChaPlus.MOD_ID , type = ModConfig.Type.SERVER)
public class AddonServerConfig {
    @Comment("Effective maximum height of Cha Anvil")
    public int maxEffectiveHeight = -59;

    @Comment("Tuning fork throw threshold time")
    public int THROW_THRESHOLD_TIME = 10;
}
