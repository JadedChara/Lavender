package io.github.jadedchara.ashfall.common.item.warpstone;

import io.github.jadedchara.ashfall.common.block.entity.SpatialAnchorBlockEntity;
import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import io.github.jadedchara.ashfall.common.cca.components.WarpstoneComponent;
import io.github.jadedchara.ashfall.common.item.util.WarpstoneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DimensionalWarpstoneItem extends WarpstoneItem {
    public DimensionalWarpstoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level l, Player p, InteractionHand hand) {

        if(!l.isClientSide()){
            ServerPlayer sp = (ServerPlayer)p;
            WarpstoneComponent warpstone = WarpstoneComponent.WARPSTONES.maybeGet(sp.getItemInHand(hand)).get();
            ResourceKey<Level> targetWorld = warpstone.getDimension();

            //Triggers the Warpstone if active
            if(targetWorld != null){
                try{
                    ServerLevel sl = sp.getServer().getLevel(targetWorld);
                    BlockPos bp;
                    if(warpstone.getBoundAnchor() != null && !sp.isCrouching()){
                        if(
                                warpstone.getBoundAnchor() !=null
                                        && sl.getBlockState(warpstone.getBoundAnchor().above()).getBlock() instanceof AirBlock
                                        && sl.getBlockState(warpstone.getBoundAnchor().above().above()).getBlock() instanceof AirBlock
                        ){
                            bp = warpstone.getBoundAnchor();
                            double xC = bp.getX()+0.5;
                            double yC = bp.getY();
                            double zC = bp.getZ()+0.5;
                            sp.teleportTo(sl,xC,yC,zC,sp.getYRot(),sp.getXRot());
                            p.sendSystemMessage(Component.literal("Warping...").withStyle(ChatFormatting.AQUA));
                            sp.getCooldowns().addCooldown(sp.getItemInHand(hand).getItem(),400);
                        }else{
                            p.sendSystemMessage(
                                    Component
                                            .literal("Target destination is obstructed...")
                                            .withStyle(ChatFormatting.DARK_RED)
                            );
                        }
                    }else{
                        warpstone.setBoundAnchor(sp.getOnPos());
                        p.sendSystemMessage(Component.literal("Binding coordinates...").withStyle(ChatFormatting.DARK_AQUA));
                    }
                }
                catch(Exception e){
                    p.sendSystemMessage(Component.literal("Warpstone error.").withStyle(ChatFormatting.DARK_RED));
                }
            }
            //Activates the Warpstone for its next usage;
            else{
                p.sendSystemMessage(Component.literal("Activating Warpstone...").withStyle(ChatFormatting.DARK_AQUA));
                WarpstoneComponent.WARPSTONES.maybeGet(sp.getItemInHand(hand)).get().setDimension(sp.getLevel().dimension());
                sp.getCooldowns().addCooldown(sp.getItemInHand(hand).getItem(),200);

            }
        }
        return super.use(l, p, hand);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        BlockPos pos = WarpstoneComponent.WARPSTONES.maybeGet(itemStack).get().getBoundAnchor();
        ResourceKey<Level> dim = WarpstoneComponent.WARPSTONES.maybeGet(itemStack).get().getDimension();
        if(dim != null){
            String plainDim = dim.location().toString();
            list.add(Component.literal(plainDim).withStyle(ChatFormatting.DARK_AQUA));
        }
        if(pos != null){
            String plainPos = pos.getX()+", "+pos.getY()+", "+ pos.getZ();
            list.add(Component.literal(plainPos).withStyle(ChatFormatting.AQUA));
        }

        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }
}
