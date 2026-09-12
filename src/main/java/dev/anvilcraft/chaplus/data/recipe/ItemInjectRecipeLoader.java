package dev.anvilcraft.chaplus.data.recipe;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonBlocks;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import net.neoforged.neoforge.common.Tags;

public class ItemInjectRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        ItemInjectRecipe.builder()
            .requires(Tags.Items.FOODS)
            .inputBlock(AddonBlocks.GENETIC_OOZE_BLOCK)
            .resultBlock(AddonBlocks.GENETIC_OOZE_BLOCK)
            .save(provider, AnvilCraftChaPlus.of("item_inject/genetic_ooze_block"));
    }
}
