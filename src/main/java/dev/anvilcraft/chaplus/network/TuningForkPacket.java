package dev.anvilcraft.chaplus.network;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.item.TuningFork;
import dev.anvilcraft.lib.v2.network.packet.IServerboundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public  class TuningForkPacket implements IServerboundPacket {

    public static final Type<TuningForkPacket> TYPE =
        new Type<>(AnvilCraftChaPlus.of("tuning_break"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TuningForkPacket> STREAM_CODEC =
        StreamCodec.ofMember(TuningForkPacket::encode, TuningForkPacket::new);


    private final BlockPos pos;
    private final InteractionHand hand;

    public TuningForkPacket(BlockPos pos , InteractionHand hand) {
        this.pos = pos;
        this.hand = hand;
    }

    public TuningForkPacket(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.hand = buf.readEnum(InteractionHand.class);
    }

    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeEnum(hand);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handleOnServer(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return ;
        Level level = serverPlayer.level();
        if (!level.isLoaded(this.pos)) return ;

        ItemStack stack = player.getItemInHand(this.hand);
        if (!(stack.getItem() instanceof TuningFork)) return;
        if (player.getCooldowns().isOnCooldown(stack.getItem())) return;
        TuningFork.tryBreakBlock(level ,player ,stack ,this.pos);

    }
}
