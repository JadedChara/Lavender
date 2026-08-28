package io.github.jadedchara.lavender.mixin.common;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal implements Shearable {
    @Mutable
    @Shadow
    @Final
    private static EntityDataAccessor<Byte> DATA_WOOL_ID;

    protected SheepMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow protected abstract DyeColor getOffspringColor(Animal animal, Animal animal2);

    /**
     * @reason Allowing >16 unique dye colors (128)
     * @author ADudeCalledLeo
     */
    @Overwrite
    public DyeColor getColor() {
        byte b = this.entityData.get(DATA_WOOL_ID);
        return DyeColor.byId(b & 0x7F);
    }

    /**
     * @reason Allowing >16 unique dye colors (128)
     * @author ADudeCalledLeo
     */
    @Overwrite
    public void setColor(DyeColor color) {
        byte b = entityData.get(DATA_WOOL_ID);
        entityData.set(DATA_WOOL_ID, (byte) ((b & 0x80) | color.getId() % 0x7F));
    }

    /**
     * @reason Allowing >16 unique dye colors (128)
     * @author ADudeCalledLeo
     */
    @Overwrite
    public boolean isSheared() {
        return (entityData.get(DATA_WOOL_ID) & 0x80) != 0;
    }

    /**
     * @reason Allowing >16 unique dye colors (128)
     * @author ADudeCalledLeo
     */
    @Overwrite
    public void setSheared(boolean sheared) {
        byte b = entityData.get(DATA_WOOL_ID);
        entityData.set(DATA_WOOL_ID, (byte) ((b & 0x7F) | (sheared ? 0x80 : 0)));
    }

    /*@Inject(method="getColor",at = @At("RETURN"),cancellable = true)
    public void getFixedColor(CallbackInfoReturnable<DyeColor> cir) {
        cir.setReturnValue(DyeColor.byId(this.entityData.get(DATA_WOOL_ID) & 0x7f));
    }

    @Inject(method="setColor",at=@At("HEAD"),cancellable = true)
    public void setFixedColor(DyeColor c, CallbackInfo ci) {
        byte b = this.entityData.get(DATA_WOOL_ID);
        this.entityData.set(DATA_WOOL_ID, (byte)(b & 0x80 | c.getId() % 0x7f));
        System.out.println(this.entityData.get(DATA_WOOL_ID));
        ci.cancel();
    }

    @Inject(method="setSheared",at=@At("HEAD"),cancellable = true)
    public void setFixedSheared(boolean bl, CallbackInfo ci) {
        byte b = (Byte)this.entityData.get(DATA_WOOL_ID);
        this.entityData.set(DATA_WOOL_ID, (byte) ((b & 0x7F) | (bl ? 0x80 : 0)));
        ci.cancel();
    }

    @Inject(method="isSheared",at=@At("RETURN"),cancellable = true)
    public void isFixedSheared(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue( ((Byte)this.entityData.get(DATA_WOOL_ID) & 0x80) != 0 );
    }*/

    @Shadow
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Sheep sheep = (Sheep)ageableMob;
        Sheep sheep2 = EntityType.SHEEP.create(serverLevel);
        sheep2.setColor(this.getOffspringColor(this,sheep));
        return sheep2;
    }
}
