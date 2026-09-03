package dev.anvilcraft.chaplus.entity;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonItems;
import dev.dubhe.anvilcraft.block.item.ResinBlockItem;
import dev.dubhe.anvilcraft.entity.ThrownHeavyHalberdEntity;
import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

public class ThrownForkEntity extends ThrownHeavyHalberdEntity {
    private ItemStack itemStack = ItemStack.EMPTY;



    public void setForkStack(ItemStack itemStack) {
        if(itemStack.isEmpty())
            return;
        setPickupItemStack(itemStack);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    @Nullable
    private Player player = null;

    public void setItemPlayer(Player player, ItemStack itemStack) {
        this.player = player;
        this.itemStack = itemStack;
    }

    public ThrownForkEntity(EntityType<? extends Entity> type, Level level) {
        super(type, level);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (itemStack.getItem() instanceof ResinBlockItem&&!this.itemStack.isEmpty()) {
            assert player != null;
            ResinBlockItem.useEntity(player,result.getEntity(), itemStack);
            return;
        }
        super.onHitEntity(result);
        if (!this.level().isClientSide()) {
            if (!itemStack.isEmpty()) {
                dropItem(itemStack);
            }
        }
    }

    private  void dropItem( ItemStack itemStack) {
        this.spawnAtLocation(itemStack);
        this.itemStack = ItemStack.EMPTY;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!itemStack.isEmpty())
            dropItem(this.itemStack);
    }

    @Override
    protected  ItemStack getDefaultPickupItem() {

        return AddonItems.TUNING_FORK.asStack();
    }
}
