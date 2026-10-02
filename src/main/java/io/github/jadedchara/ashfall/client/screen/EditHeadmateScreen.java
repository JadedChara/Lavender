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

public class EditHeadmateScreen extends Screen {

    String storedName;
    String storedColor;
    HandledButton backButton;
    HandledButton exitButton;
    HandledButton editButton;
    EditBox nameField;
    EditBox colorField;

    public EditHeadmateScreen(String name, int color) {
        super(Component.translatable("screen.front.add"));
        this.storedName = name;
        this.storedColor = String.format("0x%06x",color & 0xFFFFFF);
    }

    @Override
    protected void init() {
        backButton = new HandledButton.Factory(
                Component.translatable("button.back"),
                (button)->{Minecraft.getInstance().setScreen(new FrontScreen());},
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.back"),x,y);}
        ).dimensions(5,5,70,20).build();
        exitButton = new HandledButton.Factory(
                Component.translatable("button.exit"),
                (button)->{Minecraft.getInstance().setScreen(null);},
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.exit"),x,y);}
        ).dimensions(this.width-75,5,70,20).build();
        nameField = new EditBox(this.font,this.width/2-50,40,100,20,Component.translatable("input.box.prompt.name"));
        colorField = new EditBox(this.font,this.width/2-50,65,100,20,Component.translatable("input.box.prompt.color"));
        colorField.setMaxLength(8);

        editButton = new HandledButton.Factory(
                Component.translatable("button.update"),
                (button)->{
                    String color = colorField.getValue();
                    if(!color.startsWith("0x")){
                        color = "0x"+ color;
                    }
                    if(!this.storedName.equals(nameField.getValue()) && !this.storedColor.equals(colorField.getValue())){
                        CollectiveUtils.modifyHeadmate(this.storedName,nameField.getValue(), color);
                    }else if(!this.storedName.equals(nameField.getValue())){
                        CollectiveUtils.renameHeadmate(this.storedName,nameField.getValue());
                    }else if(!this.storedColor.equals(colorField.getValue())){
                        CollectiveUtils.recolorHeadmate(this.storedName,colorField.getValue());
                    }else{
                        Minecraft.getInstance().setScreen(new FrontScreen());
                    }

                },
                (button,m,x,y)->{this.renderTooltip(m,Component.translatable("button.update"),x,y);}
        ).dimensions(this.width/2-50,90,100,20).build();
        editButton.active = false;
        nameField.setValue(this.storedName);
        colorField.setValue(this.storedColor);

        addRenderableWidget(backButton);
        addRenderableWidget(exitButton);
        addRenderableWidget(nameField);
        addRenderableWidget(colorField);
        addRenderableWidget(editButton);

    }
    @Override
    public void render(PoseStack context, int mouseX, int mouseY, float delta) {
        String s = this.colorField.getValue();
        if(s.length() == 6 || s.length() == 8){
            try{
                if(s.startsWith("0x") && s.length() == 8){
                    s = "0xFF" + s.substring(2);
                }else{
                    s = "0xFF" + s;
                }
                fill(context, this.width/2+55,65,this.width/2+75,85,
                        (int)Long.decode(s).longValue());
            }catch(Exception e){
                fill(context, this.width/2+55,65,this.width/2+75,85,0xFF000000);
            }
        }else{
            fill(context, this.width/2+55,65,this.width/2+75,85,0xFF000000);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        this.editButton.active = (
                CollectiveUtils.isInt(this.colorField.getValue())
                        && !this.nameField.getValue().isEmpty()
                        && this.colorField.getValue().startsWith("0x")
                        && this.colorField.getValue().length() == 8
        ) ||
                (
                        CollectiveUtils.isInt("0x"+this.colorField.getValue())
                                && !this.nameField.getValue().isEmpty()
                                && this.colorField.getValue().length() == 6
                )
        ;
        super.tick();
    }
}
