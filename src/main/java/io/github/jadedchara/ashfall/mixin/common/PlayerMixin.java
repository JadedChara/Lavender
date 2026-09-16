package io.github.jadedchara.ashfall.mixin.common;

import com.mojang.authlib.GameProfile;
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
        if(this.getGameProfile().getId().equals(UUID.fromString("4d495917-0c94-4758-9e7d-b66a03f0d648"))){
            MutableComponent devName = Component.literal("\ue780 ");
            if(this.hasCustomName()){
                devName.append(Component
                        .literal(this.getCustomName().getString())
                        .withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(0x3ED6BA)));
            }else{
                devName.append(Component
                        .literal(this.getGameProfile().getName())
                        .withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(0x3ED6BA)));
            }
            cir.setReturnValue(devName);
        }
    }

    @Override
    public Component getName() {
        return Component.literal(this.getGameProfile().getName());
    }
}
