package io.github.jadedchara.ashfall.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jadedchara.ashfall.client.screen.widget.HandledButton;
import io.github.jadedchara.ashfall.common.cca.components.SystemSettingsComponent;
import io.github.jadedchara.ashfall.common.networking.CollectiveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class AddHeadmateScreen extends Screen {
    private Player storedPlayer;
    private SystemSettingsComponent pcc;

    HandledButton backButton;
    HandledButton exitButton;
    HandledButton addButton;
    EditBox nameField;
    EditBox colorField;

    public AddHeadmateScreen() {
        super(Component.translatable("screen.front.add"));
    }

    @Override
    protected void init() {
        storedPlayer = Minecraft.getInstance().player;
        pcc = SystemSettingsComponent.PLAYER_INFO.get(storedPlayer);
        backButton = new HandledButton.Factory(
                Component.translatable("button.back"),
                (button)->{Minecraft.getInstance().setScreen(new FrontScreen());},
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.back"),x,y);}
        ).dimensions(5,5,70,20).build();
        exitButton = new HandledButton.Factory(
                Component.translatable("button.exit"),
                (button)->{Minecraft.getInstance().setScreen(new FrontScreen());},
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.exit"),x,y);}
        ).dimensions(this.width-75,5,70,20).build();
        nameField = new EditBox(this.font,this.width/2-50,40,100,20,Component.translatable("input.box.prompt.name"));
        nameField.setSuggestion("Name...");
        colorField = new EditBox(this.font,this.width/2-50,65,100,20,Component.translatable("input.box.prompt.color"));
        colorField.setMaxLength(8);
        colorField.setSuggestion("0x000000");

        addButton = new HandledButton.Factory(
                Component.translatable("button.add"),
                (button)->{
                    if(CollectiveUtils.addHeadmate(nameField.getValue(), colorField.getValue())){
                    }
                },
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.add"),x,y);}
        ).dimensions(this.width/2-50,90,100,20).build();
        addButton.setActivity(false);

        addRenderableWidget(backButton);
        addRenderableWidget(exitButton);
        addRenderableWidget(nameField);
        addRenderableWidget(colorField);
        addRenderableWidget(addButton);

    }
    @Override
    public void render(PoseStack context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        this.addButton.setActivity(CollectiveUtils.isInt(this.colorField.getValue()) && !this.nameField.getValue().isEmpty());
        super.tick();
    }
}
