package io.github.jadedchara.ashfall.common.networking;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.client.screen.FrontScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;

public class ClientPacketReceiver {
    public static void init(){
        ClientPlayNetworking.registerGlobalReceiver(Ashfall.id("set_to_front_screen"),(client,listener,buf,sender)->{
            client.execute(()->client.setScreen(new FrontScreen()));
        });
    }
}
