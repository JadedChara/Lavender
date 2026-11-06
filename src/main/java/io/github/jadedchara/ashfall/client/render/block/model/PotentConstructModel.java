package io.github.jadedchara.ashfall.client.render.block.model;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.block.entity.PotentConstructBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PotentConstructModel extends AnimatedGeoModel<PotentConstructBlockEntity> {

    @Override
    public ResourceLocation getModelResource(PotentConstructBlockEntity p) {
        return (Ashfall.id("geo/block/potent_construct.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(PotentConstructBlockEntity p) {
        return (Ashfall.id("textures/block/potent_construct.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(PotentConstructBlockEntity p) {
        return (Ashfall.id("animations/block/potent_construct.animation.json"));
    }
}
