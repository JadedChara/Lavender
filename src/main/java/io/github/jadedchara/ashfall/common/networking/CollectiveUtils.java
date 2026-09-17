package io.github.jadedchara.ashfall.common.networking;

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
            newHeadmate.writeVarInt(Integer.parseInt(color));
            ClientPlayNetworking.send(new ResourceLocation("ashfall:addHeadmate"), newHeadmate);
            Minecraft.getInstance().setScreen(new FrontScreen());
            return true;
        }
        return false;
    }



    private static boolean isInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
