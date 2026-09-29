package io.github.jadedchara.ashfall.client.screen;

import io.github.jadedchara.ashfall.client.screen.widget.HandledButton;
import io.github.jadedchara.ashfall.client.screen.widget.HeadmateList;
import io.github.jadedchara.ashfall.common.cca.components.SystemSettingsComponent;
import io.github.jadedchara.ashfall.common.networking.CollectiveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class FrontScreen extends Screen {
    private Player storedPlayer;
    private SystemSettingsComponent pcc;
    ContainerObjectSelectionList box;
    HandledButton addButton;

    public FrontScreen() {
        super(Component.translatable("screen.front.main"));

    }

    @Override
    protected void init() {
        storedPlayer = Minecraft.getInstance().player;
        pcc = SystemSettingsComponent.PLAYER_INFO.get(storedPlayer);
        int yc = 0;
        box = new HeadmateList(pcc,Minecraft.getInstance(),this.width,this.height,30,this.height-10,25);
        addButton = new HandledButton.Factory(
                Component.translatable("button.add"),
                (button)->{
                    Minecraft.getInstance().setScreen(new AddHeadmateScreen());
                },
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.add"),x,y);}
        ).dimensions(this.width/2-50,5,100,20).build();


        //

        addRenderableWidget(box);
        addRenderableWidget(addButton);


    }
}
