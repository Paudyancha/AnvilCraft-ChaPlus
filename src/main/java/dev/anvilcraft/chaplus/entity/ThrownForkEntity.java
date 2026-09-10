package dev.anvilcraft.chaplus.entity;

import dev.anvilcraft.chaplus.init.AddonItems;
import dev.dubhe.anvilcraft.block.item.ResinBlockItem;
import dev.dubhe.anvilcraft.entity.ThrownHeavyHalberdEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        if (!this.level().isClientSide()) {
            double speed = this.getDeltaMovement().length();
            double baseDamage = this.getBaseDamage();
            float damage = Mth.ceil(Mth.clamp(speed * baseDamage, 0.0, 2.147483647E9));
            if (result.getEntity() instanceof LivingEntity livingEntity) {
                if(damage > livingEntity.getHealth()) {
                    livingEntity.addEffect(new MobEffectInstance(new MobEffectInstance(MobEffects.WEAKNESS, 5 * 20)));
                }else{
                    super.onHitEntity(result);
                }
                if (itemStack.getItem() instanceof ResinBlockItem&&!this.itemStack.isEmpty()&&player!=null&&livingEntity.getHealth()>0) {
                    try {
                        Method method = itemStack.getItem().getClass().getMethod("useEntity",Player.class,Entity.class,ItemStack.class);
                        method.invoke(null,player,result.getEntity(),itemStack);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
            }

            dropItem(itemStack);

        }
    }

    private  void dropItem( ItemStack itemStack) {
        if (itemStack.isEmpty()) return;
        this.spawnAtLocation(itemStack);
        this.itemStack = ItemStack.EMPTY;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {

        if  (!this.level().isClientSide()) {
            if (!itemStack.isEmpty()){
                if (itemStack.getItem() instanceof ResinBlockItem resinBlockItem) {
                    if (player!=null && ResinBlockItem.hasMob(itemStack)) {
                        resinBlockItem.useOn(new UseOnContext(this.level(),player, InteractionHand.MAIN_HAND, itemStack, result));
                    }
                }
                dropItem(this.itemStack);
            }
        }
        super.onHitBlock(result);
    }

    @Override
    protected  ItemStack getDefaultPickupItem() {

        return AddonItems.TUNING_FORK.asStack();
    }
}
