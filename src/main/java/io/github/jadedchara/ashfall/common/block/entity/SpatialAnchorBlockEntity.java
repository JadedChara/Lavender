package io.github.jadedchara.ashfall.common.block.entity;

import io.github.jadedchara.ashfall.common.cca.components.AnchorPositionComponent;
import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import io.github.jadedchara.ashfall.common.registry.AshfallSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class SpatialAnchorBlockEntity extends BlockEntity implements BlockEntityTicker<SpatialAnchorBlockEntity>, IAnimatable {

    public boolean powered = false;
    public boolean valid = false;
    private int tickSpacer = 0;
    private int power = 0;

    public SpatialAnchorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AshfallBlocks.SPATIAL_ANCHOR_BLOCK_ENTITY, blockPos, blockState);
    }

    public int getPower(){
        return this.power;
    }

    public void setPower(int f){
        this.power = f;
        this.setChanged();
    }

    @Override
    public void tick(Level l, BlockPos bp, BlockState bs, SpatialAnchorBlockEntity be) {
        if(!l.isClientSide()){
            be.updateCharge(l,l.hasNeighborSignal(bp));
            if(be.tickSpacer == 0){
                if(!be.validate(l,bp)){
                    if(be.valid){
                        AnchorPositionComponent.ANCHORS.maybeGet(l).get().removePosition(bp);
                        System.out.println("Removed Anchor at: " + bp + " in " + l);
                        System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(l).get().getPositions());
                        be.valid = false;
                        be.setChanged();
                        l.sendBlockUpdated(bp,bs,bs,3);
                    }
                }else{
                    if(!be.valid){
                        if(be.getPower()>=32){
                            be.upgradeProtocol(l,bp);
                        }else if(be.getPower() >=16){
                            AnchorPositionComponent.ANCHORS.maybeGet(l).get().addPosition(bp);
                            System.out.println("Added Anchor at: " + bp + " in " + l);
                            System.out.println("All Anchors: " + AnchorPositionComponent.ANCHORS.maybeGet(l).get().getPositions());
                            be.valid = true;
                            be.setChanged();
                            l.sendBlockUpdated(bp,bs,bs,3);
                        }
                    }
                }
                be.tickSpacer++;
            }else if(be.tickSpacer == 9){
                be.tickSpacer = 0;
            }else{
                be.tickSpacer++;
            }
        }
    }

    public void updateCharge(Level l,boolean charge){
        if(charge != this.powered) {
            this.powered = charge;
            this.setChanged();
            if(charge){
                l.playSound(null,this.getBlockPos(), AshfallSounds.PULSE_EVENT, SoundSource.BLOCKS,0.5f,1.5f);
            }
        }
    }

    public void upgradeProtocol(Level l, BlockPos bp){
        if(!l.isClientSide){
            l.setBlock(bp, AshfallBlocks.CONTROL_ANCHOR_BLOCK.defaultBlockState(),3);
        }
    }

    public boolean validate(Level l, BlockPos bp){

        BlockPos tip = bp.below();
        BlockPos upper = tip.below();
        BlockPos mid = upper.below();
        BlockPos base = mid.below();
            return (
                    /*air gap*/
                    l.getBlockState(bp.above()).isAir()
                            &&l.getBlockState(bp.above().above()).isAir()
                    /*pillar*/
                            && l.getBlockState(tip).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(upper).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid).getBlock() == Blocks.COBBLED_DEEPSLATE
                    /*middle-ring*/
                            && l.getBlockState(mid.west(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.east(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.north(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.south(1)).getBlock() == Blocks.COBBLED_DEEPSLATE
                            && l.getBlockState(mid.north(1).west(1)).getBlock() == Blocks.SOUL_SAND
                            && l.getBlockState(mid.north(1).east(1)).getBlock() == Blocks.SOUL_SAND
                            && l.getBlockState(mid.south(1).west(1)).getBlock() == Blocks.SOUL_SAND
                            && l.getBlockState(mid.south(1).east(1)).getBlock() == Blocks.SOUL_SAND
                    /*upper-ring*/
                            && l.getBlockState(upper.west(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.east(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.north(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.south(1)).getBlock() == Blocks.OBSIDIAN
                            && l.getBlockState(upper.north(1).west(1)).getBlock() == Blocks.SOUL_FIRE
                            && l.getBlockState(upper.north(1).east(1)).getBlock() == Blocks.SOUL_FIRE
                            && l.getBlockState(upper.south(1).west(1)).getBlock() == Blocks.SOUL_FIRE
                            && l.getBlockState(upper.south(1).east(1)).getBlock() == Blocks.SOUL_FIRE
                    /*platform*/
                            && l.getBlockState(base.north(2).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(2).east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.north(1).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.north(1).east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.south(1).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(1).east(2)).getBlock() == Blocks.SOUL_SOIL

                            && l.getBlockState(base.south(2).west(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2).west(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2).east(1)).getBlock() == Blocks.SOUL_SOIL
                            && l.getBlockState(base.south(2).east(2)).getBlock() == Blocks.SOUL_SOIL

            );
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean("charged", powered);
        nbt.putBoolean("valid",valid);
        nbt.putInt("power",power);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        powered = nbt.getBoolean("charged");
        valid = nbt.getBoolean("valid");
        power = nbt.getInt("power");
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    //-----------------------------------------------------
    //GECKOLIB-ification

    protected static final AnimationBuilder ACTIVATE =
            new AnimationBuilder().addAnimation("animation.anchor.activate",
                    ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME);

    protected static final AnimationBuilder SPIN = new AnimationBuilder().addAnimation("animation.anchor.spin",
            ILoopType.EDefaultLoopTypes.LOOP);

    protected static final AnimationBuilder IDLE =
            new AnimationBuilder().addAnimation("animation.anchor.passive",ILoopType.EDefaultLoopTypes.LOOP);


    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);


    @Override
    public void registerControllers(AnimationData d) {
        d.addAnimationController(new AnimationController(this,"Main",0,this::mainController));
    }

    protected <E extends PotentConstructBlockEntity> PlayState mainController(final AnimationEvent<E> event) {

        if(this.valid) {
            if (event.getController().getCurrentAnimation() != null &&
                    event.getController().getCurrentAnimation().animationName.equals("animation.anchor.spin")) {
                return PlayState.CONTINUE;
            }
            if(event.getController().getCurrentAnimation() != null &&
                    event.getController().getCurrentAnimation().animationName.equals("animation.anchor.activate")){
                if(event.getController().getAnimationState() == AnimationState.Stopped){
                    event.getController().setAnimation(SPIN);
                }
                return PlayState.CONTINUE;
            }
            event.getController().setAnimation(ACTIVATE);
        }else{
            if (event.getController().getCurrentAnimation() != null && !event.getController().getCurrentAnimation().animationName.equals("animation.anchor.passive")) {
                event.getController().setAnimation(IDLE);
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
