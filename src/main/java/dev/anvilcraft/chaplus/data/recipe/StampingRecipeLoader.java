package dev.anvilcraft.chaplus.data.recipe;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonItems;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.StampingRecipe;

public class StampingRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        StampingRecipe.builder()
            .requires(AddonItems.MUSH_BAR_BOWL)
            .result(AddonItems.MUSH_BAR)
            .save(provider , AnvilCraftChaPlus.of("stamping/mush_bar"));
    }
}
