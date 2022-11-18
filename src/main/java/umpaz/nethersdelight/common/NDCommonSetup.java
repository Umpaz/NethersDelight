package umpaz.nethersdelight.common;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import umpaz.nethersdelight.common.world.NDGeneration;

public class NDCommonSetup {

    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerCompostables();
            NDGeneration.registerNDGeneration();
        });
    }

    public static void registerCompostables() {
        //ComposterBlock.COMPOSTABLES.put(ModItems.TREE_BARK.get(), 0.3F);
    }
}
