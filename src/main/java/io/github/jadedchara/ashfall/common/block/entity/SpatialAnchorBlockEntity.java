package io.github.jadedchara.ashfall.common.block.entity;

import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class SpatialAnchorBlockEntity extends BlockEntity implements BlockEntityTicker<SpatialAnchorBlockEntity> {

    private boolean powered = false;

    public SpatialAnchorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AshfallBlocks.SPATIAL_ANCHOR_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void tick(Level l, BlockPos bp, BlockState bs, SpatialAnchorBlockEntity be) {
        if(l.hasNeighborSignal(bp)){
            be.updateCharge(true);
            l.playSound(null,this.getBlockPos(), AshfallSounds.FEEDBACK_EVENT, SoundSource.BLOCKS,1f,1f);
        }else{
           be.updateCharge(false);
        }
    }

    public void updateCharge(boolean charge){
        if(charge != this.powered) {
            this.powered = charge;
            this.setChanged();
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean("charged", powered);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        powered = nbt.getBoolean("charged");
    }
}
