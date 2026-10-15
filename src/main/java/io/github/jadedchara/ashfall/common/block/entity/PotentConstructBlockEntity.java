package io.github.jadedchara.ashfall.common.block.entity;

import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
        if(be.state == 1 && be.quantity >= 16){
            l.setBlock(bp, AshfallBlocks.SPATIAL_ANCHOR_BLOCK.defaultBlockState(),1);
        }else{
            be.setQuantity(0);
            be.setState(0);
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("state", 0);
        nbt.putInt("quantity",0);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        state = nbt.getInt("state");
        quantity = nbt.getInt("quantity");
    }
}
