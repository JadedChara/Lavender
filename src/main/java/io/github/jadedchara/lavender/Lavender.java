package io.github.jadedchara.lavender;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.registry.LavenderItems;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lavender implements ModInitializer {
	public static final String MOD_ID = "lavender";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		//LavenderColors.init();
		LavenderItems.init();
		LavenderBlocks.init();

		LOGGER.info("Hello Fabric/Quilt world from Lavender!");
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
