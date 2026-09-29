package io.github.jadedchara.ashfall.common.networking;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.client.screen.AddHeadmateScreen;
import io.github.jadedchara.ashfall.client.screen.FrontScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class CollectiveUtils {
    public static boolean addHeadmate(String name, String color){
        FriendlyByteBuf newHeadmate = PacketByteBufs.create();

        if(!name.equals("") && isInt(color)) {
            newHeadmate.writeUtf(name);
            newHeadmate.writeVarInt(Integer.decode(color));
            ClientPlayNetworking.send(Ashfall.id("add_headmate"), newHeadmate);
            if(Minecraft.getInstance().screen instanceof AddHeadmateScreen){
                Minecraft.getInstance().setScreen(new FrontScreen());
            }
            //Minecraft.getInstance().setScreen(new FrontScreen());
            return true;
        }
        return false;
    }
    public static boolean removeHeadmate(String name){
        FriendlyByteBuf oldHeadmate = PacketByteBufs.create();

        if(!name.equals("")) {
            oldHeadmate.writeUtf(name);
            ClientPlayNetworking.send(Ashfall.id("remove_headmate"), oldHeadmate);
            //Minecraft.getInstance().setScreen(new FrontScreen());
            return true;
        }
        return false;
    }
    public static boolean modifyHeadmate(String name,String newName, String newColor){
        FriendlyByteBuf updatedHeadmate = PacketByteBufs.create();

        if(!name.equals("") && !newName.equals("") && isInt(newColor)) {
            updatedHeadmate.writeUtf(name);
            updatedHeadmate.writeUtf(newName);
            updatedHeadmate.writeVarInt(Integer.decode(newColor));
            ClientPlayNetworking.send(Ashfall.id("modify_headmate"), updatedHeadmate);
            //Minecraft.getInstance().setScreen(new FrontScreen());
            return true;
        }
        return false;
    }
    public static boolean recolorHeadmate(String name, String newColor){
        FriendlyByteBuf updatedHeadmate = PacketByteBufs.create();

        if(!name.equals("") && isInt(newColor)) {
            updatedHeadmate.writeUtf(name);
            updatedHeadmate.writeVarInt(Integer.decode(newColor));
            ClientPlayNetworking.send(Ashfall.id("change_headmate_color"), updatedHeadmate);
            //Minecraft.getInstance().setScreen(new FrontScreen());
            return true;
        }
        return false;
    }
    public static boolean renameHeadmate(String name, String newName){
        FriendlyByteBuf updatedHeadmate = PacketByteBufs.create();

        if(!name.equals("") && !newName.equals("")) {
            updatedHeadmate.writeUtf(name);
            updatedHeadmate.writeUtf(newName);
            ClientPlayNetworking.send(Ashfall.id("change_headmate_hame"), updatedHeadmate);
            //Minecraft.getInstance().setScreen(new FrontScreen());
            return true;
        }
        return false;
    }
    public static boolean toggleFront(){
        FriendlyByteBuf enableFront = PacketByteBufs.create();
        enableFront.writeInt(0);
        ClientPlayNetworking.send(Ashfall.id("toggle_front"), enableFront);
        //Minecraft.getInstance().setScreen(new FrontScreen());
        return true;
    }
    public static boolean setFront(String name){
        FriendlyByteBuf fronter = PacketByteBufs.create();

        if(!name.equals("")) {
            fronter.writeUtf(name);
            ClientPlayNetworking.send(Ashfall.id("set_front"), fronter);
            //Minecraft.getInstance().setScreen(new FrontScreen());
            return true;
        }
        return false;
    }
    public static boolean clearSystem(){
        FriendlyByteBuf fullWipe = PacketByteBufs.create();
        fullWipe.writeInt(0);
        ClientPlayNetworking.send(Ashfall.id("clear_system"), fullWipe);
        //Minecraft.getInstance().setScreen(new FrontScreen());
        return true;
    }



    public static boolean isInt(String s){
        try{
            Integer.decode(s).intValue();
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
