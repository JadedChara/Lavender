package io.github.jadedchara.ashfall.client.screen.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationSupplier;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class HandledButton extends Button {
    public HandledButton(int x, int y, int w, int h, Component c, OnPress boop, OnTooltip tt) {
        super(x, y, w, h, c, boop, tt);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory{
        private final Component name;
        private final OnPress func;
        @Nullable
        private OnTooltip tt;
        private int x;
        private int y;
        private int width = 16;
        private int height = 16;
        public Factory(Component message, OnPress onPress) {
            this.name = message;
            this.func = onPress;
        }

        public Factory position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Factory width(int width) {
            this.width = width;
            return this;
        }

        public Factory size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Factory dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public HandledButton build() {
            HandledButton b = new HandledButton(this.x, this.y, this.width, this.height, this.name, this.func,
                    this.tt);
            b.setMessage(this.name);
            return b;
        }
    }
}
