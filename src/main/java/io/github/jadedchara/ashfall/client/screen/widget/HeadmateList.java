package io.github.jadedchara.ashfall.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jadedchara.ashfall.client.screen.EditHeadmateScreen;
import io.github.jadedchara.ashfall.client.screen.FrontScreen;
import io.github.jadedchara.ashfall.common.cca.components.SystemSettingsComponent;
import io.github.jadedchara.ashfall.common.networking.CollectiveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

import java.util.List;

public class HeadmateList extends ContainerObjectSelectionList<HeadmateList.HeadmateEntry> {
    SystemSettingsComponent stored;
    int maxWidth = 0;
    public HeadmateList(SystemSettingsComponent pcc, Minecraft client, int i, int j, int k, int l, int m) {
        super(client, i, j, k, l, m);
        this.stored = pcc;
        for(String val : stored.getHeadmates().keySet()){
            if(client.font.width(FormattedText.of(val, Style.EMPTY.withColor(stored.getHeadmates().get(val))))>maxWidth) maxWidth =
                    client.font.width(val);
        }
        pcc.getHeadmates().keySet().forEach(h->{
            this.addEntry(new HeadmateEntry(h,pcc.getHeadmates().get(h)));
        });
    }

    @Override
    public int getRowWidth() {
        return 200;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
        super.render(poseStack, i, j, f);
    }

    public class FronterSelectButton extends HandledButton{
        public FronterSelectButton(int x, int y, String headmate,int co) {
            super(x, y, 100, 20, Component.translatable("headmate.set.front"), (b)->{
                CollectiveUtils.setFront(headmate);
                Minecraft.getInstance().setScreen(null);
            }, (button,bm,bx,by)->{});
            this.active = (!HeadmateList.this.stored.getFrontName().equals(headmate));
        }
    }

    public class HeadmateEntry extends Entry<HeadmateEntry>{
        String headmateName;
        int headmateColor;
        public FronterSelectButton setFront;
        public HandledButton editHeadmate;
        public HandledButton removeHeadmate;
        private final List<GuiEventListener> yeetableChildren;
        private final List<NarratableEntry> mehs;

        HeadmateEntry(String h, int c){
            this.headmateName = h;
            this.headmateColor = c;

            setFront = new FronterSelectButton(0,0,h,c);
            editHeadmate = new HandledButton(
                    105,
                    0,
                    50,
                    20,
                    Component.translatable("headmate.edit"),
                    (b)->{
                        Minecraft.getInstance().setScreen(new EditHeadmateScreen(this.headmateName,this.headmateColor));
                    },
                    (button,bm,bx,by)->{});
            removeHeadmate = new HandledButton(
                    160,
                    0,
                    60,
                    20,
                    Component.translatable("headmate.remove"),
                    (b)->{
                        CollectiveUtils.removeHeadmate(headmateName);
                        Minecraft.getInstance().setScreen(new FrontScreen());
                        },
                    (button,bm,bx,by)->{});
            this.yeetableChildren = List.of(this.setFront,this.editHeadmate,this.removeHeadmate);
            this.mehs = List.of(this.setFront,this.editHeadmate,this.removeHeadmate);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.mehs;
        }

        @Override
        public void render(PoseStack poseStack, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
            this.setFront.y = j;
            this.setFront.x = k+HeadmateList.this.maxWidth+7;
            this.editHeadmate.y=j;
            this.editHeadmate.x = this.setFront.x+105;
            this.removeHeadmate.y = j;
            this.removeHeadmate.x = this.editHeadmate.x+55;
            fill(poseStack,k-5,j,k-4,j+20,0xFFDDDDDD);
            fill(poseStack,k+HeadmateList.this.maxWidth+3,j,k+HeadmateList.this.maxWidth+4,j+20,0xFFDDDDDD);
            fill(poseStack,k-5,j,k+HeadmateList.this.maxWidth+4,j+1,0xFFDDDDDD);
            fill(poseStack,k-5,j+19,k+HeadmateList.this.maxWidth+4,j+20,0xFFDDDDDD);
            drawString(
                    poseStack,
                    Minecraft.getInstance().font,
                    Component.literal(this.headmateName),
                    k,
                    j+6,
                    this.headmateColor);
            this.setFront.render(poseStack, n, o, f);
            this.editHeadmate.render(poseStack, n, o, f);
            this.removeHeadmate.render(poseStack, n, o, f);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return this.yeetableChildren;
        }
    }

    @Override
    protected void renderBackground(PoseStack poseStack) {
        //
    }

    @Override
    protected void renderDecorations(PoseStack poseStack, int i, int j) {
        //
    }
}
