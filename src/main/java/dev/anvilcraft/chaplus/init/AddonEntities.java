package dev.anvilcraft.chaplus.init;

import dev.anvilcraft.chaplus.client.renderer.entity.ForkRenderer;
import dev.anvilcraft.chaplus.entity.ThrownForkEntity;
import dev.anvilcraft.lib.v2.registrum.util.entry.EntityEntry;
import dev.dubhe.anvilcraft.client.renderer.entity.SpectralProjectileRenderer;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.SpectralArrowRenderer;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.SpectralArrow;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.REGISTRUM;

public class AddonEntities {

    public static EntityEntry<ThrownForkEntity> THROWN_FORK = REGISTRUM
        .<ThrownForkEntity>entity("thrown_fork", ThrownForkEntity::new, MobCategory.MISC)
        .renderer(() -> ForkRenderer::new)
        .properties(builder -> builder
            .sized(0.5F, 0.5F)
            .eyeHeight(0.13F)
                .clientTrackingRange(4)
                .updateInterval(20)
        )
        .register()
        ;

    public static void register(){}
}
