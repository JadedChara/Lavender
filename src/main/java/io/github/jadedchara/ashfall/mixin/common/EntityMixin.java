package io.github.jadedchara.ashfall.mixin.common;

import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Level getLevel();

    /*@Inject(method="setPos(DDD)V",at=@At("HEAD"),cancellable = true)
    public void setPosBundle(double d, double e, double f, CallbackInfo ci){
        try{
            if(!this.getLevel().isClientSide()){
                AnchorPositionComponent apc = AnchorPositionComponent.ANCHORS.maybeGet(this.getLevel()).get();
                if(!apc.warpAllowed()){
                    ci.cancel();
                }
            }
        }catch(Exception err){
            //ignore in case of world shite.
        }
    }
    @Inject(method="setPosRaw",at=@At("HEAD"),cancellable = true)
    public void setPosButWeird(double d, double e, double f, CallbackInfo ci){
        try{
            if(!this.getLevel().isClientSide()){
                AnchorPositionComponent apc = AnchorPositionComponent.ANCHORS.maybeGet(this.getLevel()).get();
                if(!apc.warpAllowed()){
                    ci.cancel();
                }
            }
        }catch(Exception err){
            //ignore in case of world shite.
        }
    }
    @Inject(method="setPos(Lnet/minecraft/world/phys/Vec3;)V",at=@At("TAIL"),cancellable = true)
    public void setPosVec(Vec3 v, CallbackInfo ci){
        try{
            if(!this.getLevel().isClientSide()){
                AnchorPositionComponent apc = AnchorPositionComponent.ANCHORS.maybeGet(this.getLevel()).get();
                if(!apc.warpAllowed()){
                    ci.cancel();
                }
            }
        }catch(Exception err){
            //ignore in case of world shite.
        }
    }*/
    @Inject(method="changeDimension",at=@At("HEAD"),cancellable = true)
    public void surpressDimensions(ServerLevel serverLevel, CallbackInfoReturnable<Entity> cir){
        try{
            if(!this.getLevel().isClientSide()){
                AnchorPositionComponent apc = AnchorPositionComponent.ANCHORS.maybeGet(this.getLevel()).get();
                if(!apc.warpAllowed()){
                    cir.cancel();
                }
            }
        }catch(Exception err){
            //ignore in case of world shite.
        }
    }
    @Inject(method="teleportTo",at=@At("HEAD"),cancellable = true)
    public void tp(double d, double e, double f, CallbackInfo ci){
        try{
            if(!this.getLevel().isClientSide()){
                AnchorPositionComponent apc = AnchorPositionComponent.ANCHORS.maybeGet(this.getLevel()).get();
                if(!apc.warpAllowed()){
                    ci.cancel();
                }
            }
        }catch(Exception err){
            //ignore in case of world shite.
        }
    }
    @Inject(method="teleportToWithTicket",at=@At("HEAD"),cancellable = true)
    public void tpWithTicket(double d, double e, double f, CallbackInfo ci){
        try{
            if(!this.getLevel().isClientSide()){
                AnchorPositionComponent apc = AnchorPositionComponent.ANCHORS.maybeGet(this.getLevel()).get();
                if(!apc.warpAllowed()){
                    ci.cancel();
                }
            }
        }catch(Exception err){
            //ignore in case of world shite.
        }
    }
}
