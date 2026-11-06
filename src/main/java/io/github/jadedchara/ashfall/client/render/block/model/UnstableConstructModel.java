package io.github.jadedchara.ashfall.client.render.block.model;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.block.entity.UnstableConstructBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnstableConstructModel extends AnimatedGeoModel<UnstableConstructBlockEntity> {
    @Override
    public void setCustomAnimations(UnstableConstructBlockEntity animatable, int instanceId, AnimationEvent animationEvent) {
        super.setCustomAnimations(animatable, instanceId, animationEvent);
        IBone orbit = this.getAnimationProcessor().getBone("orbit");
        if(orbit != null){
            double t = animationEvent.getAnimationTick() / 20.0;
            orbit.setRotationY((float) (Math.toRadians(t*-180)));
        }
    }

    @Override
    public ResourceLocation getModelResource(UnstableConstructBlockEntity u) {
        return (Ashfall.id("geo/block/unstable_construct.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(UnstableConstructBlockEntity u) {
        return (Ashfall.id("textures/block/unstable_construct.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(UnstableConstructBlockEntity u) {
        return (Ashfall.id("animations/block/unstable_construct.animation.json"));
    }
}
