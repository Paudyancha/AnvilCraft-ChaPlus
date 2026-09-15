package dev.anvilcraft.chaplus.data.recipe;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonBlocks;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.anvilcraft.lib.v2.util.predicate.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.block.ModBlocks;
import dev.dubhe.anvilcraft.init.item.ModItemSubPredicates;
import dev.dubhe.anvilcraft.item.property.predicate.ItemSavedEntityPredicate;
import dev.dubhe.anvilcraft.recipe.anvil.procedural.ProceduralProcessRecipeBuilder;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.BlockCompressRecipe;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

public class ProceduralProcessRecipeLoader {
    public static void init(RegistrumRecipeProvider provider){
        ProceduralProcessRecipeBuilder.of(AddonBlocks.GENETIC_OOZE_BLOCK.get())
            .addStep(
                BlockCompressRecipe.builder()
                    .input(Blocks.SNOW_BLOCK)
                    .input(AddonBlocks.GENETIC_OOZE_BLOCK.get())
                    .result(ModBlocks.WIP_BLOCK.get())
                    .buildRecipe()
            )
            .addStep(
                ItemInjectRecipe.builder()
                    .inputBlock(ModBlocks.WIP_BLOCK.get())
                    .requires(
                        ItemIngredientPredicate
                            .of(ModBlocks.RESIN_BLOCK.asItem())
                            .withSubPredicate(
                                ModItemSubPredicates.SAVED_ENTITY.get(),
                                ItemSavedEntityPredicate.any()
                            )
                            .build()
                    )
                    .resultBlock(ModBlocks.WIP_BLOCK)
                    .buildRecipe()
            ).addStep(
                ItemInjectRecipe.builder()
                    .inputBlock(ModBlocks.WIP_BLOCK.get())
                    .requires(Tags.Items.GLASS_BLOCKS)
                    .resultBlock(ModBlocks.WIP_BLOCK.get())
                    .buildRecipe()
            )
            .result(AddonBlocks.CEMENT_WORM_BLOCK)
            .icon(AddonBlocks.CEMENT_WORM_BLOCK.asStack())
            .displayedModels(
                AnvilCraftChaPlus.of("cement_worm_wip0"),
                AnvilCraftChaPlus.of("cement_worm_wip1")
            )
            .save(provider, AnvilCraftChaPlus.of("procedural_process/cement_worm_block"));
    }
}
