package io.github.jadedchara.ashfall.common.item.warpstone.inverted;

import io.github.jadedchara.ashfall.common.cca.components.WarpstoneComponent;
import io.github.jadedchara.ashfall.common.item.util.WarpstoneItem;
import io.github.jadedchara.ashfall.common.item.warpstone.DimensionalWarpstoneItem;
import io.github.jadedchara.ashfall.mixin.common.ServerLevelAccessor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class InvertedDimensionalWarpstoneItem extends WarpstoneItem {
    public InvertedDimensionalWarpstoneItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack i, Player p, LivingEntity e,
                                                  InteractionHand hand) {
        if(!p.getLevel().isClientSide()){
            try{
                if(WarpstoneComponent.WARPSTONES.maybeGet(p.getItemInHand(hand)).get().getBoundAnchor() != null){
                    WarpstoneComponent.WARPSTONES.maybeGet(p.getItemInHand(hand)).get().setBoundEntity(e.getUUID());
                    p.getItemInHand(hand).setTag(p.getItemInHand(hand).getOrCreateTag());
                    p.sendSystemMessage(Component.literal("Bound to entity...").withStyle(ChatFormatting.DARK_AQUA));
                }else{
                    WarpstoneComponent.WARPSTONES.maybeGet(p.getItemInHand(hand)).get().setBoundAnchor(p.getOnPos());
                    WarpstoneComponent.WARPSTONES.maybeGet(p.getItemInHand(hand)).get().setDimension(p.getLevel().dimension());
                    p.getItemInHand(hand).setTag(p.getItemInHand(hand).getOrCreateTag());
                    p.sendSystemMessage(Component.literal("Activating Warpstone and binding coordinates...").withStyle(ChatFormatting.DARK_AQUA));
                    if(!p.getAbilities().instabuild){
                        p.getCooldowns().addCooldown(p.getItemInHand(hand).getItem(),200);
                    }
                }
            }catch (Exception err){
                //
            }
        }
        return InteractionResult.sidedSuccess(p.getLevel().isClientSide());
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
                        if(warpstone.getBoundEntity() != null){
                            Entity ent = null;
                            ent = sp.getServer().getPlayerList().getPlayer(warpstone.getBoundEntity());
                            if(ent == null){
                                for (ServerLevel serverLevel : sp.getServer().getAllLevels()) {
                                    ent = serverLevel.getEntity(warpstone.getBoundEntity());
                                    if (ent == null && ((ServerLevelAccessor)serverLevel).getEntityManager() != null) {
                                        ent =
                                                ((ServerLevelAccessor)serverLevel)
                                                        .getEntityManager()
                                                        .getEntityGetter()
                                                        .get(warpstone.getBoundEntity()
                                                        );
                                    }
                                    if (ent != null) break;
                                }
                            }
                            if(ent != null){
                                if(
                                        warpstone.getBoundAnchor() !=null
                                                && sl.getBlockState(warpstone.getBoundAnchor().above()).getBlock() instanceof AirBlock
                                                && sl.getBlockState(warpstone.getBoundAnchor().above().above()).getBlock() instanceof AirBlock
                                ){
                                    bp = warpstone.getBoundAnchor();
                                    double xC = bp.getX()+0.5;
                                    double yC = bp.getY()+1;
                                    double zC = bp.getZ()+0.5;
                                    if(ent instanceof ServerPlayer pl){
                                        pl.teleportTo(sl,xC,yC,zC,sp.getYRot(),sp.getXRot());
                                    }else{
                                        ent.setPos(xC,yC,zC);
                                        ent.changeDimension(sl);
                                    }
                                    p.sendSystemMessage(Component.literal("Warping...").withStyle(ChatFormatting.AQUA));
                                    if(!p.getAbilities().instabuild){
                                        sp.getCooldowns().addCooldown(sp.getItemInHand(hand).getItem(),400);
                                    }
                                }else{
                                    p.sendSystemMessage(
                                            Component
                                                    .literal("Target destination is obstructed...")
                                                    .withStyle(ChatFormatting.DARK_RED)
                                    );
                                }
                            }else{
                                p.sendSystemMessage(Component.literal("No valid entity. Unbinding...").withStyle(ChatFormatting.DARK_RED));
                                warpstone.removeBoundEntity();
                            }
                        }else{
                            p.sendSystemMessage(Component.literal("No valid entity. Unbinding...").withStyle(ChatFormatting.DARK_RED));
                            warpstone.removeBoundEntity();
                        }
                    }else{
                        warpstone.setBoundAnchor(sp.getOnPos());
                        warpstone.setDimension(sp.getLevel().dimension());
                        p.sendSystemMessage(Component.literal("Binding coordinates...").withStyle(ChatFormatting.DARK_AQUA));
                    }
                }
                catch(Exception e){
                    p.sendSystemMessage(Component.literal(
                            "Warpstone error. Potential entity may be inaccessible at this time...")
                            .withStyle(ChatFormatting.DARK_RED)
                    );
                }
            }
            //Activates the Warpstone for its next usage;
            else{
                p.sendSystemMessage(Component.literal("Activating Warpstone...").withStyle(ChatFormatting.DARK_AQUA));
                WarpstoneComponent.WARPSTONES.maybeGet(sp.getItemInHand(hand)).get().setDimension(sp.getLevel().dimension());
                if(!p.getAbilities().instabuild){
                    sp.getCooldowns().addCooldown(sp.getItemInHand(hand).getItem(),200);
                }

            }
        }
        return InteractionResultHolder.sidedSuccess(p.getItemInHand(hand),p.getLevel().isClientSide());
    }
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        BlockPos pos = WarpstoneComponent.WARPSTONES.maybeGet(itemStack).get().getBoundAnchor();
        ResourceKey<Level> dim = WarpstoneComponent.WARPSTONES.maybeGet(itemStack).get().getDimension();
        UUID bind = WarpstoneComponent.WARPSTONES.maybeGet(itemStack).get().getBoundEntity();
        if(dim != null){
            String plainDim = dim.location().toString();
            list.add(Component.literal(plainDim).withStyle(ChatFormatting.DARK_AQUA));
        }
        if(pos != null){
            String plainPos = pos.getX()+", "+pos.getY()+", "+ pos.getZ();
            list.add(Component.literal(plainPos).withStyle(ChatFormatting.AQUA));
        }
        if(bind != null){
            if(level.getPlayerByUUID(bind) != null){
                list.add(level.getPlayerByUUID(bind).getDisplayName());
            }
            list.add(Component.literal("Entity: "+bind));
        }

        super.appendHoverText(itemStack, level, list, tooltipFlag);
    }
}
