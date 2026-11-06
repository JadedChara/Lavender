package io.github.jadedchara.ashfall.common.block.entity;

import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ControlAnchorBlockEntity extends SpatialAnchorBlockEntity{
    public ControlAnchorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
    }

    @Override
    public void upgradeProtocol(Level l, BlockPos bp) {
        return;
    }

    @Override
    public void updateCharge(Level l, boolean charge){
        if(charge != this.powered) {
            this.powered = charge;
            try{
                AnchorPositionComponent.ANCHORS.maybeGet(l).get().allowWarps(charge);
            }catch(Exception e){

            }
            this.setChanged();
            if(charge){
                l.playSound(null,this.getBlockPos(), AshfallSounds.PULSE_EVENT, SoundSource.BLOCKS,0.5f,1.5f);
            }
        }
    }
}
