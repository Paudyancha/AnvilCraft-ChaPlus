package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.block.ChaAnvilBlock;
import dev.anvilcraft.chaplus.block.GeneticOozeBlock;
import dev.anvilcraft.chaplus.block.GeneticOozeCauldronBlock;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.REGISTRUM;

public class AddonBlocks {
    static {
        REGISTRUM.defaultCreativeTab(AddonItemGroups.CHAPLUS_ITEMS.getKey());
    }

    public static BlockEntry<ChaAnvilBlock> CHA_ANVIL = REGISTRUM
        .block("cha_anvil",ChaAnvilBlock::new)
        .blockstate(DataGenUtil::noExtraModelOrState)
        .initialProperties(()-> Blocks.SPRUCE_PLANKS)
        .simpleItem()
        .recipe((ctx, provider) -> ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get())
            .pattern("AAA")
            .pattern(" M ")
            .pattern("BBB")
            .define('A', Items.BROWN_WOOL)
            .define('M', Items.MELON)
            .define('B', Items.SPRUCE_PLANKS)
            .unlockedBy(AnvilCraftDatagen.hasItem(Items.MELON), AnvilCraftDatagen.has(Items.MELON))
            .save(provider))
        .register();

    public static BlockEntry<GeneticOozeBlock> GENETIC_OOZE_BLOCK = REGISTRUM
        .block("genetic_ooze_block",GeneticOozeBlock::new)
        .lang("Genetic Ooze")
        .initialProperties(()-> Blocks.MUD)
        .blockstate((ctx, provider) -> provider.simpleBlock(ctx.getEntry()))
        .register();

    public static final BlockEntry<GeneticOozeCauldronBlock> GENETIC_OOZE_CAULDRON = REGISTRUM
        .block("genetic_ooze_cauldron", GeneticOozeCauldronBlock::new)
        .initialProperties(() -> Blocks.CAULDRON)
        .blockstate(DataGenUtil::noExtraModelOrState)
        .loot((tables, block) -> tables.dropOther(block, Items.CAULDRON))
        .tag(BlockTags.MINEABLE_WITH_PICKAXE, BlockTags.CAULDRONS)
        .onRegister(block -> Item.BY_BLOCK.put(block, Items.CAULDRON))
        .register();

    public static void register() {}
}
