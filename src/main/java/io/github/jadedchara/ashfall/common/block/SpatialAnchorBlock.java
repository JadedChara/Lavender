package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.block.entity.SpatialAnchorBlockEntity;
import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import io.github.jadedchara.ashfall.common.cca.components.WarpstoneComponent;
import io.github.jadedchara.ashfall.common.item.warpstone.AnchorWarpstoneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SpatialAnchorBlock extends Block implements EntityBlock {

    public SpatialAnchorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos bp, BlockState bs) {
        return new SpatialAnchorBlockEntity(bp,bs);

    }

    @Override
    public void onPlace(BlockState bs, Level l, BlockPos bp, BlockState bs2, boolean bl) {
        super.onPlace(bs, l, bp, bs2, bl);
        if(!l.isClientSide()){
            AnchorPositionComponent.ANCHORS.maybeGet(l).get().addPosition(bp);
            System.out.println("Added Anchor at: " + bp + " in " + l);
            System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(l).get().getPositions());
        }
    }

    @Override
    public void destroy(LevelAccessor la, BlockPos bp, BlockState bs) {
        super.destroy(la, bp, bs);
        if(!la.isClientSide()){
            AnchorPositionComponent.ANCHORS.maybeGet(la).get().removePosition(bp);
            System.out.println("Removed Anchor at: " + bp + " in " + la);
            System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(la).get().getPositions());

        }

    }

    @Override
    public InteractionResult use(BlockState bs, Level l, BlockPos bp, Player p,
                                 InteractionHand hand, BlockHitResult bhr) {
        if(!l.isClientSide()){
            if(p.getItemInHand(hand).getItem() instanceof AnchorWarpstoneItem){
                WarpstoneComponent.WARPSTONES.maybeGet(p.getItemInHand(hand)).get().setBoundAnchor(bp);
                p.sendSystemMessage(Component.literal("Binding Warpstone to a Spatial Anchor...").withStyle(ChatFormatting.AQUA));
            }
        }
        return super.use(bs, l, bp, p, hand, bhr);
    }
}
