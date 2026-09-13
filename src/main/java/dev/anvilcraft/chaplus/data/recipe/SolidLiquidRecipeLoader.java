package dev.anvilcraft.chaplus.data.recipe;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonFluids;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.SolidLiquidRecipe;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class SolidLiquidRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        SolidLiquidRecipe.builder()
            .cauldron(AddonFluids.GENETIC_OOZE.get())
            .consume(250)
            .requires(Tags.Items.FOODS, 1)
            .requires(Items.DIRT)
            .transform(AddonFluids.GENETIC_OOZE, 500)
            .save(provider, AnvilCraftChaPlus.of("solid_liquid/genetic_ooze"));

    }
}
