package io.github.jadedchara.ashfall.client.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ReaperScreen extends Screen {
    protected ReaperScreen(Component component) {
        super(Component.translatable("screen.reaper.main.controls"));
    }
}
