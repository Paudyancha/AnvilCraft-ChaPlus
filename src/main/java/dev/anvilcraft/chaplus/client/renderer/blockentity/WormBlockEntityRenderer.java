package dev.anvilcraft.chaplus.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anvilcraft.chaplus.block.entity.WormBlockEntity;
import dev.dubhe.anvilcraft.client.renderer.FluidTankRenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class WormBlockEntityRenderer implements BlockEntityRenderer<WormBlockEntity> {

    private static final float TUBE_WALL_PIXELS = 1.0F;

    public WormBlockEntityRenderer(BlockEntityRendererProvider.Context ignore) {
    }

    @Override
    public void render(
        WormBlockEntity tank, float tickDelta, PoseStack ms, MultiBufferSource vertexConsumers, int light, int overlay) {
        IFluidHandler handler = tank.getFluidHandler();
        FluidStack fluid = handler.getFluidInTank(0);
        if (fluid.isEmpty()) return;

        float fill = (float) fluid.getAmount() / handler.getTankCapacity(0);
        fill = Mth.clamp(fill, 0, 1);

        FluidTankRenderUtil.drawFluidInTank(
            ms,
            vertexConsumers,
            light,
            fluid,
            fill,
            WormBlockEntityRenderer.TUBE_WALL_PIXELS
        );
    }
}
