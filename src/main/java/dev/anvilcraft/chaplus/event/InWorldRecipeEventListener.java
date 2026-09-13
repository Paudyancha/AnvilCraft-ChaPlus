package dev.anvilcraft.chaplus.event;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.lib.v2.recipe.cache.BlockCache;
import dev.anvilcraft.lib.v2.recipe.event.InWorldRecipeEvent;
import dev.anvilcraft.lib.v2.recipe.util.InWorldRecipeContext;
import dev.anvilcraft.lib.v2.util.predicate.BlockStatePredicate;
import dev.anvilcraft.lib.v2.util.predicate.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.ItemInjectRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.List;

import static dev.anvilcraft.chaplus.block.GeneticOozeBlock.INFECTION;

@EventBusSubscriber(modid = AnvilCraftChaPlus.MOD_ID)
public class InWorldRecipeEventListener {
    //与 ItemInjectRecipeLoader 中生成的配方 id 保持一致
    private static final ResourceLocation GENETIC_OOZE_INJECT_ID = AnvilCraftChaPlus.of("item_inject/genetic_ooze_block");

    //用于修改感染值
    @SubscribeEvent
    public static void inItemInject (InWorldRecipeEvent  event) {
        if (!GENETIC_OOZE_INJECT_ID.equals(event.getId())) return;
        if (!(event.getRecipe() instanceof ItemInjectRecipe recipe)) return;
        List<BlockStatePredicate> inputBlocks = recipe.getInputBlocks();
        if (inputBlocks.isEmpty()) return;
        List<ItemIngredientPredicate> inputItems= recipe.getInputItems();
        if (inputItems.isEmpty()) return;
        ItemStack stack = inputItems.getFirst().getItems()[0];
        InWorldRecipeContext context = event.getContext();
        BlockCache cache = context.computeIfAbsent(BlockCache.BLOCK_CACHE);
        BlockPos outputPos = BlockPos.containing(context.getPos().add(recipe.getProperty().getBlockOutputOffset()));

        BlockState state = context.getLevel().getBlockState(outputPos);
        state = state.setValue(INFECTION,getInfection(stack,state));
        cache.setBlock(outputPos,state);
        context.getLevel().setBlockAndUpdate(outputPos, state);
    }

    private static int getInfection(ItemStack stack ,BlockState state) {
        FoodProperties food = stack.get(DataComponents.FOOD);
        if (food != null) return Math.min(15,state.getValue(INFECTION)+stack.getCount()*food.nutrition());
        AnvilCraftChaPlus.LOGGER.info("getInfection {}", stack);
        return state.getValue(INFECTION);
    }
}
