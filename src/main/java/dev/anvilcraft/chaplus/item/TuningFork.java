package dev.anvilcraft.chaplus.item;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.config.AddonServerConfig;
import dev.anvilcraft.chaplus.entity.ThrownForkEntity;
import dev.anvilcraft.chaplus.init.AddonEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TuningFork extends Item  {
    public static final int THROW_THRESHOLD_TIME = AnvilCraftChaPlus.CONFIG.THROW_THRESHOLD_TIME;

    public TuningFork(Properties properties) {
        super(properties);
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

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeCharged) {
        AnvilCraftChaPlus.LOGGER.info(String.valueOf(11371));
        if (entity instanceof Player player) {
            // timeCharged 是总蓄力时间 - 实际使用时间
            // 计算实际使用时间
            int i = this.getUseDuration(stack, entity) - timeCharged;
            // 如果蓄力时间大于阈值，则投掷
            AnvilCraftChaPlus.LOGGER.info(String.valueOf(i));
            if (i >= THROW_THRESHOLD_TIME) {
                if (!level.isClientSide) {
                    // 投掷逻辑
                    this.shootFork(level, player, stack);
                }
                // 投掷后，如果不是创造模式，消耗物品
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            }
        }
        super.releaseUsing(stack, level, entity, timeCharged);
    }

    private void shootFork(Level level, Player player, ItemStack stack) {
        ThrownForkEntity fork = AddonEntities.THROWN_FORK.create(level);
        if (fork == null) return;

        // 生成位置：玩家眼睛前方 0.5 格
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 spawnPos = eyePos.add(lookVec.scale(0.5));

        fork.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
        // 正确传入射手（player），并使用玩家的朝向
        fork.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F);

        level.addFreshEntity(fork);
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
