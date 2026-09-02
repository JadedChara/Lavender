package io.github.jadedchara.lavender.client;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;

public class LavenderClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		for(Block block : LavenderBlocks.LAVENDER_BLOCKS){
			if(block.getName().getString().contains("stained_glass")){
				BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent());
			}
		}
	}
}