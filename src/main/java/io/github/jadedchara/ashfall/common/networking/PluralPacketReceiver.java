package io.github.jadedchara.ashfall.common.networking;

import io.github.jadedchara.ashfall.common.cca.components.PlayerControlComponent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class PluralPacketReceiver {
    public static void init(){
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:addHeadmate"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    int color = buf.readInt();
                    PlayerControlComponent.PLAYER_INFO.get(sp).addHeadmate(name,color);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:removeHeadmate"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();

                    PlayerControlComponent.PLAYER_INFO.get(sp).removeHeadmate(name);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:modifyHeadmate"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    String newName = buf.readUtf();
                    int newColor = buf.readInt();

                    PlayerControlComponent.PLAYER_INFO.get(sp).modifyHeadmate(name,newName,newColor);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:changeHeadmateColor"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    int newColor = buf.readInt();

                    PlayerControlComponent.PLAYER_INFO.get(sp).modifyHeadmateColor(name,newColor);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:changeHeadmateName"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    String newName = buf.readUtf();

                    PlayerControlComponent.PLAYER_INFO.get(sp).modifyHeadmateName(name,newName);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:toggleFront"),
                (server,sp,c,buf,sender)->{
                    PlayerControlComponent.PLAYER_INFO.get(sp).toggleShowFront();
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:setFront"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    PlayerControlComponent.PLAYER_INFO.get(sp).setHeadmate(name);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                new ResourceLocation("ashfall:clearSystem"),
                (server,sp,c,buf,sender)->{
                    PlayerControlComponent.PLAYER_INFO.get(sp).clearSystem();
                }
        );
    }
}
