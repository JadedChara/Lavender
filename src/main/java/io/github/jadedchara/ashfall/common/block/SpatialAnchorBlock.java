package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.block.entity.SpatialAnchorBlockEntity;
import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import io.github.jadedchara.ashfall.common.cca.components.WarpstoneComponent;
import io.github.jadedchara.ashfall.common.item.warpstone.AnchorWarpstoneItem;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
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
            if(l.getBlockEntity(bp) instanceof SpatialAnchorBlockEntity sabe){
                if(sabe.validate(l,bp)){
                    AnchorPositionComponent.ANCHORS.maybeGet(l).get().addPosition(bp);
                    System.out.println("Added Anchor at: " + bp + " in " + l);
                    System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(l).get().getPositions());
                }
            }
        }
    }

    @Override
    public void destroy(LevelAccessor la, BlockPos bp, BlockState bs) {
        if(!la.isClientSide() && la.getBlockEntity(bp) instanceof SpatialAnchorBlockEntity sabe){
            if(sabe.getPower() > 0){
                for(int i = 0;i<sabe.getPower();i++){
                    Containers.dropItemStack((Level) la,bp.getX(),bp.getY(),bp.getZ(),
                            Items.AMETHYST_SHARD.getDefaultInstance());
                }
            }
        }
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
        if(!l.isClientSide() && l.getBlockEntity(bp) instanceof SpatialAnchorBlockEntity sabe){
            if(p.getItemInHand(hand).getItem() instanceof AnchorWarpstoneItem && sabe.valid){
                WarpstoneComponent.WARPSTONES.maybeGet(p.getItemInHand(hand)).get().setBoundAnchor(bp);
                p.sendSystemMessage(Component.literal("Binding Warpstone to a Spatial Anchor...").withStyle(ChatFormatting.AQUA));
            }else if(p.getItemInHand(hand).getItem() == Items.AMETHYST_SHARD){
                sabe.setPower(sabe.getPower()+1);
                l.sendBlockUpdated(bp,bs,bs,3);
                if(!p.getAbilities().instabuild){
                    p.getItemInHand(hand).shrink(1);
                }
                if(p instanceof ServerPlayer sp){
                    //l.playSound();
                    sp.connection.send(new ClientboundSetTitlesAnimationPacket(10,40,10));
                    sp.connection.send(new ClientboundSetActionBarTextPacket(Component
                                    .literal("Charging Spatial Anchor: " +sabe.getPower()+ "/16")
                                    .withStyle(ChatFormatting.LIGHT_PURPLE)
                            )
                    );
                }

            }
        }
        return super.use(bs, l, bp, p, hand, bhr);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState bs, BlockEntityType<T> t) {
        return t == AshfallBlocks.SPATIAL_ANCHOR_BLOCK_ENTITY ?
                (level,bp,s,be)->{
                    SpatialAnchorBlockEntity sabe = (SpatialAnchorBlockEntity) be;
                    sabe.tick(level,bp,s,sabe);
                } : null;
    }
    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
