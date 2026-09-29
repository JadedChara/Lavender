package io.github.jadedchara.ashfall.client.screen.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.jadedchara.ashfall.client.screen.FrontScreen;
import io.github.jadedchara.ashfall.common.cca.components.SystemSettingsComponent;
import io.github.jadedchara.ashfall.common.networking.CollectiveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class HeadmateList extends ContainerObjectSelectionList<HeadmateList.HeadmateEntry> {
    SystemSettingsComponent stored;

    public HeadmateList(SystemSettingsComponent pcc, Minecraft client, int i, int j, int k, int l, int m) {
        super(client, i, j, k, l, m);
        int yc = 0;
        this.stored = pcc;
        pcc.getHeadmates().keySet().forEach(h->{
            this.addEntry(new HeadmateEntry(h,pcc.getHeadmates().get(h)));
        });
    }

    @Override
    public int getRowWidth() {
        return 200;
    }

    public static class FronterSelectButton extends HandledButton{
        public FronterSelectButton(int x, int y, String headmate) {
            super(x, y, 100, 20, Component.translatable("front.select",headmate), (b)->{
                CollectiveUtils.setFront(headmate);
                Minecraft.getInstance().setScreen(null);
            }, (button,bm,bx,by)->{});
        }
    }

    public class HeadmateEntry extends Entry<HeadmateEntry>{
        String headmateName;
        int headmateColor;
        public FronterSelectButton setFront;
        public HandledButton removeHeadmate;
        private final List<GuiEventListener> yeetableChildren;
        private final List<NarratableEntry> mehs;

        HeadmateEntry(String h, int c){
            this.headmateName = h;
            this.headmateColor = c;

            setFront = new FronterSelectButton(0,0,h);
            removeHeadmate = new HandledButton(
                    105,
                    0,
                    70,
                    20,
                    Component.translatable("headmate.remove"),
                    (b)->{
                        CollectiveUtils.removeHeadmate(headmateName);
                        Minecraft.getInstance().setScreen(new FrontScreen());
                        },
                    (button,bm,bx,by)->{});
            this.yeetableChildren = List.of(this.setFront, this.removeHeadmate);
            this.mehs = List.of(this.setFront,this.removeHeadmate);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.mehs;
        }

        @Override
        public void render(PoseStack poseStack, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
            this.setFront.y = j;
            this.setFront.x = k;
            this.removeHeadmate.y = j;
            this.removeHeadmate.x = k+105;
            this.setFront.render(poseStack, n, o, f);
            this.removeHeadmate.render(poseStack, n, o, f);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return this.yeetableChildren;
        }
    }

    @Override
    public void setRenderBackground(boolean bl) {
        super.setRenderBackground(false);
    }
}
