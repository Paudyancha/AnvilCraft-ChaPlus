package dev.anvilcraft.chaplus.client.event;

import dev.anvilcraft.chaplus.AnvilCraftChaPlus;
import dev.anvilcraft.chaplus.init.AddonItems;
import dev.anvilcraft.chaplus.item.TuningFork;
import dev.anvilcraft.chaplus.network.TuningForkPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = AnvilCraftChaPlus.MOD_ID ,value = Dist.CLIENT)
public class TuningForkClientEventListener {
    private TuningForkClientEventListener() {}

    @SubscribeEvent
    public static void breakBlock(PlayerInteractEvent.LeftClickBlock event){
        if (!(event.getItemStack().getItem() instanceof TuningFork)) return;
        PacketDistributor.sendToServer(new TuningForkPacket(event.getPos()));
        event.setCanceled(true);
    }

}
