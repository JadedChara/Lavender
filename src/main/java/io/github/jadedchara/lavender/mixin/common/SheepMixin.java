package io.github.jadedchara.lavender.mixin.common;

import com.google.common.collect.Maps;
import io.github.jadedchara.lavender.Lavender;
import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal implements Shearable {
    @Mutable
    @Shadow
    @Final
    private static EntityDataAccessor<Byte> DATA_WOOL_ID;

    @Shadow
    @Final
    private static Map<DyeColor, ItemLike> ITEM_BY_DYE;


    /*@Inject(method="<clinit>",at=@At("TAIL"))
    private static void appendColors(CallbackInfo ci){
        Map<DyeColor,ItemLike> substitute = new HashMap<>(ITEM_BY_DYE);
        for(LavenderColors color : LavenderColors.values()){
            for(Block wool : LavenderBlocks.LAVENDER_BLOCKS){
                if(Registry.BLOCK.getKey(wool).getPath().endsWith(color.getName()+"_wool")){
                    substitute.put(color.get(),wool);
                }
            }
        }
        ITEM_BY_DYE = substitute;
    }*/

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

        if (!this.isSheared() && this.getColor().getId()>15) {
            cir.setReturnValue(Lavender.id("entities/sheep/"+this.getColor().getName()));
        }
    }

    @Inject(method = "shear",at=@At(value="HEAD"),cancellable = true)
    private void appendShear(SoundSource soundSource, CallbackInfo ci){
        this.level.playSound(null, this, SoundEvents.SHEEP_SHEAR, soundSource, 1.0F, 1.0F);
        this.setSheared(true);
        ci.cancel();
        int i = 1 + this.random.nextInt(3);

        if(!this.level.isClientSide()){
            for(int j = 0; j < i; ++j) {

                ItemLike b = ITEM_BY_DYE.get(this.getColor());

                if(this.getColor().getId()>15){
                    for(Block wool : LavenderBlocks.LAVENDER_BLOCKS){
                        //System.out.println(wool.getName().toString());
                        if(Registry.BLOCK.getKey(wool).getPath().endsWith(this.getColor().getName()+"_wool")){
                            b = wool;
                            break;
                        }
                    }
                }
                //System.out.println(b.asItem().getName(b.asItem().getDefaultInstance()));
                ItemEntity itemEntity = this.spawnAtLocation(b, 1);
                if (itemEntity != null) {
                    itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().add(((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (this.random.nextFloat() * 0.05F), ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
                }
            }
        }
    }


}
