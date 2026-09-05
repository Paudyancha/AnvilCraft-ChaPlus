package dev.anvilcraft.chaplus.item;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.entity.ThrownForkEntity;
import dev.anvilcraft.chaplus.init.AddonEntities;
import dev.anvilcraft.chaplus.network.TuningForkPacket;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class TuningFork extends Item  {

    public TuningFork(Properties properties) {
        super(properties);
    }

    //添加一个thrown属性
    @Override
    @SuppressWarnings({"removal"})
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        ItemProperties.register(
            this,
            ResourceLocation.withDefaultNamespace("throwing"),
            (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide) {
            ItemStack itemStack = player.getItemInHand(usedHand);
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(itemStack);
        }
        return super.use(level, player, usedHand);
    }

    //结束时调用.如果时间大于配置发射
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeCharged) {
        if (!level.isClientSide) {
            if (entity instanceof Player player) {
                if (this.getUseDuration(stack, entity) - timeCharged >= AnvilCraftChaPlus.CONFIG.THROW_THRESHOLD_TIME) {
                    this.shootFork(level, player);
                    if (!player.getAbilities().instabuild)
                        stack.shrink(1);
                }
            }
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        if (!level.isClientSide) {
            ItemStack offItem = miningEntity.getOffhandItem();
            if(state.getBlock().asItem() == offItem.getItem())
                level.destroyBlock(pos, true);
        }
        return super.mineBlock(stack, level, state, pos, miningEntity);
    }

    private void shootFork(Level level, Player player) {
        ThrownForkEntity fork = AddonEntities.THROWN_FORK.create(level);

        if (fork == null) return;

        // 生成位置
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 spawnPos = eyePos.add(lookVec.scale(0.5));
        ItemStack sStack = player.getOffhandItem();

        fork.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        fork.pickup = AbstractArrow.Pickup.ALLOWED;
        if (!player.getMainHandItem().isEmpty())
            fork.setForkStack(player.getMainHandItem());
        if (player.getUsedItemHand()!= InteractionHand.OFF_HAND)
            if(!sStack.isEmpty())
                fork.setItemPlayer(player,sStack.split(1));
        fork.setForkStack(player.getMainHandItem().copy());
        fork.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);
        level.addFreshEntity(fork);
    }

    public int tryBreakBlock(Level level, BlockState state, BlockPos pos) {
        if (!level.isClientSide) {
            level.destroyBlock(pos, true);
        }else  {
            //new TuningForkPacket(pos);
        }
        return 0;
    }
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }
}
