package io.github.jadedchara.ashfall;


import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import io.github.jadedchara.ashfall.common.registry.AshfallItems;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ashfall implements ModInitializer {
        public static final String MOD_ID = "ashfall";
        public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

        @Override
        public void onInitialize() {

            AshfallSounds.init();
            AshfallBlocks.init();
            AshfallItems.init();
            LOGGER.info("Booting sub-mod: [ASHFALL] from [LAVENDER]");
            LOGGER.info("Hello Fabric/Quilt world from Ashfall!");
        }
        public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
