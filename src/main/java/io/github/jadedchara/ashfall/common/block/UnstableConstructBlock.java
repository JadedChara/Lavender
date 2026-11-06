package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.block.entity.UnstableConstructBlockEntity;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class UnstableConstructBlock extends Block implements EntityBlock {
    public UnstableConstructBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos bp, BlockState bs) {
        return new UnstableConstructBlockEntity(bp,bs);
    }

    @Override
    public InteractionResult use(BlockState bs, Level l, BlockPos bp, Player p,
                                 InteractionHand hand, BlockHitResult result) {
        return super.use(bs,l,bp,p,hand,result);
    }



    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState bs, BlockEntityType<T> t) {
        return t == AshfallBlocks.UNSTABLE_CONSTRUCT_BLOCK_ENTITY ?
                (level,bp,s,be)->{
                    UnstableConstructBlockEntity ucbe = (UnstableConstructBlockEntity) be;
                    ucbe.tick(level,bp,s,ucbe);
                } : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
