package io.github.jadedchara.lavender;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.registry.LavenderItems;
import io.github.jadedchara.lavender.common.registry.LavenderSounds;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import net.minecraft.sounds.SoundEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lavender implements ModInitializer {
	public static final String MOD_ID = "lavender";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LavenderItems.init();
		LavenderBlocks.init();
		LavenderSounds.init();
		LOGGER.info("Hello Fabric/Quilt world from Lavender!");
	}
	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
