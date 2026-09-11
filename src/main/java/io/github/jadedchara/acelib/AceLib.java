package io.github.jadedchara.acelib;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AceLib implements ModInitializer {
    public static final String MOD_ID = "acelib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Booting sub-mod: [ACELIB] from [LAVENDER]");
        LOGGER.info("Hello Fabric/Quilt world from AceLib!");
    }
    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}