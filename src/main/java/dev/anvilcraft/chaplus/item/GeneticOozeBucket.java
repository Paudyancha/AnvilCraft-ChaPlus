package dev.anvilcraft.chaplus.item;

import dev.anvilcraft.chaplus.init.AddonBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.SolidBucketItem;

public class GeneticOozeBucket extends SolidBucketItem {
    public GeneticOozeBucket( Properties properties) {
        super(AddonBlocks.GENETIC_OOZE_BLOCK.get(), SoundEvents.MUD_PLACE , properties);
    }
}
