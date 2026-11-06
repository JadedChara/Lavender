package io.github.jadedchara.ashfall.client;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.jadedchara.ashfall.client.render.block.ControlAnchorRenderer;
import io.github.jadedchara.ashfall.client.render.block.PotentConstructRenderer;
import io.github.jadedchara.ashfall.client.render.block.SpatialAnchorRenderer;
import io.github.jadedchara.ashfall.client.render.block.UnstableConstructRenderer;
import io.github.jadedchara.ashfall.client.render.block.model.ControlAnchorModel;
import io.github.jadedchara.ashfall.client.render.block.model.item.ControlAnchorItemModel;
import io.github.jadedchara.ashfall.client.render.block.model.item.SpatialAnchorItemModel;
import io.github.jadedchara.ashfall.client.render.block.model.item.PotentConstructItemModel;
import io.github.jadedchara.ashfall.client.render.block.model.item.UnstableConstructItemModel;
import io.github.jadedchara.ashfall.client.screen.FrontScreen;
import io.github.jadedchara.ashfall.common.networking.ClientPacketReceiver;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

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
        BlockEntityRendererRegistry.register(AshfallBlocks.UNSTABLE_CONSTRUCT_BLOCK_ENTITY, UnstableConstructRenderer::new);
        BlockEntityRendererRegistry.register(AshfallBlocks.SPATIAL_ANCHOR_BLOCK_ENTITY, SpatialAnchorRenderer::new);
        BlockEntityRendererRegistry.register(AshfallBlocks.CONTROL_ANCHOR_BLOCK_ENTITY, ControlAnchorRenderer::new);

        BlockRenderLayerMap.INSTANCE.putBlock(AshfallBlocks.UNSTABLE_CONSTRUCT_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AshfallBlocks.POTENT_CONSTRUCT_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AshfallBlocks.SPATIAL_ANCHOR_BLOCK, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(AshfallBlocks.CONTROL_ANCHOR_BLOCK, RenderType.cutout());


        BuiltinItemRendererRegistry.INSTANCE.register(AshfallBlocks.UNSTABLE_CONSTRUCT_BLOCK.asItem(),
                new GeoItemRenderer<>(new UnstableConstructItemModel()));
        BuiltinItemRendererRegistry.INSTANCE.register(AshfallBlocks.POTENT_CONSTRUCT_BLOCK.asItem(),
                new GeoItemRenderer<>(new PotentConstructItemModel()));
        BuiltinItemRendererRegistry.INSTANCE.register(AshfallBlocks.SPATIAL_ANCHOR_BLOCK.asItem(),
                new GeoItemRenderer<>(new SpatialAnchorItemModel()));
        BuiltinItemRendererRegistry.INSTANCE.register(AshfallBlocks.CONTROL_ANCHOR_BLOCK.asItem(),
                new GeoItemRenderer<>(new ControlAnchorItemModel()));

    }
}
