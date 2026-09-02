package io.github.jadedchara.lavender.mixin.common;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Shulker.class)
public class ShulkerMixin extends AbstractGolem {
    @Shadow
    protected static final EntityDataAccessor<Byte> DATA_COLOR_ID = SynchedEntityData
            .defineId(
                    Shulker.class,
                    EntityDataSerializers.BYTE
            );
    protected ShulkerMixin(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
            method = "getColor",
            at = @At(shift = At.Shift.BEFORE, value = "RETURN"),
            cancellable = true
    )
    private void getPatchedColor(CallbackInfoReturnable<DyeColor> cir) {
        byte b = this.entityData.get(DATA_COLOR_ID);
        cir.setReturnValue( b != 64 && b <= 63 ? DyeColor.byId(b) : null);
    }
    @Inject(locals = LocalCapture.CAPTURE_FAILHARD,
            method = "defineSynchedData",
            at = @At(shift = At.Shift.BEFORE, value = "TAIL"),
            cancellable = true
    )
    private void definePatchedSynchedData(CallbackInfo ci) {
        this.entityData.set(DATA_COLOR_ID, (byte)64);
    }
}
