package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.item.TuningFork;
import dev.anvilcraft.lib.v2.registrum.providers.RegistrumRecipeProvider;
import dev.anvilcraft.lib.v2.registrum.util.entry.ItemEntry;
import dev.dubhe.anvilcraft.init.item.ModItems;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

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

    public static final ItemEntry<Item> MUSH_BAR = REGISTRUM
        .item("mush_bar", Item::new)
        .properties(prop -> {
            prop.food(Foods.ROTTEN_FLESH);
            return prop;
        })
        .register();

    public static final ItemEntry<Item> MUSH_FRY = REGISTRUM
        .item("mush_fry", Item::new)
        .properties(prop -> {
            prop.food(Foods.ROTTEN_FLESH);
            return prop;
        })
        .recipe((ctx, provider) -> {
            SimpleCookingRecipeBuilder
                .campfireCooking(
                    Ingredient.of(AddonItems.MUSH_BAR.get()),
                    RecipeCategory.FOOD,
                    ctx.get(),
                    1.0F,
                    600)
                .unlockedBy("has_mush_bar", RegistrumRecipeProvider.has(AddonItems.MUSH_BAR.get()))
                .save(provider, ResourceLocation.fromNamespaceAndPath(AnvilCraftChaPlus.MOD_ID, "smelting/mush_fry_from_campfire"));
            SimpleCookingRecipeBuilder
                .smoking(
                    Ingredient.of(AddonItems.MUSH_BAR.get()),
                    RecipeCategory.FOOD,
                    ctx.get(),
                    1.0F,
                    100)
                .unlockedBy("has_mush_bar", RegistrumRecipeProvider.has(AddonItems.MUSH_BAR.get()))
                .save(provider, ResourceLocation.fromNamespaceAndPath(AnvilCraftChaPlus.MOD_ID, "smelting/mush_fry_from_smoker"));
            SimpleCookingRecipeBuilder
                .smelting(
                    Ingredient.of(AddonItems.MUSH_BAR.get()),
                    RecipeCategory.FOOD,
                    ctx.get(),
                    1.0F,
                    200)
                .unlockedBy("has_mush_bar", RegistrumRecipeProvider.has(AddonItems.MUSH_BAR.get()))
                .save(provider, ResourceLocation.fromNamespaceAndPath(AnvilCraftChaPlus.MOD_ID, "smelting/mush_fry_from_furnace"));
        })
        .register();

    public static void register() {}
}
