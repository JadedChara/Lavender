package io.github.jadedchara.lavender.mixin.common;

import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.world.item.DyeColor;

import net.minecraft.world.level.material.MaterialColor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Debug(export = true)
@Mixin(value = DyeColor.class)
public abstract class DyeColorMixin {
    @Mutable
    @Shadow
    @Final
    private static DyeColor[] $VALUES;

    @Invoker("<init>")
    private static DyeColor lavender$init(String dyeId, int ordinal, int id, String name, int entityColor,
                                      MaterialColor mapColor, int fireworkColor, int signColor){
        throw new AssertionError();

    }

    @Inject(method="<clinit>", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/item/DyeColor;$VALUES:[Lnet/minecraft/world/item/DyeColor;",
            shift = At.Shift.AFTER))
    private static void createDye(CallbackInfo ci){
        ArrayList<DyeColor> dyes = new ArrayList<>(Arrays.asList($VALUES));
        //int ord = dyes.get(dyes.size()-1).ordinal();
        //int i = 1;

        for (LavenderColors color : LavenderColors.values()) {
            dyes.add(lavender$init(color.name(), color.getId(), color.getId(), color.getName(), color.getColor(),
                    color.getMapColor(), color.getFireworkColor(), color.getTextColor()));

        }
        $VALUES = dyes.toArray(new DyeColor[0]);
    }


}
