package io.github.jadedchara.lavender.mixin.common;


import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @Inject(method="isValid",at=@At("HEAD"),cancellable = true)
    void adjustValidity(BlockState blockState, CallbackInfoReturnable<Boolean> cir){
        Block b = blockState.getBlock();
        if(
                b instanceof BedBlock ||
                        b instanceof BannerBlock ||
                        b instanceof WallBannerBlock ||
                        b instanceof ShulkerBoxBlock
        ){
            cir.setReturnValue(true);
        }
    }

}
