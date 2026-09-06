package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.item.TuningFork;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.anvilcraft.lib.v2.registrum.util.entry.ItemEntry;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.REGISTRUM;

public class AddonItems {
    static {
        REGISTRUM.defaultCreativeTab(AddonItemGroups.CHAPLUS_ITEMS.getKey());
    }
    public static final ItemEntry<TuningFork> TUNING_FORK = REGISTRUM
        .item("tuning_fork", TuningFork::new)
        .model(DataGenUtil::noExtraModelOrState)
        .properties(prop -> prop.durability(1561).stacksTo(1))
        .recipe((ctx, provider) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
            .pattern(" B ")
            .pattern(" RB")
            .pattern("A  ")
            .define('A', ModItems.ROYAL_STEEL_NUGGET)
            .define('R', ModItems.ROYAL_STEEL_INGOT )
            .define('B', Items.IRON_NUGGET)
            .unlockedBy("has_iron_nugget", RegistrumRecipeProvider.has(Items.IRON_NUGGET))
            .unlockedBy("has_royal_steel_nugget", RegistrumRecipeProvider.has(ModItems.ROYAL_STEEL_NUGGET))
            .unlockedBy("has_royal_steel_ingot", RegistrumRecipeProvider.has(ModItems.ROYAL_STEEL_NUGGET))
            .save(provider)
        )
        .register();
    public static void register() {}
}
