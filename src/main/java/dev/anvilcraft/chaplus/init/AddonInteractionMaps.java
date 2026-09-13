package dev.anvilcraft.chaplus.init;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;

public class AddonInteractionMaps {
    public static final CauldronInteraction.InteractionMap GENETIC_OOZE = CauldronInteraction.newInteractionMap("genetic_ooze");

    public static void init() {
        var map = GENETIC_OOZE.map();
        map.put(
            Items.BUCKET,
            (state, level, pos, player, hand, stack) -> CauldronInteraction.fillBucket(
                state,
                level,
                pos,
                player,
                hand,
                stack,
                AddonItems.GENETIC_OOZE_BUCKET.asStack(),
                (s) -> AddonBlocks.GENETIC_OOZE_CAULDRON.get().isFull(s),
                SoundEvents.BUCKET_FILL
            )
        );
        var emptyMap = CauldronInteraction.EMPTY.map();
        emptyMap.put(
            AddonItems.GENETIC_OOZE_BUCKET.get(),
            (state, level, pos, player, hand, stack) -> CauldronInteraction.emptyBucket(
                level,
                pos,
                player,
                hand,
                stack,
                AddonBlocks.GENETIC_OOZE_CAULDRON.get().fullFilled(),
                SoundEvents.BUCKET_EMPTY
            )
        );
    }
}
