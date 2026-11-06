package io.github.jadedchara.ashfall.common.block.entity;

import io.github.jadedchara.ashfall.common.registry.AshfallBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class UnstableConstructBlockEntity extends BlockEntity implements BlockEntityTicker<UnstableConstructBlockEntity>, IAnimatable {
    int state = 0;
    int quantity = 0;

    public UnstableConstructBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AshfallBlocks.UNSTABLE_CONSTRUCT_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, UnstableConstructBlockEntity blockEntity) {

    }
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.setChanged();
    }

    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
        this.setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putInt("state", this.state);
        nbt.putInt("quantity",this.quantity);
    }
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        state = nbt.getInt("state");
        quantity = nbt.getInt("quantity");
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
            new AnimationBuilder().addAnimation("animation.construct.activate", ILoopType.EDefaultLoopTypes.LOOP);

    protected static final AnimationBuilder IDLE =
            new AnimationBuilder().addAnimation("animation.construct.idle",ILoopType.EDefaultLoopTypes.LOOP);

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);


    @Override
    public void registerControllers(AnimationData d) {
        d.addAnimationController(new AnimationController(this,"Main",0,this::mainController));
    }

    protected <E extends PotentConstructBlockEntity> PlayState mainController(final AnimationEvent<E> event) {
        if(this.getState() == 1){
            event.getController().setAnimation(ACTIVATE);
        }else{
            event.getController().setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
