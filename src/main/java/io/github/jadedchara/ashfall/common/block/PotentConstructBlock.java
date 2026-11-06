package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.block.entity.PotentConstructBlockEntity;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
                //SPATIAL ANCHOR
                if(pcbe.getState()==1 ||pcbe.getState()==0 ){
                    if(!l.isClientSide()){
                        try{
                            pcbe.setState(1);
                            pcbe.setQuantity(pcbe.getQuantity()+1);
                            l.sendBlockUpdated(bp,bs,bs,3);

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
                            }
                        }catch(Exception e){
                            System.out.println("Error serializing block entity for Potent Construct at: " + bp);
                        }
                    }
                    return InteractionResult.sidedSuccess(l.isClientSide());
                }
                //
                //
            }
        }
        //8 blaze powder, 4 iron ingots, 4 soul shards
        else if(p.getItemInHand(hand).getItem() == Items.BLAZE_POWDER){
            if(l.getBlockEntity(bp) instanceof PotentConstructBlockEntity pcbe){
                //UNSTABLE CONSTRUCT
                if((pcbe.getState()==2 && pcbe.getQuantity()<8) || pcbe.getState()==0){
                    if(!l.isClientSide()){
                        try{
                            pcbe.setState(2);
                            pcbe.setQuantity(pcbe.getQuantity()+1);
                            l.sendBlockUpdated(bp,bs,bs,3);

                            if(!p.getAbilities().instabuild){
                                p.getItemInHand(hand).shrink(1);
                            }
                            l.playSound(null,bp, AshfallSounds.FEEDBACK_EVENT, SoundSource.BLOCKS,1f,1f);
                            if(p instanceof ServerPlayer sp){
                                sp.connection.send(new ClientboundSetTitlesAnimationPacket(10,40,10));
                                sp.connection.send(new ClientboundSetActionBarTextPacket(Component
                                                .literal("Constructing Unstable Construct: " +pcbe.getQuantity()+ "/16")
                                                .withStyle(ChatFormatting.GRAY)
                                        )
                                );
                            }
                        }catch(Exception e){
                            System.out.println("Error serializing block entity for Potent Construct at: " + bp);
                        }
                    }
                    return InteractionResult.sidedSuccess(l.isClientSide());
                }
            }
        }
        else if(p.getItemInHand(hand).getItem() == Items.IRON_INGOT){
            if(l.getBlockEntity(bp) instanceof PotentConstructBlockEntity pcbe){
                //UNSTABLE CONSTRUCT
                if((pcbe.getState()==2 && pcbe.getQuantity()>=8 && pcbe.getQuantity()<12)){
                    if(!l.isClientSide()){
                        try{
                            pcbe.setState(2);
                            pcbe.setQuantity(pcbe.getQuantity()+1);
                            l.sendBlockUpdated(bp,bs,bs,3);

                            if(!p.getAbilities().instabuild){
                                p.getItemInHand(hand).shrink(1);
                            }
                            l.playSound(null,bp, AshfallSounds.FEEDBACK_EVENT, SoundSource.BLOCKS,1f,1f);
                            if(p instanceof ServerPlayer sp){
                                sp.connection.send(new ClientboundSetTitlesAnimationPacket(10,40,10));
                                sp.connection.send(new ClientboundSetActionBarTextPacket(Component
                                                .literal("Constructing Unstable Construct: " +pcbe.getQuantity()+ "/16")
                                                .withStyle(ChatFormatting.GRAY)
                                        )
                                );
                            }
                        }catch(Exception e){
                            System.out.println("Error serializing block entity for Potent Construct at: " + bp);
                        }
                    }
                    return InteractionResult.sidedSuccess(l.isClientSide());
                }
            }
        }
        else if(p.getItemInHand(hand).getItem() == Items.ECHO_SHARD){
            if(l.getBlockEntity(bp) instanceof PotentConstructBlockEntity pcbe){
                //UNSTABLE CONSTRUCT
                if((pcbe.getState()==2 && pcbe.getQuantity()>=12 && pcbe.getQuantity()<16) || pcbe.getState()==0){
                    if(!l.isClientSide()){
                        try{
                            pcbe.setState(2);
                            pcbe.setQuantity(pcbe.getQuantity()+1);
                            l.sendBlockUpdated(bp,bs,bs,3);

                            if(!p.getAbilities().instabuild){
                                p.getItemInHand(hand).shrink(1);
                            }
                            l.playSound(null,bp, AshfallSounds.FEEDBACK_EVENT, SoundSource.BLOCKS,1f,1f);
                            if(p instanceof ServerPlayer sp){
                                sp.connection.send(new ClientboundSetTitlesAnimationPacket(10,40,10));
                                sp.connection.send(new ClientboundSetActionBarTextPacket(Component
                                                .literal("Constructing Unstable Construct: " +pcbe.getQuantity()+ "/16")
                                                .withStyle(ChatFormatting.GRAY)
                                        )
                                );
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

    @Override
    public void onRemove(BlockState bs, Level l, BlockPos bp, BlockState bs2, boolean bl) {
        if(!l.isClientSide() && l.getBlockEntity(bp) instanceof PotentConstructBlockEntity pcbe){
            ItemStack d1;
            ItemStack d2;
            ItemStack d3;
            if(pcbe.getState() == 1){
                d1 = Items.ENDER_PEARL.getDefaultInstance();
                d1.setCount(pcbe.getQuantity());
                Containers.dropItemStack(l,bp.getX(),bp.getY(),bp.getZ(),d1);
            }else if(pcbe.getState() == 2){
                if(pcbe.getQuantity()>12){
                    d1 = Items.BLAZE_POWDER.getDefaultInstance();
                    d1.setCount(8);
                    Containers.dropItemStack(l,bp.getX(),bp.getY(),bp.getZ(),d1);
                    d2 = Items.IRON_INGOT.getDefaultInstance();
                    d2.setCount(4);
                    Containers.dropItemStack(l,bp.getX(),bp.getY(),bp.getZ(),d1);
                    d3 = Items.ECHO_SHARD.getDefaultInstance();
                    d3.setCount(pcbe.getQuantity()-12);
                    Containers.dropItemStack(l,bp.getX(),bp.getY(),bp.getZ(),d1);
                }else if(pcbe.getQuantity()>8){
                    d1 = Items.BLAZE_POWDER.getDefaultInstance();
                    d1.setCount(8);
                    Containers.dropItemStack(l,bp.getX(),bp.getY(),bp.getZ(),d1);
                    d2 = Items.IRON_INGOT.getDefaultInstance();
                    d2.setCount(pcbe.getQuantity());
                    Containers.dropItemStack(l,bp.getX(),bp.getY(),bp.getZ(),d1);
                }else{
                    d1 = Items.BLAZE_POWDER.getDefaultInstance();
                    d1.setCount(pcbe.getQuantity());
                    Containers.dropItemStack(l,bp.getX(),bp.getY(),bp.getZ(),d1);
                }
            }
        }
        super.onRemove(bs,l,bp,bs2,bl);
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

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}

