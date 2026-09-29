package io.github.jadedchara.ashfall.client.screen;

import io.github.jadedchara.ashfall.common.cca.components.SystemSettingsComponent;
import io.github.jadedchara.ashfall.common.cca.components.ScytheComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ReaperScreen extends Screen {

    private Player storedPlayer;
    private SystemSettingsComponent pcc;
    private ScytheComponent sc;

    protected ReaperScreen() {
        super(Component.translatable("screen.reaper.main.controls"));

    }

    @Override
    protected void init() {

    }
}
