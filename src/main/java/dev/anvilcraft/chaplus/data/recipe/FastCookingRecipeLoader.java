package dev.anvilcraft.chaplus.data.recipe;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonItems;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.FastCookingRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

public class FastCookingRecipeLoader {
    public static void init(RegistrumRecipeProvider provider) {
        FastCookingRecipe.builder()
            .cauldron(Blocks.WATER_CAULDRON)
            .requires(Items.DIRT)
            .requires(Tags.Items.FOODS)
            .requires(Items.BOWL)
            .result(AddonItems.MUSH_BAR_BOWL)
            .save(provider , AnvilCraftChaPlus.of("fast_cooking/mush_bar_bowl"));
    }
}
