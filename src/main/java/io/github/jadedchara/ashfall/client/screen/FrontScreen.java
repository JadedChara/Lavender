package io.github.jadedchara.ashfall.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jadedchara.ashfall.client.screen.widget.HandledButton;
import io.github.jadedchara.ashfall.client.screen.widget.HeadmateList;
import io.github.jadedchara.ashfall.common.cca.components.SystemSettingsComponent;
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
    HandledButton exitButton;

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
        exitButton = new HandledButton.Factory(
                Component.translatable("button.exit"),
                (button)->{Minecraft.getInstance().setScreen(null);},
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.exit"),x,y);}
        ).dimensions(this.width-75,5,70,20).build();

        //

        addRenderableWidget(box);
        addRenderableWidget(addButton);
        addRenderableWidget(exitButton);


    }

    @Override
    public void renderBackground(PoseStack poseStack) {
        //
    }

    @Override
    public void renderDirtBackground(int i) {
        //
    }

    @Override
    public void render(PoseStack context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }
}
