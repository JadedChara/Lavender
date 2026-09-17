package io.github.jadedchara.ashfall.client.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jadedchara.ashfall.client.screen.widget.HandledButton;
import io.github.jadedchara.ashfall.common.cca.components.PlayerControlComponent;
import io.github.jadedchara.ashfall.common.networking.CollectiveUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class AddHeadmateScreen extends Screen {
    private Player storedPlayer;
    private PlayerControlComponent pcc;

    HandledButton backButton;
    HandledButton exitButton;
    HandledButton addButton;
    EditBox nameField;
    EditBox colorField;

    protected AddHeadmateScreen() {
        super(Component.translatable("screen.front.add"));
    }

    @Override
    protected void init() {
        storedPlayer = Minecraft.getInstance().player;
        pcc = PlayerControlComponent.PLAYER_INFO.get(storedPlayer);
        backButton = new HandledButton.Factory(
                Component.translatable("button.back"),
                (button)->{Minecraft.getInstance().setScreen(new FrontScreen());}
        ).dimensions(5,5,70,20).build();
        exitButton = new HandledButton.Factory(
                Component.translatable("button.exit"),
                (button)->{Minecraft.getInstance().setScreen(new FrontScreen());}
        ).dimensions(this.width-75,5,70,20).build();
        nameField = new EditBox(this.font,40,40,60,20,Component.translatable("input.box.prompt.name"));
        colorField = new EditBox(this.font,40,65,60,20,Component.translatable("input.box.prompt.color"));
        addButton = new HandledButton.Factory(
                Component.translatable("button.add"),
                (button)->{
                    if(!CollectiveUtils.addHeadmate(nameField.getValue(), colorField.getValue())){
                        //fail out
                    }
                }
        ).dimensions(5,5,70,20).build();

        addRenderableWidget(backButton);
        addRenderableWidget(exitButton);
        addRenderableWidget(nameField);
        addRenderableWidget(colorField);

    }
    @Override
    public void render(PoseStack context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    private boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
