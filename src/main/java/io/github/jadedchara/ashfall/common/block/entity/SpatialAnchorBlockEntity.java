package io.github.jadedchara.ashfall.common.block.entity;

import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public class SpatialAnchorBlockEntity extends BlockEntity implements BlockEntityTicker<SpatialAnchorBlockEntity> {

    private boolean powered = false;
    public boolean valid = false;
    private int tickSpacer = 0;

    public SpatialAnchorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AshfallBlocks.SPATIAL_ANCHOR_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void tick(Level l, BlockPos bp, BlockState bs, SpatialAnchorBlockEntity be) {
        if(!l.isClientSide()){
            if(l.hasNeighborSignal(bp)){
                be.updateCharge(l,true);
            }else{
                be.updateCharge(l,false);
            }
            if(tickSpacer == 0){
                if(!be.validate(l,bp)){
                    if(be.valid){
                        try{
                            AnchorPositionComponent.ANCHORS.maybeGet(l).get().removePosition(bp);
                            System.out.println("Removed Anchor at: " + bp + " in " + l);
                            System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(l).get().getPositions());
                            be.valid = false;
                            be.setChanged();
                        }catch(Exception e){
                            //
                        }
                    }
                }else{
                    if(!be.valid){
                        try{
                            AnchorPositionComponent.ANCHORS.maybeGet(l).get().addPosition(bp);
                            System.out.println("Added Anchor at: " + bp + " in " + l);
                            System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(l).get().getPositions());
                            be.valid = true;
                            be.setChanged();
                        }catch(Exception e){
                            //
                        }
                    }
                }
                tickSpacer++;
            }else if(tickSpacer == 9){
                tickSpacer = 0;
            }else{
                tickSpacer++;
            }
        }
    }

    public void updateCharge(Level l,boolean charge){
        if(charge != this.powered) {
            this.powered = charge;
            this.setChanged();
            if(charge){
                l.playSound(null,this.getBlockPos(), AshfallSounds.PULSE_EVENT, SoundSource.BLOCKS,0.5f,1.5f);
            }
        }
    }

    public boolean validate(Level l, BlockPos bp){

        BlockPos tip = bp.below();
        BlockPos upper = tip.below();
        BlockPos mid = upper.below();
        BlockPos base = mid.below();
            return (
                    /*pillar*/
                    l.getBlockState(tip).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(upper).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid).getBlock() == Blocks.COBBLED_DEEPSLATE
                    /*middle-ring*/
                            && l.getBlockState(mid.west(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.east(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.north(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.south(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.north(1).west(1)).getBlock() == Blocks.SOUL_SAND
                            && l.getBlockState(mid.north(1).east(1)).getBlock() == Blocks.SOUL_SAND
                            && l.getBlockState(mid.south(1).west(1)).getBlock() == Blocks.SOUL_SAND
                            && l.getBlockState(mid.south(1).east(1)).getBlock() == Blocks.SOUL_SAND
                    /*upper-ring*/
                            && l.getBlockState(upper.west(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.east(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.north(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.south(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.north(1).west(1)).getBlock() == Blocks.SOUL_FIRE
                            && l.getBlockState(upper.north(1).east(1)).getBlock() == Blocks.SOUL_FIRE
                            && l.getBlockState(upper.south(1).west(1)).getBlock() == Blocks.SOUL_FIRE
                            && l.getBlockState(upper.south(1).east(1)).getBlock() == Blocks.SOUL_FIRE
                    /*platform*/
                            && l.getBlockState(base.north(2).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2).east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.north(1).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1).east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.south(1).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1).east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.south(2).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2).east(2)).getBlock() == Blocks.SOUL_SOIL

            );
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean("charged", powered);
        nbt.putBoolean("valid",valid);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        powered = nbt.getBoolean("charged");
        valid = nbt.getBoolean("valid");
    }
}
