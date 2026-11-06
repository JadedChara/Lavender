package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.block.entity.ControlAnchorBlockEntity;
import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import io.github.jadedchara.ashfall.common.cca.components.WarpstoneComponent;
import io.github.jadedchara.ashfall.common.item.warpstone.AnchorWarpstoneItem;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ControlAnchorBlock extends Block implements EntityBlock {
    public ControlAnchorBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
    @Override
    public void onPlace(BlockState bs, Level l, BlockPos bp, BlockState bs2, boolean bl) {
        super.onPlace(bs, l, bp, bs2, bl);
        if(!l.isClientSide()){
            if(l.getBlockEntity(bp) instanceof ControlAnchorBlockEntity cabe){
                if(cabe.validate(l,bp)){
                    AnchorPositionComponent.ANCHORS.maybeGet(l).get().addPosition(bp);
                    System.out.println("Added Anchor at: " + bp + " in " + l);
                    System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(l).get().getPositions());
                }
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level l, BlockPos bp, BlockState blockState2, boolean bl) {
        if(!l.isClientSide() && l.getBlockEntity(bp) instanceof ControlAnchorBlockEntity cabe){
            ItemStack droppable = Items.AMETHYST_BLOCK.getDefaultInstance();
            if(cabe.getPower() > 0){
                droppable.setCount(cabe.getPower());
                Containers.dropItemStack((Level) l,bp.getX(),bp.getY(),bp.getZ(),
                        droppable);
            }
        }
        super.onRemove(blockState, l, bp, blockState2, bl);
        if(!l.isClientSide()){
            AnchorPositionComponent.ANCHORS.maybeGet(l).get().removePosition(bp);

            System.out.println("Removed Anchor at: " + bp + " in " + l);
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
        if(!l.isClientSide() && l.getBlockEntity(bp) instanceof ControlAnchorBlockEntity cabe){
            if(p.getItemInHand(hand).getItem() instanceof AnchorWarpstoneItem && cabe.valid){
                WarpstoneComponent.WARPSTONES.maybeGet(p.getItemInHand(hand)).get().setBoundAnchor(bp);
                p.sendSystemMessage(Component.literal("Binding Warpstone to a Control Anchor...").withStyle(ChatFormatting.AQUA));
            }else if(p.getItemInHand(hand).getItem() == Items.AMETHYST_BLOCK && cabe.getPower() <16){
                cabe.setPower(cabe.getPower()+1);
                l.sendBlockUpdated(bp,bs,bs,3);
                if(!p.getAbilities().instabuild){
                    p.getItemInHand(hand).shrink(1);
                }
                if(p instanceof ServerPlayer sp){
                    //l.playSound();
                    sp.connection.send(new ClientboundSetTitlesAnimationPacket(10,40,10));
                    sp.connection.send(new ClientboundSetActionBarTextPacket(Component
                                    .literal("Charging Control Anchor: " +cabe.getPower()+ "/16")
                                    .withStyle(ChatFormatting.LIGHT_PURPLE)
                            )
                    );
                }
                return InteractionResult.sidedSuccess(l.isClientSide());

            }
        }
        return super.use(bs, l, bp, p, hand, bhr);
    }
}
