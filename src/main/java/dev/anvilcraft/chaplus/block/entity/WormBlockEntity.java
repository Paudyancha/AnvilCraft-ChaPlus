package dev.anvilcraft.chaplus.block.entity;

import dev.dubhe.anvilcraft.api.fluid.FluidHandlerWrapper;
import dev.dubhe.anvilcraft.api.fluid.IFluidHandlerHolder;
import dev.dubhe.anvilcraft.api.fluid.network.FluidNetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public class WormBlockEntity extends BlockEntity implements IFluidHandlerHolder {
    public static final int CAPACITY = 4 * FluidType.BUCKET_VOLUME;
    private static final String TAG_TANK = "Tank";

    protected final FluidTank tank = new FluidTank(WormBlockEntity.CAPACITY) {
        @Override
        protected void onContentsChanged() {
            WormBlockEntity.this.onTankChanged();
        }
    };

    protected int hitNum = 0;

    public WormBlockEntity(
        BlockEntityType<?> type,
        BlockPos pos,
        BlockState blockState
    ) {
        super(type, pos, blockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.level != null && !this.level.isClientSide()) {
            FluidNetworkManager.INSTANCE.addContainer(this.level, this.getBlockPos());
        }
    }

    @Override
    public void setRemoved() {
        if (this.level != null && !this.level.isClientSide()) {
            FluidNetworkManager.INSTANCE.removeContainer(this.level, this.getBlockPos());
        }
        super.setRemoved();
    }

    private void onTankChanged() {
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(WormBlockEntity.TAG_TANK, this.tank.writeToNBT(registries, new CompoundTag()));
        tag.putInt("hitNum", this.hitNum);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.tank.readFromNBT(registries, tag.getCompound(WormBlockEntity.TAG_TANK));
        this.hitNum = tag.getInt("hitNum");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        tag.put(WormBlockEntity.TAG_TANK, this.tank.writeToNBT(registries, new CompoundTag()));
        tag.putInt("hitNum", this.hitNum);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean onPlayerUse(Player player, InteractionHand hand) {
        if (this.level != null
            && FluidHandlerWrapper.tryInteractWithBottle(player, hand, this.tank, this.level, this.getBlockPos())) {
            return true;
        }
        return FluidUtil.interactWithFluidHandler(player, hand, this.tank);
    }

    @Override
    public IFluidHandler getFluidHandler() {
        return this.tank;
    }

    public void landOn(Level level, BlockPos pos, WormBlockEntity worm) {
        worm.hitNum +=1;
    }
}
