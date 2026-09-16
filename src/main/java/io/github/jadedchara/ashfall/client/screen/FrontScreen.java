package io.github.jadedchara.ashfall.client.screen;

import io.github.jadedchara.ashfall.common.cca.components.PlayerControlComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class FrontScreen extends Screen {
    private Player storedPlayer;
    private PlayerControlComponent pcc;

    protected FrontScreen() {
        super(Component.translatable("screen.front.main"));

    }

    @Override
    protected void init() {
        storedPlayer = Minecraft.getInstance().player;
        pcc = PlayerControlComponent.PLAYER_INFO.get(storedPlayer);


    }
}
