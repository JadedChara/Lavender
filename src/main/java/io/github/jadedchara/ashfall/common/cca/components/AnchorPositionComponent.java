package io.github.jadedchara.ashfall.common.cca.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.cca.util.PositionListComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class AnchorPositionComponent implements PositionListComponent, AutoSyncedComponent {
    public static final ComponentKey<AnchorPositionComponent> ANCHORS =
            ComponentRegistry.getOrCreate(Ashfall.id("anchors"), AnchorPositionComponent.class);

    private List<BlockPos> anchors = new ArrayList<>();
    private final Level world;

    public AnchorPositionComponent(Level l){
        this.world = l;
    }

    public void update(){
        ANCHORS.sync(this.world);
    }

    @Override
    public void addPosition(BlockPos bp) {
        this.anchors.add(bp);
        this.update();
    }

    @Override
    public void removePosition(BlockPos bp) {
        try{
            anchors.remove(bp);
        }catch(Exception e){
            //ignore
        }
        this.update();
    }

    @Override
    public BlockPos getPosition(BlockPos bp) {
        if(!this.anchors.isEmpty()){
            if(this.anchors.contains(bp)){
                return bp;
            }else{
                return this.anchors.get((int) Math.floor(Math.random()*anchors.size()));
            }
        }else{
            return null;
        }
    }

    @Override
    public List<BlockPos> getPositions() {
        return this.anchors;
    }

    @Override
    public void clearPositions() {
        this.anchors = new ArrayList<>();
        this.update();
    }

    @Override
    public BlockPos getRandomPosition() {
        if(!this.anchors.isEmpty()){
            return this.anchors.get((int) Math.floor(Math.random()*anchors.size()));
        }else{
            return null;
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.anchors = new ArrayList<>();
        tag.getList("Anchors",CompoundTag.TAG_COMPOUND).forEach(l->{
            anchors.add(NbtUtils.readBlockPos((CompoundTag) l));
        });
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        ListTag lt = new ListTag();
        this.anchors.forEach(anchor->{
            lt.add(NbtUtils.writeBlockPos(anchor));
        });
        tag.put("Anchors",lt);
    }
}
