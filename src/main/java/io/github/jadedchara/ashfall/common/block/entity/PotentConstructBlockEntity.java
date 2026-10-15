package io.github.jadedchara.ashfall.common.block.entity;

import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PotentConstructBlockEntity extends BlockEntity implements BlockEntityTicker<PotentConstructBlockEntity> {
    //craft using four iron ingots, four iron nuggets, and 1 quartz


    int state = 0;
    /*
    * STATES
    * 0 - passive, listens for all items.
    * 1 - Spatial Anchor, listens for pearls
    * 2 - Control Anchor, listens for ender eyes
    * 3 -
    * 4 -
    * */
    int quantity = 0;

    public PotentConstructBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AshfallBlocks.POTENT_CONSTRUCT_BLOCK_ENTITY, blockPos, blockState);
    }


    @Override
    public void tick(Level l, BlockPos bp, BlockState bs, PotentConstructBlockEntity be) {
        if(!l.isClientSide()){
            if(be.state == 1 && be.quantity >= 16){
                l.setBlock(bp, AshfallBlocks.SPATIAL_ANCHOR_BLOCK.defaultBlockState(),3);
                if(l instanceof ServerLevel sl){
                    sl.sendParticles(
                            ParticleTypes.PORTAL,
                            bp.getX()+0.5,
                            bp.getY()+0.5,
                            bp.getZ()+0.5,
                            30,
                            0.5,
                            0.5,
                            0.5,
                            0.45
                    );
                }
                l.playSound(null,bp, AshfallSounds.PULSE_EVENT, SoundSource.BLOCKS,1f,0.5f);
            }
        }
    }

    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }

    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("state", this.state);
        nbt.putInt("quantity",this.quantity);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        state = nbt.getInt("state");
        quantity = nbt.getInt("quantity");
    }
}
