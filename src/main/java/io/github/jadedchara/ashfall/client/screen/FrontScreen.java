package io.github.jadedchara.ashfall.client.screen;

import io.github.jadedchara.ashfall.client.screen.widget.HandledButton;
import io.github.jadedchara.ashfall.common.cca.components.PlayerControlComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class FrontScreen extends Screen {
    private Player storedPlayer;
    private PlayerControlComponent pcc;
    ContainerObjectSelectionList box;

    public FrontScreen() {
        super(Component.translatable("screen.front.main"));

    }

    @Override
    protected void init() {
        storedPlayer = Minecraft.getInstance().player;
        pcc = PlayerControlComponent.PLAYER_INFO.get(storedPlayer);
        int yc = 0;
        box = new ContainerObjectSelectionList(
                Minecraft.getInstance(),
                5,
                5,
                this.width-5,
                this.width-5,
                20
                ) {
            @Override
            public boolean changeFocus(boolean bl) {
                return super.changeFocus(bl);
            }
        };
        pcc.getHeadmates().keySet().forEach(h->{

        });


    }
}
