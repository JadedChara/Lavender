package io.github.jadedchara.ashfall.common.block.entity;

import net.minecraft.core.BlockPos;
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
}
