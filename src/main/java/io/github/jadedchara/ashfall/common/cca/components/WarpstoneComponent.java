package io.github.jadedchara.ashfall.common.cca.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.cca.util.NavigationalComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;


public class WarpstoneComponent extends ItemComponent implements NavigationalComponent {
    public static final ComponentKey<WarpstoneComponent> WARPSTONES =
            ComponentRegistry.getOrCreate(Ashfall.id("warpstones"), WarpstoneComponent.class);

    public WarpstoneComponent(ItemStack stack) {
        super(stack);
    }

    @Override
    public void setDimension(ResourceKey<Level> dimension) {
        this.putString("dimension",dimension.location().toString());
    }

    @Override
    public ResourceKey<Level> getDimension() {
        if(!this.hasTag("dimension") || this.getString("dimension").isEmpty()){
            return null;
        }
        return ResourceKey.create(Registry.DIMENSION_REGISTRY,
                new ResourceLocation(this.getString("dimension")));
    }
    public void setBoundEntity(UUID id){
        this.putString("boundEntity",id.toString());
    }
    public UUID getBoundEntity(){
        if (!this.hasTag("boundEntity") || this.getString("boundEntity").isEmpty()) {
            return null;
        }
        return UUID.fromString(this.getString("boundEntity"));
    }
    public void removeBoundEntity() {
        this.remove("boundEntity");
    }

    @Override
    public void setBoundAnchor(BlockPos pos) {

        this.putCompound("Bound", NbtUtils.writeBlockPos(pos));
    }

    @Override
    public void removeBoundAnchor() {
        this.remove("Bound");

    }

    @Override
    public BlockPos getBoundAnchor() {
        if(!this.hasTag("Bound") || this.getCompound("Bound").isEmpty()){
            this.remove("Bound");
            return null;
        }
        return NbtUtils.readBlockPos(this.getCompound("Bound"));
    }
}
