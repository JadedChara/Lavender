package io.github.jadedchara.ashfall.client;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.jadedchara.ashfall.client.render.block.PotentConstructRenderer;
import io.github.jadedchara.ashfall.client.render.block.SpatialAnchorRenderer;
import io.github.jadedchara.ashfall.client.screen.AddHeadmateScreen;
import io.github.jadedchara.ashfall.client.screen.FrontScreen;
import io.github.jadedchara.ashfall.common.networking.ClientPacketReceiver;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class AshfallClient implements ClientModInitializer {
    KeyMapping sysScreen;
    @Override
    public void onInitializeClient() {
        sysScreen = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.ashfall.system.screen",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KeyMapping.CATEGORY_MISC
        ));
        ClientPacketReceiver.init();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (sysScreen.consumeClick()) {
                client.player.displayClientMessage(Component.literal("Opening system tab..."), false);
                client.setScreen(new FrontScreen());
            }
        });

        //

        BlockEntityRendererRegistry.register(AshfallBlocks.POTENT_CONSTRUCT_BLOCK_ENTITY, PotentConstructRenderer::new);
        BlockEntityRendererRegistry.register(AshfallBlocks.SPATIAL_ANCHOR_BLOCK_ENTITY, SpatialAnchorRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(AshfallBlocks.SPATIAL_ANCHOR_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AshfallBlocks.POTENT_CONSTRUCT_BLOCK, RenderType.cutout());


    }
}
