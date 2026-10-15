package io.github.jadedchara.ashfall.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderpearlItem.class)
public class PearlMixin {
    @Inject(method="use",at=@At("HEAD"),cancellable = true)
    public void chargeConstruct(Level l, Player p, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        BlockPos target = l.clip(
                new ClipContext(
                        p.getEyePosition(),
                        p.getEyePosition().add(p.getLookAngle().scale(3)),
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        p
                )
        ).getBlockPos();

    }
}
