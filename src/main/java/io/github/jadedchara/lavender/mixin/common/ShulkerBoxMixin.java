package io.github.jadedchara.lavender.mixin.common;


import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxMixin {
    @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
            method = "getBlockByColor",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private static void getAppendedColors(DyeColor dyeColor, CallbackInfoReturnable<Block> cir){
        for (LavenderColors color : LavenderColors.values()) {
            if (dyeColor == color.get()) {
                for (Block block : LavenderBlocks.LAVENDER_BLOCKS) {
                    if (block.getName().toString() == color.getName() + "_shulker_box") {
                       cir.setReturnValue(block);
                    }
                }
            }
        }

    }
}
