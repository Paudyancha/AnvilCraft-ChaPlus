package dev.anvilcraft.chaplus.entity;

import dev.anvilcraft.chaplus.init.AddonItems;
import dev.dubhe.anvilcraft.block.item.ResinBlockItem;
import dev.dubhe.anvilcraft.entity.ThrownHeavyHalberdEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import javax.annotation.Nullable;

import static dev.anvilcraft.chaplus.AnvilCraftChaPlus.LOGGER;

public class ThrownForkEntity extends ThrownHeavyHalberdEntity {
    private ItemStack itemStack = ItemStack.EMPTY;
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

        }
        if (!itemStack.isEmpty()) {
            dropItem(this,itemStack);
        }
        super.onHitEntity(result);
    }
    /*
    *
     */
    private static void dropItem(ThrownForkEntity entity, ItemStack itemStack) {
        Level level = entity.level();
        if (level.isClientSide) return;
        ItemEntity itemEntity = new ItemEntity(level, entity.getX(), entity.getY() + .3D, entity.getZ(), itemStack);
        //entity.spawnAtLocation(itemStack,-0.6F);

        level.addFreshEntity(itemEntity);
        String s = itemStack.getHoverName().getString()+" spawn at: " +itemEntity.getAge();
        LOGGER.info(s);

        itemStack.setCount(0);

    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!itemStack.isEmpty())
            dropItem(this,this.itemStack);

        super.onHitBlock(result);
    }


    @Override
    protected  ItemStack getDefaultPickupItem() {
        return AddonItems.TUNING_FORK.asStack();
    }
}
