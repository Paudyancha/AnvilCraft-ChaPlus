package dev.anvilcraft.chaplus.block.entity;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.dubhe.anvilcraft.init.block.ModFluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class CementWormBlockEntity extends WormBlockEntity{

    public Sizes size = Sizes.ONE;

    public CementWormBlockEntity(
        BlockEntityType<?> type,
        BlockPos pos,
        BlockState blockState
    ) {
        super(type, pos, blockState);
    }

    @Override
    public void landOn(Level level, BlockPos pos, WormBlockEntity worm) {
        boolean i = CementWormBlockEntity.platformExp(level, pos, worm, this.size.size);
        if (i) return;
        super.landOn(level, pos, worm);
    }

    private static boolean platformExp(Level level, BlockPos pos, WormBlockEntity worm, int size) {
        if (level.isClientSide()) return false;
        if (worm.tank.isEmpty() || !worm.tank.getFluid().is(ModFluidTags.CEMENT)) return false;
        MinecraftServer server = level.getServer();
        if (server == null) return false;
        if (worm.tank.getFluidAmount() < 1000 << size ) return false;
        StructureTemplateManager manager = server.getStructureManager();
        ResourceLocation id = AnvilCraftChaPlus.of("plat0");
        StructureTemplate template = manager.get(id).orElse(null);
        if (template == null) return false;
        ChunkPos chunkPos = new ChunkPos(pos);
        BlockPos posO = new BlockPos(chunkPos.getMinBlockX(), pos.getY() - 1, chunkPos.getMinBlockZ());
        if (worm.tank.isEmpty()) return false;
        StructurePlaceSettings settings = new StructurePlaceSettings();
        worm.tank.drain(1000 << size, IFluidHandler.FluidAction.EXECUTE);
        return template.placeInWorld((ServerLevelAccessor) level, posO, posO, settings,level.getRandom(), 3);
    }

    public enum Sizes {
        ONE(0),
        NINE(1),
        MAX(2);

        public final int size;

        Sizes(int i) {
            if (i > 0 || i < 2 )
                this.size = i;
            else
                this.size = 0;
        }
    }
}
