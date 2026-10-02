package io.github.jadedchara.ashfall.client;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.jadedchara.ashfall.client.screen.AddHeadmateScreen;
import io.github.jadedchara.ashfall.client.screen.FrontScreen;
import io.github.jadedchara.ashfall.common.networking.ClientPacketReceiver;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
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

    }
}
