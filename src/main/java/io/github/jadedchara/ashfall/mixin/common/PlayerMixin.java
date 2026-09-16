package io.github.jadedchara.ashfall.mixin.common;

import com.mojang.authlib.GameProfile;
import io.github.jadedchara.ashfall.common.cca.components.PlayerControlComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerMixin implements Nameable {

    @Shadow public abstract GameProfile getGameProfile();

    @Inject(method="getName",at=@At("TAIL"),cancellable = true)
    public void getAlteredName(CallbackInfoReturnable<Component> cir){
        if (this.hasCustomName()){
            cir.setReturnValue(this.getCustomName());
            //if(this.)
        }
        else{
            cir.setReturnValue(Component.literal(this.getGameProfile().getName()));
        }
        try{
            PlayerControlComponent pcc = PlayerControlComponent.PLAYER_INFO.get(this);
            if (pcc.hasLoreName()) {
                cir.setReturnValue(Component.literal(pcc.getCustomName()).withStyle(Style.EMPTY.withColor(0x00c5cd)));
            } else if (pcc.canShowFront())
                cir.setReturnValue(Component.literal(pcc.getFrontName()).withStyle(Style.EMPTY.withColor(pcc.getFrontColor())));
        }catch(Exception e){
            //ignore for now
        }
    }

    @Override
    public Component getName() {
        return Component.literal(this.getGameProfile().getName());
    }
}
