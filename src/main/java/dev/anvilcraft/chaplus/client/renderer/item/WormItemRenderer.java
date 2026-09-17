package dev.anvilcraft.chaplus.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.anvilcraft.chaplus.block.entity.WormBlockEntity;
import dev.anvilcraft.chaplus.init.AddonBlocks;
import dev.dubhe.anvilcraft.client.renderer.FluidTankRenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.fluids.FluidStack;

public class WormItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static final String TAG_TANK = "Tank";
    private static final String TAG_FLUID = "Fluid";

    private static final float TUBE_WALL_PIXELS = 1.0F;

    private static WormItemRenderer instance;

    private WormItemRenderer(
        BlockEntityRenderDispatcher blockEntityRenderDispatcher,
        EntityModelSet entityModelSet
    ) {
        super(blockEntityRenderDispatcher, entityModelSet);
    }

    public static WormItemRenderer getInstance() {
        if (instance == null) {
            Minecraft minecraft = Minecraft.getInstance();
            instance = new WormItemRenderer(
                minecraft.getBlockEntityRenderDispatcher(),
                minecraft.getEntityModels()
            );
        }
        return instance;
    }

    @Override
    public void renderByItem(
        ItemStack stack,
        ItemDisplayContext displayContext,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        int packedOverlay
    ) {
        if (!stack.is(AddonBlocks.CEMENT_WORM_BLOCK.asItem())) return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        // 物品模型是 builtin/entity，自身没有几何体，方块模型由渲染器自行绘制
        BakedModel model = Minecraft.getInstance()
            .getBlockRenderer()
            .getBlockModel(AddonBlocks.CEMENT_WORM_BLOCK.get().defaultBlockState());
        renderModel(itemRenderer, stack, poseStack, buffer, packedLight, packedOverlay, model);

        CustomData blockEntityData = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (blockEntityData == null || blockEntityData.isEmpty()) return;
        CompoundTag tankTag = blockEntityData.copyTag().getCompound(TAG_TANK);
        if (!tankTag.contains(TAG_FLUID, CompoundTag.TAG_COMPOUND)) return;

        Minecraft minecraft = Minecraft.getInstance();
        HolderLookup.Provider registries;
        if (minecraft.level != null) {
            registries = minecraft.level.registryAccess();
        } else if (minecraft.getConnection() != null) {
            registries = minecraft.getConnection().registryAccess();
        } else {
            return;
        }

        FluidStack fluid = FluidStack.parseOptional(registries, tankTag.getCompound(TAG_FLUID));
        if (fluid.isEmpty()) return;
        float fill = Mth.clamp(
            (float) fluid.getAmount() / WormBlockEntity.CAPACITY,
            0.0F,
            1.0F
        );
        FluidTankRenderUtil.drawFluidInTank(
            poseStack,
            buffer,
            packedLight,
            fluid,
            fill,
            WormItemRenderer.TUBE_WALL_PIXELS
        );
    }

    private static void renderModel(
        ItemRenderer itemRenderer,
        ItemStack stack,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int packedLight,
        int packedOverlay,
        BakedModel model
    ) {
        for (BakedModel pass : model.getRenderPasses(stack, true)) {
            for (RenderType renderType : pass.getRenderTypes(stack, true)) {
                VertexConsumer vertices = ItemRenderer.getFoilBuffer(
                    buffer,
                    renderType,
                    true,
                    stack.hasFoil()
                );
                itemRenderer.renderModelLists(
                    pass,
                    stack,
                    packedLight,
                    packedOverlay,
                    poseStack,
                    vertices
                );
            }
        }
    }
}
