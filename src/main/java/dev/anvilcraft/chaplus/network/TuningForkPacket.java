package dev.anvilcraft.chaplus.network;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.lib.v2.network.packet.IServerboundPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public  class TuningForkPacket implements IServerboundPacket {

    public static final Type<TuningForkPacket> TYPE =
        new Type<>(AnvilCraftChaPlus.of("tuning_break"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TuningForkPacket> STREAM_CODEC =
        StreamCodec.ofMember(TuningForkPacket::encode, TuningForkPacket::new);

    public static final IPayloadHandler<TuningForkPacket> HANDLER  = TuningForkPacket::handle;

    private final BlockPos pos;

    public TuningForkPacket(BlockPos pos) {
        this.pos = pos;
    }

    public TuningForkPacket(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void encode(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(TuningForkPacket packet, IPayloadContext context) {
        context.enqueueWork(
            () -> {
                if (context.flow().isServerbound())
                    handleOnServer(packet ,context);
            }
        );
    }

    private static void handleOnServer(TuningForkPacket packet, IPayloadContext context) {
        var player = context.player();
        var level  = player.level();
        level.destroyBlock(packet.pos,true);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handleOnServer(Player player) {

    }
}
