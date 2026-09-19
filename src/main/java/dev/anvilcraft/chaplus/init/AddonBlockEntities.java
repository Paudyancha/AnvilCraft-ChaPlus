package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.block.entity.CementWormBlockEntity;
import dev.anvilcraft.chaplus.block.entity.WormBlockEntity;
import dev.anvilcraft.chaplus.client.renderer.blockentity.WormBlockEntityRenderer;
import dev.anvilcraft.lib.v2.registrum.util.entry.BlockEntityEntry;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.REGISTRUM;

public class AddonBlockEntities {

    public static final BlockEntityEntry<WormBlockEntity> NORMAL_WORM_BLOCK_ENTITY = REGISTRUM
        .blockEntity("normal_worm", WormBlockEntity::new)
        .validBlock(AddonBlocks.CEMENT_WORM_BLOCK)
        .renderer(() -> WormBlockEntityRenderer::new)
        .register();

    public static final BlockEntityEntry<CementWormBlockEntity> CEMENT_WORM_BLOCK_BLOCK_ENTITY = REGISTRUM
        .blockEntity("cement_worm",CementWormBlockEntity::new)
        .validBlock(AddonBlocks.CEMENT_WORM_BLOCK)
        .renderer(() -> WormBlockEntityRenderer::new)
        .register();

    public static void register() {}
}
