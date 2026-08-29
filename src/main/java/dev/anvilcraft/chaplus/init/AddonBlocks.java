package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.block.ChaAnvilBlock;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntry;
import dev.dubhe.anvilcraft.util.DataGenUtil;
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
        .register();

    public static void register() {}
}
