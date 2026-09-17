package io.github.jadedchara.ashfall.client.screen.widget;

import io.github.jadedchara.ashfall.common.cca.components.PlayerControlComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.network.chat.Component;

public class HeadmateList extends ContainerObjectSelectionList {
    public HeadmateList(PlayerControlComponent pcc, Minecraft client, int i, int j, int k, int l, int m) {
        super(client, i, j, k, l, m);
        int yc = 0;
        pcc.getHeadmates().keySet().forEach(h->{
            //this.addEntry(new HandledButton.Factory());
        });
    }
    private class FronterSelectButton extends HandledButton{
        public FronterSelectButton(int x, int y, String headmate) {
            super(x, y, 100, 20, Component.translatable("front.select"), (b)->{}, null);
        }
    }
}
