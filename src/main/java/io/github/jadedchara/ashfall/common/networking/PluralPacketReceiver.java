package io.github.jadedchara.ashfall.common.networking;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.cca.components.SystemSettingsComponent;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

public class PluralPacketReceiver {
    private static FriendlyByteBuf pointblank = PacketByteBufs.create();
    public static void init(){
        pointblank.writeInt(0);
        //---------------

        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("add_headmate"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    int color = buf.readVarInt();
                    SystemSettingsComponent.PLAYER_INFO.get(sp).addHeadmate(name,color);
                    ServerPlayNetworking.send(sp,Ashfall.id("set_to_front_screen"),pointblank);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("remove_headmate"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    SystemSettingsComponent.PLAYER_INFO.get(sp).removeHeadmate(name);
                    ServerPlayNetworking.send(sp,Ashfall.id("set_to_front_screen"),pointblank);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("modify_headmate"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    String newName = buf.readUtf();
                    int newColor = buf.readVarInt();

                    SystemSettingsComponent.PLAYER_INFO.get(sp).modifyHeadmate(name,newName,newColor);
                    ServerPlayNetworking.send(sp,Ashfall.id("set_to_front_screen"),pointblank);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("change_headmate_color"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    int newColor = buf.readVarInt();

                    SystemSettingsComponent.PLAYER_INFO.get(sp).modifyHeadmateColor(name,newColor);
                    ServerPlayNetworking.send(sp,Ashfall.id("set_to_front_screen"),pointblank);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("change_headmate_name"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    String newName = buf.readUtf();

                    SystemSettingsComponent.PLAYER_INFO.get(sp).modifyHeadmateName(name,newName);
                    ServerPlayNetworking.send(sp,Ashfall.id("set_to_front_screen"),pointblank);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("toggle_front"),
                (server,sp,c,buf,sender)->{
                    SystemSettingsComponent.PLAYER_INFO.get(sp).toggleShowFront();
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("set_front"),
                (server,sp,c,buf,sender)->{
                    String name = buf.readUtf();
                    SystemSettingsComponent.PLAYER_INFO.get(sp).setHeadmate(name);
                    ServerPlayNetworking.send(sp,Ashfall.id("set_to_front_screen"),pointblank);
                }
        );
        ServerPlayNetworking.registerGlobalReceiver(
                Ashfall.id("clear_system"),
                (server,sp,c,buf,sender)->{
                    SystemSettingsComponent.PLAYER_INFO.get(sp).clearSystem();
                }
        );
    }
}
