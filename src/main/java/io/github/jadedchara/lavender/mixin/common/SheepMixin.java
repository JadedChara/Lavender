package io.github.jadedchara.lavender.mixin.common;

import io.github.jadedchara.lavender.Lavender;
import net.minecraft.Util;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
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

    @Shadow
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        Sheep sheep = (Sheep)ageableMob;
        Sheep sheep2 = EntityType.SHEEP.create(serverLevel);
        sheep2.setColor(this.getOffspringColor(this,sheep));
        return sheep2;
    }

    @Inject(method = "getDefaultLootTable", at = @At(value = "HEAD"), cancellable = true)
    private void tweakDefaultLootTable(CallbackInfoReturnable<ResourceLocation> cir) {
        //Sheep $this = (Sheep) (Object) this;

        if (!this.isSheared() && this.getColor().getId()>15) {
            cir.setReturnValue(Lavender.id("entities/sheep/"+this.getColor().getName()));
        }
    }


}
