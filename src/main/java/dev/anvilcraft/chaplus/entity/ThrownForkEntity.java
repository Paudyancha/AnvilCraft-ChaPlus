package dev.anvilcraft.chaplus.entity;

import dev.anvilcraft.chaplus.init.AddonItems;
import dev.dubhe.anvilcraft.entity.ThrownHeavyHalberdEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrownForkEntity extends ThrownHeavyHalberdEntity {


    public ThrownForkEntity(EntityType<? extends Entity> type , Level level) {
        super(type, level );
    }

    @Override
    protected  ItemStack getDefaultPickupItem() {
        return AddonItems.TUNING_FORK.asStack();
    }
}
