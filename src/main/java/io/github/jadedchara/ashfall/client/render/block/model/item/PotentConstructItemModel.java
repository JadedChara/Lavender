package io.github.jadedchara.ashfall.client.render.block.model.item;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.block.entity.PotentConstructBlockEntity;
import io.github.jadedchara.ashfall.common.item.util.AnimatableBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PotentConstructItemModel extends AnimatedGeoModel<AnimatableBlockItem> {
    @Override
    public ResourceLocation getModelResource(AnimatableBlockItem potentConstructBlockEntity) {
        return (Ashfall.id("geo/block/potent_construct.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(AnimatableBlockItem potentConstructBlockEntity) {
        return (Ashfall.id("textures/block/potent_construct.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatableBlockItem potentConstructBlockEntity) {
        return (Ashfall.id("animations/block/potent_construct.animation.json"));
    }
}
