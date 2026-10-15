package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.block.entity.PotentConstructBlockEntity;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.commands.TitleCommand;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PotentConstructBlock extends Block implements EntityBlock {
    public PotentConstructBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos bp, BlockState bs) {
        return new PotentConstructBlockEntity(bp,bs);
    }

    @Override
    public InteractionResult use(BlockState bs, Level l, BlockPos bp, Player p,
                                 InteractionHand hand, BlockHitResult result) {
        if(p.getItemInHand(hand).getItem() == Items.ENDER_PEARL){
            if(l.getBlockEntity(bp) instanceof PotentConstructBlockEntity pcbe){
                if(pcbe.getState()<2){
                    if(!l.isClientSide()){
                        try{
                            pcbe.setState(1);
                            pcbe.setQuantity(pcbe.getQuantity()+1);
                            pcbe.setChanged();

                            if(!p.getAbilities().instabuild){
                                p.getItemInHand(hand).shrink(1);
                            }
                            l.playSound(null,bp, AshfallSounds.FEEDBACK_EVENT, SoundSource.BLOCKS,1f,1f);
                            if(p instanceof ServerPlayer sp){
                                sp.connection.send(new ClientboundSetTitlesAnimationPacket(10,40,10));
                                sp.connection.send(new ClientboundSetActionBarTextPacket(Component
                                                        .literal("Constructing Spatial Anchor: " +pcbe.getQuantity()+ "/16")
                                                        .withStyle(ChatFormatting.DARK_AQUA)
                                        )
                                );
                                //sp.connection.send(new ClientboundSetTitleTextPacket(Component.literal("\u200b")));
                            }
                        }catch(Exception e){
                            System.out.println("Error serializing block entity for Potent Construct at: " + bp);
                        }
                    }
                    return InteractionResult.sidedSuccess(l.isClientSide());
                }
            }
        }
        return super.use(bs, l, bp, p, hand, result);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState bs, BlockEntityType<T> t) {
        return t == AshfallBlocks.POTENT_CONSTRUCT_BLOCK_ENTITY ?
                (level,bp,s,be)->{
            PotentConstructBlockEntity pcbe = (PotentConstructBlockEntity) be;
                    pcbe.tick(level,bp,s,pcbe);
                } : null;
    }
}
