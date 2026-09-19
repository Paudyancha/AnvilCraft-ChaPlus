package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.block.CementWormBlock;
import dev.anvilcraft.chaplus.block.ChaAnvilBlock;
import dev.anvilcraft.chaplus.block.GeneticOozeBlock;
import dev.anvilcraft.chaplus.block.GeneticOozeCauldronBlock;
import dev.anvilcraft.chaplus.block.item.WormBlockItem;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.client.model.generators.ModelFile;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.REGISTRUM;

public class AddonBlocks {
    static {
        REGISTRUM.defaultCreativeTab(AddonItemGroups.CHAPLUS_ITEMS.getKey());
    }

    @SuppressWarnings("unused")
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
        .simpleItem()
        .initialProperties(()-> Blocks.MUD)
        .properties(BlockBehaviour.Properties::noLootTable)
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

    @SuppressWarnings("unused")
    public static final BlockEntry<CementWormBlock>  CEMENT_WORM_BLOCK = REGISTRUM
        .block("cement_worm_block", CementWormBlock::new)
        .lang("Cement Worm")
        .initialProperties(()-> Blocks.MUD)
        .properties(BlockBehaviour.Properties::noOcclusion)
        .blockstate(DataGenUtil::noExtraModelOrState)
        .item(WormBlockItem::new)
        .properties(properties -> properties.component(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY))
        .model((ctx, prov) -> prov.getBuilder(ctx.getName())
            .parent(new ModelFile.UncheckedModelFile("builtin/entity"))
            .texture("particle", AnvilCraftChaPlus.of("block/cement_worm_side"))
            .transforms()
            .transform(ItemDisplayContext.GUI)
            .rotation(30, 225, 0).scale(0.625F).end()
            .transform(ItemDisplayContext.GROUND)
            .translation(0, 3, 0).scale(0.25F).end()
            .transform(ItemDisplayContext.FIXED)
            .scale(0.5F).end()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND)
            .rotation(75, 45, 0).translation(0, 2.5F, 0).scale(0.375F).end()
            .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND)
            .rotation(0, 45, 0).scale(0.4F).end()
            .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND)
            .rotation(0, 225, 0).scale(0.4F).end()
            .end())
        .build()
        .register();

    public static void register() {}
}
