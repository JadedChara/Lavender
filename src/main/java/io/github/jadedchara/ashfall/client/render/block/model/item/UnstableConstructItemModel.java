package io.github.jadedchara.ashfall.client.render.block.model.item;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.item.util.AnimatableBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnstableConstructItemModel extends AnimatedGeoModel<AnimatableBlockItem> {


    @Override
    public ResourceLocation getModelResource(AnimatableBlockItem u) {
        return (Ashfall.id("geo/block/unstable_construct.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(AnimatableBlockItem u) {
        return (Ashfall.id("textures/block/unstable_construct.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatableBlockItem u) {
        return (Ashfall.id("animations/block/unstable_construct.animation.json"));
    }
}
