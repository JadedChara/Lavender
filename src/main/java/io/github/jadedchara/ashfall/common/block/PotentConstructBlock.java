package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.block.entity.PotentConstructBlockEntity;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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
        if(!l.isClientSide){
            try{
                PotentConstructBlockEntity pcbe = (PotentConstructBlockEntity) l.getBlockEntity(bp);
                if(p.getItemInHand(hand).equals(Items.ENDER_PEARL)){
                    pcbe.setState(1);
                    pcbe.setQuantity(1);
                    p.getItemInHand(hand).shrink(1);
                    l.playSound(null,bp, AshfallSounds.PULSE_EVENT, SoundSource.BLOCKS,1f,1f);
                }
            }catch(Exception e){
                System.out.println("Error serializing block entity for Potent Construct at: " + bp);
            }
        }
        return super.use(bs, l, bp, p, hand, result);
    }
}
